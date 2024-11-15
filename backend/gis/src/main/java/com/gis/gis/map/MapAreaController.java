package com.gis.gis.map;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
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
      Geometry bbox = new WKTReader().read(boundingBox);
      return repository.findAreasWithinBounds(bbox);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid bounding box format", e);
    }
  }

  private double calculateAreaSize(Geometry geometry) {
    return geometry.getArea() / 1_000_000;
  }
}
