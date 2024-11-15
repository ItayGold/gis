package com.gis.gis;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

  @MessageMapping("/draw")
  @SendTo("/topic/updates")
  public DrawUpdate handleDrawUpdate(DrawUpdate drawUpdate) {

    if (!drawUpdate.isValid()) {
      throw new IllegalArgumentException("Invalid DrawUpdate payload");
    }

    System.out.println("Received valid DrawUpdate: " + drawUpdate);

    return drawUpdate;
  }
}


