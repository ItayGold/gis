package com.gis.gis.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;

import java.io.IOException;

public class GeometryDeserializer extends StdDeserializer<Geometry> {

  public GeometryDeserializer() {
    super(Geometry.class);
  }

  @Override
  public Geometry deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    String geoJson = parser.readValueAsTree().toString();
    GeoJsonReader reader = new GeoJsonReader();
    try {
      return reader.read(geoJson);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}

