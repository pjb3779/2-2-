package com.example.demo.controller;

import com.example.demo.model.Messages;
import com.example.demo.service.MessagesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @GetMapping("/messages/select/{userId}")
    public List<Messages> getMessages(@PathVariable int userId) {
        return messagesService.getMessages(userId);
    }

    @DeleteMapping("/messages/delete/{userId}/{messageId}")
    public void deleteMessages(@PathVariable int userId, @PathVariable int messageId) {
        messagesService.deleteMessages(userId, messageId);
    }

    @PostMapping("/messages/send")
    public void sendMessage(@RequestBody Messages message) {
        messagesService.sendMessage(message);
    }
}
