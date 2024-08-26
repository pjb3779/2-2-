package com.example.demo.controller;

import com.example.demo.controller.MessagesController;

import com.example.demo.model.Messages;
import com.example.demo.service.MessagesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessagesController.class)
public class MessagesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessagesService messagesService;

    @InjectMocks
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetMessages() throws Exception {
        Messages message = new Messages();
        message.setUserId(1);
        message.setMessageId(1);
        message.setMessage("Hello, world!");

        List<Messages> messagesList = Collections.singletonList(message);

        when(messagesService.getMessages(1)).thenReturn(messagesList);

        mockMvc.perform(get("/messages/select/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].messageId").value(1))
                .andExpect(jsonPath("$[0].message").value("Hello, world!"));
    }

    @Test
    public void testDeleteMessages() throws Exception {
        doNothing().when(messagesService).deleteMessages(1, 1);

        mockMvc.perform(delete("/messages/delete/{userId}/{messageId}", 1, 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testSendMessage() throws Exception {
        Messages message = new Messages();
        message.setUserId(1);
        message.setMessageId(1);
        message.setMessage("Hello, world!");

        String messageJson = objectMapper.writeValueAsString(message);

        doNothing().when(messagesService).sendMessage(any(Messages.class));

        mockMvc.perform(post("/messages/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(messageJson))
                .andExpect(status().isOk());
    }
}
