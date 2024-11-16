package com.gis.gis.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.geojson.GeoJsonWriter;

import java.io.IOException;

public class GeometrySerializer extends StdSerializer<Geometry> {

  public GeometrySerializer() {
    super(Geometry.class);
  }

  @Override
  public void serialize(Geometry value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    GeoJsonWriter writer = new GeoJsonWriter();
    String geoJson = writer.write(value);
    gen.writeRawValue(geoJson);
  }
}

