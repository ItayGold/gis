package com.gis.gis.map;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Polygon;

@Entity
@Data
public class MapArea {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(columnDefinition = "Geometry")
  private Polygon geometry;

  private Double areaSize; // In square kilometers
}

