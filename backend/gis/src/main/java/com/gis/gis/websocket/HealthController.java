package com.gis.gis.websocket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {
  @GetMapping("/health")
  public String healthCheck() {
    return "Server is running";
  }
}
