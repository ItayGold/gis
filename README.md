# GIS Collaborative Application

This is a collaborative GIS application that allows multiple users to draw, save, and analyze areas on a map in real-time. The project integrates a React-based frontend using Leaflet.js and a Spring Boot backend with PostgreSQL/PostGIS for spatial data storage and manipulation.

---

## Setup Instructions

### **Frontend Setup**

1. Navigate to the `frontend` directory.
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm start
   ```
   The frontend will be available at `http://localhost:3000`.

---

### **Backend Setup**

1. Navigate to the `backend` directory.
2. Ensure Java 17+ and Maven are installed.
3. Configure the `application.properties` for PostgreSQL connection:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/gisdb
   spring.datasource.username=postgres
   spring.datasource.password=123456
   spring.jpa.hibernate.ddl-auto=update
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   The backend will be available at `http://localhost:8080`.

---

### **Database Setup**

1. Ensure Docker is installed.
2. Pull the latest PostGIS Docker image:
   ```bash
   docker pull postgis/postgis:latest
   ```
3. Run the database container:
   ```bash
   docker run --name postgres-container \
     -e POSTGRES_USER=postgres \
     -e POSTGRES_PASSWORD=123456 \
     -e POSTGRES_DB=gisdb \
     -p 5432:5432 \
     -d postgis/postgis:latest
   ```
4. Connect to the database and create the required table:
   ```sql
   CREATE TABLE map_area (
       id SERIAL PRIMARY KEY,
       name VARCHAR(255),
       geometry GEOMETRY NOT NULL,
       area_size DOUBLE PRECISION,
       valid BOOLEAN
   );
   ```
5. Insert initial data:
   ```sql
   INSERT INTO map_area (name, geometry, area_size, valid) VALUES
   ('Test Area 1', ST_GeomFromText('POLYGON((34.9 31.7, 34.9 31.8, 35.0 31.8, 35.0 31.7, 34.9 31.7))', 4326), 0.01, true);
   ```

---

## Technical Decisions

- **Frontend Framework**: React with Leaflet.js for a modern and responsive map interface.
- **Backend Framework**: Spring Boot for REST APIs and WebSocket communication.
- **Database**: PostgreSQL with PostGIS for efficient spatial queries and data storage.
- **Real-Time Communication**: STOMP over WebSocket for real-time updates.
- **GeoJSON Support**: Used GeoJSON for exchanging spatial data between frontend and backend.

---

## Known Limitations

1. Bounding box queries assume geometries are in EPSG:4326 (WGS 84).
2. The frontend does not currently support advanced error handling for WebSocket disconnects.
3. Large-scale collaboration may require performance optimizations for WebSocket and database queries.
4. Area calculations assume planar geometry and may not account for Earth's curvature in certain edge cases.

---

## Future Improvements

1. **Authentication**:
    - Add user authentication and authorization for better security.

2. **Advanced Drawing Tools**:
    - Support additional shapes like circles, lines.
    - Allow editing existing shapes.

3. **Scalability**:
    - Migrate WebSocket to a message broker like RabbitMQ for larger user bases.
    - Optimize spatial queries for high-performance scenarios.

4. **UI Enhancements**:
    - Add a dashboard to visualize and filter areas.
    - Provide satellite imagery from custom providers like govmap.gov.il.

5. **Testing**:
    - Add comprehensive unit and integration tests for both backend and frontend.

6. **Deployment**:
    - Containerize the full stack with Docker Compose for easy deployment.
    - Deploy to a cloud provider like AWS or Azure.

---

## Repository Structure

```
gis-project/
├── backend/
│   ├── src/
│   ├── pom.xml
├── frontend/
│   ├── src/
│   ├── package.json
├── .gitignore
└── README.md
```

---

## How to Contribute

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add new feature"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.

