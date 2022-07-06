package com.amido.demo.controller;

import com.amido.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

  @Autowired private MessageService messageService;

  @PostMapping("/send")
  public ResponseEntity<String> sendMessage(@RequestBody String message) {

    return ResponseEntity.ok(messageService.send(message));
  }
}
