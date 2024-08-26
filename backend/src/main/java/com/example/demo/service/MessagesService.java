package com.example.demo.service;

import com.example.demo.mapper.MessagesMapper;
import com.example.demo.model.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesService {

    @Autowired
    private MessagesMapper messagesMapper;

    public List<Messages> getMessages(int userId) {
        return messagesMapper.getMessages(userId);
    }

    public void deleteMessages(int userId, int documentId) {
        messagesMapper.deleteMessages(userId, documentId);
    }

    public void sendMessage(Messages message) {
        messagesMapper.sendMessage(message);
    }
}
