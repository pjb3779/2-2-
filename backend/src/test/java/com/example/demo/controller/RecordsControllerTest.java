package com.example.demo.controller;

import com.example.demo.controller.RecordsController;

import com.example.demo.model.Records;
import com.example.demo.service.RecordsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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

@WebMvcTest(RecordsController.class)
public class RecordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecordsService recordsService;

    @InjectMocks
    private RecordsController recordsController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetRecords() throws Exception {
        Records record = new Records();
        record.setUserID(1);
        record.setDocumentId(1);

        List<Records> recordsList = Collections.singletonList(record);

        when(recordsService.getRecords(1)).thenReturn(recordsList);

        mockMvc.perform(get("/records/select/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userID").value(1))
                .andExpect(jsonPath("$[0].documentId").value(1));
    }

    @Test
    public void testDeleteRecords() throws Exception {
        doNothing().when(recordsService).deleteRecords(1, 1);

        mockMvc.perform(delete("/records/delete/{userId}/{documentId}", 1, 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostMethodName() throws Exception {
        Records record = new Records();
        record.setUserID(1);
        record.setDocumentId(1);

        String recordJson = objectMapper.writeValueAsString(record);

        doNothing().when(recordsService).InsertRecords(anyInt(), anyInt());

        mockMvc.perform(post("/records/post/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recordJson))
                .andExpect(status().isOk())
                .andExpect(content().string("success record insertion"));
    }
}
