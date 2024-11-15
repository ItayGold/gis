package com.gis.gis.map;

import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MapAreaRepository extends JpaRepository<MapArea, Long> {

  @Query("SELECT m FROM MapArea m WHERE ST_Within(m.geometry, :boundingBox) = TRUE")
  List<MapArea> findAreasWithinBounds(Geometry boundingBox);
}

