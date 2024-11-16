package com.gis.gis.map;

import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MapAreaRepository extends JpaRepository<MapArea, Long> {

  @Query("SELECT m FROM MapArea m WHERE ST_Intersects(m.geometry, ST_GeomFromGeoJSON(:boundingBox)) = true")
  List<MapArea> findAreasWithinBounds(@Param("boundingBox") String boundingBox);
}

