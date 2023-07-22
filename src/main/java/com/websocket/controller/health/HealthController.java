package com.websocket.controller.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("health")
@RequestMapping("/")
public class HealthController {
  @RequestMapping(value = "health", method = RequestMethod.GET)
  public ResponseEntity<String> health() {
    return new ResponseEntity<>("healthy", HttpStatus.OK);

  }
}
