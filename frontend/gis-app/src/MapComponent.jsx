import React, { useState, useEffect, useCallback } from "react";
import { MapContainer, TileLayer, FeatureGroup } from "react-leaflet";
import { EditControl } from "react-leaflet-draw";
import "leaflet/dist/leaflet.css";
import "leaflet-draw/dist/leaflet.draw.css";
import { Stomp } from "@stomp/stompjs";
import axios from "axios";

const MapComponent = () => {
  const [areas, setAreas] = useState([]);

  // Handle area creation on the map
  const handleCreated = useCallback(
    (e) => {
      const layer = e.layer;
      const geoJson = layer.toGeoJSON();
      const area = {
        name: `Area ${areas.length + 1}`, // Dynamic naming
        geometry: geoJson.geometry,
      };

      // Send new area to the backend
      axios
        .post("/api/areas", area)
        .then((response) => {
          setAreas((prevAreas) => [...prevAreas, response.data]);
        })
        .catch((error) => console.error("Error saving area:", error));
    },
    [areas]
  );

  useEffect(() => {
    const client = Stomp.client("ws://localhost:8080/ws");

    client.connect({}, () => {
      client.subscribe("/topic/updates", (message) => {
        const update = JSON.parse(message.body);

        // Integrate new updates into the areas state
        setAreas((prevAreas) => [...prevAreas, ...update]);
      });
    });

    // Disconnect WebSocket when component unmounts
    return () => {
      if (client.connected) {
        client.disconnect();
      }
    };
  }, []);

  return (
    <MapContainer
      center={[31.0461, 34.8516]} // Default map center (Israel)
      zoom={8}
      style={{ height: "100vh", width: "100%" }}
    >
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="&copy; OpenStreetMap contributors"
      />
      <FeatureGroup>
        <EditControl
          position="topright"
          onCreated={handleCreated} // Callback for new areas
        />
      </FeatureGroup>
    </MapContainer>
  );
};

export default MapComponent;
