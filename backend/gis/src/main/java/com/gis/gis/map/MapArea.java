package com.gis.gis.map;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;

@Entity
public class MapArea {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(columnDefinition = "geometry")
  private Geometry geometry;

  private double areaSize;

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Geometry getGeometry() {
    return geometry;
  }

  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
  }

  public double getAreaSize() {
    return areaSize;
  }

  public void setAreaSize(double areaSize) {
    this.areaSize = areaSize;
  }
}
