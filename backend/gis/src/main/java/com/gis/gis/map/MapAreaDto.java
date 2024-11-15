package com.gis.gis.map;

public class MapAreaDto {

  private String name;
  private String geometry; // GeoJSON as String

  public MapAreaDto() {}

  public MapAreaDto(String name, String geometry) {
    this.name = name;
    this.geometry = geometry;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGeometry() {
    return geometry;
  }

  public void setGeometry(String geometry) {
    this.geometry = geometry;
  }
}
