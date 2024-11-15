package com.gis.gis.draw;

import lombok.Data;

@Data
public class DrawUpdate {

  private String action; // "add", "update", "delete"
  private String userId; // the user performing the action
  private String areaName; // Name of the drawn area
  private double[][] coordinates; // Coordinates for the polygon [latitude, longitude] pairs
  private String layer; // Layer information: "regular" or "satellite"

  // Additional metadata (optional)
  private double areaSize; // Calculated area in square kilometers
  private String timestamp; // Timestamp of the action

  /**
   * Validates if the DrawUpdate object is correctly populated.
   *
   * @return true if valid; false otherwise.
   */
  public boolean isValid() {
    // Ensure the coordinates array is not null and has at least 3 points for a polygon
    if (coordinates == null || coordinates.length < 3) {
      return false;
    }

    // Check if each coordinate is valid (latitude: -90 to 90, longitude: -180 to 180)
    for (double[] point : coordinates) {
      if (point.length != 2 || point[0] < -90 || point[0] > 90 || point[1] < -180 || point[1] > 180) {
        return false;
      }
    }

    // Action must be one of the allowed values
    if (action == null || (!action.equals("add") && !action.equals("update") && !action.equals("delete"))) {
      return false;
    }

    // Valid if all checks pass
    return true;
  }
}

