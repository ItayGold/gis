import React, { useState, useEffect, useCallback } from "react";
import { MapContainer, TileLayer, FeatureGroup } from "react-leaflet";
import { EditControl } from "react-leaflet-draw";
import "leaflet/dist/leaflet.css";
import "leaflet-draw/dist/leaflet.draw.css";
import { Client } from "@stomp/stompjs";
import axios from "axios";

const MapComponent = () => {
  const [areas, setAreas] = useState([]);

  // Base URL for the backend
  const BASE_URL = "http://localhost:8080";

  // Handle area creation on the map
  const handleCreated = useCallback(
    (e) => {
      const layer = e.layer;
      const geoJson = layer.toGeoJSON();
      const area = {
        name: `Area ${Date.now()}`,
        geometry: geoJson.geometry,
      };

      axios
        .post(`${BASE_URL}/api/areas`, area)
        .then((response) => {
          setAreas((prevAreas) => [...prevAreas, response.data]);
        })
        .catch((error) => console.error("Error saving area:", error));
    },
    [BASE_URL]
  );

  useEffect(() => {
    const client = new Client({
      brokerURL: "ws://localhost:8080/ws",
      reconnectDelay: 5000,
    });

    client.onConnect = () => {
      client.subscribe("/topic/updates", (message) => {
        const update = JSON.parse(message.body);
        setAreas((prevAreas) => [...prevAreas, ...update]);
      });
    };

    client.onStompError = (error) => {
      console.error("WebSocket Error:", error);
    };

    client.activate();

    return () => {
      if (client.connected) {
        client.deactivate(); 
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
          onCreated={handleCreated}
        />
      </FeatureGroup>
    </MapContainer>
  );
};

export default MapComponent;
