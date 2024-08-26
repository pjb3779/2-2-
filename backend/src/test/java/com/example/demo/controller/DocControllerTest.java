package com.example.demo.controller;

import com.example.demo.controller.DocController;
import com.example.demo.controller.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DocController.class)
public class DocControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private SearchService searchService;

    @Test
    public void testGetHotDocs() throws Exception {
        Map<String, Object> doc = new HashMap<>();
        doc.put("DocumentID", 1);
        List<Map<String, Object>> resultList = Collections.singletonList(doc);

        when(jdbcTemplate.queryForList(anyString())).thenReturn(resultList);

        mockMvc.perform(get("/Doc/getHotDocs"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].DocumentID").value(1));
    }

    @Test
    public void testGetHotDocsToday() throws Exception {
        Map<String, Object> doc = new HashMap<>();
        doc.put("DocumentID", 1);
        List<Map<String, Object>> resultList = Collections.singletonList(doc);

        when(jdbcTemplate.queryForList(anyString())).thenReturn(resultList);

        mockMvc.perform(get("/Doc/getHotDocsToday"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].DocumentID").value(1));
    }

    @Test
    public void testGetNewDocs() throws Exception {
        Map<String, Object> doc = new HashMap<>();
        doc.put("DocumentID", 1);
        List<Map<String, Object>> resultList = Collections.singletonList(doc);

        when(jdbcTemplate.queryForList(anyString())).thenReturn(resultList);

        mockMvc.perform(get("/Doc/getNewDocs"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].DocumentID").value(1));
    }

    @Test
    public void testGetRecDocs_LoggedIn() throws Exception {
        Map<String, Object> doc = new HashMap<>();
        doc.put("DocumentID", 1);
        List<Map<String, Object>> resultList = Collections.singletonList(doc);

        List<Map<String, Object>> tempList = Collections.singletonList(Map.of("CategoryID", 1));
        when(jdbcTemplate.queryForList(anyString(), anyInt())).thenReturn(tempList);
        when(jdbcTemplate.queryForList(anyString())).thenReturn(resultList);

        mockMvc.perform(get("/Doc/getRecDocs/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].DocumentID").value(1));
    }

    @Test
    public void testGetRecDocs_NotLoggedIn() throws Exception {
        Map<String, Object> doc = new HashMap<>();
        doc.put("DocumentID", 1);
        List<Map<String, Object>> resultList = Collections.singletonList(doc);

        when(jdbcTemplate.queryForList(anyString())).thenReturn(resultList);

        mockMvc.perform(get("/Doc/getRecDocs/{id}", -1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].DocumentID").value(1));
    }

    @Test
    public void testGetDocById() throws Exception {
        Map<String, Object> doc = new HashMap<>();
        doc.put("DocumentID", 1);
        List<Map<String, Object>> resultList = Collections.singletonList(doc);

        when(jdbcTemplate.queryForList(anyString(), anyInt())).thenReturn(resultList);

        mockMvc.perform(get("/Doc/getDocById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].DocumentID").value(1));
    }

    @Test
    public void testGetDocsInDocs() throws Exception {
        Map<String, Object> doc = new HashMap<>();
        doc.put("DocumentID", 1);
        List<Map<String, Object>> resultList = Collections.singletonList(doc);

        when(jdbcTemplate.queryForList(anyString())).thenReturn(resultList);

        mockMvc.perform(get("/Doc/getDocs/{type}/{classify}", 0, 1))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].DocumentID").value(1));
    }
    

    @Test
    public void testGetDocsBySearch() throws Exception {
        Map<String, Object> doc = new HashMap<>();
        doc.put("DocumentID", 1);
        List<Map<String, Object>> resultList = Collections.singletonList(doc);

        List<String> keywords = Collections.singletonList("keyword");
        when(searchService.analyzeKeywords(anyString())).thenReturn(keywords);
        when(jdbcTemplate.queryForList(anyString(), anyString(), anyString())).thenReturn(resultList);

        String searchTerm = URLEncoder.encode("example search", StandardCharsets.UTF_8.toString());
        mockMvc.perform(get("/Doc/searchDoc/{searchTerm}/{type}/{classify}/{time}", searchTerm, 0, 1, 0))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].DocumentID").value(1));
    }
}
