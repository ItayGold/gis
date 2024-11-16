package com.gis.gis.map;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.jts.io.geojson.GeoJsonWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
public class MapAreaController {

  @Autowired
  private MapAreaRepository repository;

  @PostMapping
  public MapArea saveArea(@RequestBody MapArea area) {
    area.setAreaSize(calculateAreaSize(area.getGeometry()));
    return repository.save(area);
  }

  @GetMapping
  public List<MapArea> getAreasWithinBounds(@RequestParam String boundingBox) {
    try {
      // Parse the GeoJSON bounding box into Geometry
      GeoJsonReader reader = new GeoJsonReader();
      Geometry bbox = reader.read(boundingBox);

      // Convert Geometry back to GeoJSON string
      GeoJsonWriter writer = new GeoJsonWriter();
      String geoJsonBoundingBox = writer.write(bbox);

      // Fetch areas from the repository
      return repository.findAreasWithinBounds(geoJsonBoundingBox);

    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid GeoJSON format for bounding box: " + e.getMessage());
    }
  }

  private double calculateAreaSize(Geometry geometry) {
    return geometry.getArea() / 1_000_000;
  }
}
