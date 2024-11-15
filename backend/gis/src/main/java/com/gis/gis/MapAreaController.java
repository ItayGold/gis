package com.gis.gis;

import org.locationtech.jts.geom.Geometry;
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
    // Save the area with calculated size
    area.setAreaSize(calculateAreaSize(area.getGeometry()));
    return repository.save(area);
  }

  @GetMapping
  public List<MapArea> getAreasWithinBounds(@RequestParam Geometry boundingBox) {
    return repository.findAreasWithinBounds(boundingBox);
  }

  private double calculateAreaSize(Geometry geometry) {
    return geometry.getArea() / 1_000_000; // Convert sq. meters to sq. kilometers
  }
}

