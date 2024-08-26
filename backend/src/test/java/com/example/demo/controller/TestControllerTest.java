package com.example.demo.controller;

import com.example.demo.controller.testcontroller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(testcontroller.class)
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private testcontroller testController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDbType() throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("DocumentId", 1);
        resultMap.put("Title", "Sample Document");

        List<Map<String, Object>> resultList = Collections.singletonList(resultMap);

        when(jdbcTemplate.queryForList(anyString(), anyLong())).thenReturn(resultList);

        mockMvc.perform(get("/mysql/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].DocumentId").value(1))
                .andExpect(jsonPath("$[0].Title").value("Sample Document"));
    }
}
