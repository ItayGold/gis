package com.gis.gis.draw;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

@Service
public class DrawUpdateService {

  @Autowired
  private DrawUpdateRepository repository;

  public void saveToDatabase(DrawUpdate drawUpdate) {
    if (!drawUpdate.isValid()) {
      throw new IllegalArgumentException("Invalid DrawUpdate");
    }

    // Convert coordinates to JTS Geometry
    GeometryFactory geometryFactory = new GeometryFactory();
    Coordinate[] coordinates = new Coordinate[drawUpdate.getCoordinates().length];
    for (int i = 0; i < drawUpdate.getCoordinates().length; i++) {
      double[] point = drawUpdate.getCoordinates()[i];
      coordinates[i] = new Coordinate(point[1], point[0]); // Latitude, Longitude
    }

    // Close the polygon by repeating the first point at the end
    coordinates = closePolygonIfNeeded(coordinates);

    Polygon polygon = geometryFactory.createPolygon(coordinates);

    DrawUpdateEntity entity = new DrawUpdateEntity();
    entity.setAction(drawUpdate.getAction());
    entity.setUserId(drawUpdate.getUserId());
    entity.setAreaName(drawUpdate.getAreaName());
    entity.setGeometry(polygon);
    entity.setAreaSize(drawUpdate.getAreaSize());
    entity.setTimestamp(drawUpdate.getTimestamp());
    entity.setLayer(drawUpdate.getLayer());

    repository.save(entity);

    System.out.printf("DrawUpdate saved: %s, %s, %s, %s, %s, %s\n",
        drawUpdate.getAction(), drawUpdate.getUserId(), drawUpdate.getAreaName(),
        drawUpdate.getLayer(), drawUpdate.getAreaSize(), drawUpdate.getTimestamp());
  }

  private Coordinate[] closePolygonIfNeeded(Coordinate[] coordinates) {

    if (coordinates[0].equals(coordinates[coordinates.length - 1])) {
      return coordinates; // Already closed
    }

    // Add the first coordinate at the end to close the polygon
    Coordinate[] closedCoordinates = new Coordinate[coordinates.length + 1];
    System.arraycopy(coordinates, 0, closedCoordinates, 0, coordinates.length);
    closedCoordinates[closedCoordinates.length - 1] = coordinates[0];
    return closedCoordinates;
  }
}

