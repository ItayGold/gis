package com.gis.gis.map;

import jakarta.persistence.*;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

@Entity
public class MapArea {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  private String name;

  @Setter
  @Column(columnDefinition = "geometry(POLYGON, 4326)")
  private Geometry geometry;

  @Setter
  private double areaSize;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Geometry getGeometry() {
    return geometry;
  }

  public double getAreaSize() {
    return areaSize;
  }

  public boolean isValid() {
    return name != null && geometry != null && areaSize > 0;
  }
}
