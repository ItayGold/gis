package com.gis.gis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawUpdateRepository extends JpaRepository<DrawUpdateEntity, Long> { }

