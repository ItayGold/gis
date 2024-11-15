package com.gis.gis;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;

@Entity
@Data
@Table(name = "draw_updates")
public class DrawUpdateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String action;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false)
  private String areaName;

  @Column(nullable = false, columnDefinition = "geometry")
  private Geometry geometry; // Use JTS Geometry for spatial data

  @Column(nullable = false)
  private double areaSize;

  @Column(nullable = false)
  private String timestamp;

  @Column(nullable = false)
  private String layer;
}

