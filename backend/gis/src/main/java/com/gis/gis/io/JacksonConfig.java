package com.gis.gis.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.annotation.PostConstruct;
import org.locationtech.jts.geom.Geometry;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JacksonConfig {

  private final ObjectMapper objectMapper;

  public JacksonConfig(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @PostConstruct
  public void customizeObjectMapper() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(Geometry.class, new GeometrySerializer());
    module.addDeserializer(Geometry.class, new GeometryDeserializer());
    objectMapper.registerModule(module);
  }
}
