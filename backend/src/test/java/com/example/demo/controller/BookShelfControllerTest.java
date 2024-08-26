package com.example.demo.controller;

import com.example.demo.controller.BookShelfController;
import com.example.demo.service.BookshelfService;
import com.example.demo.model.FavorDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookShelfController.class)
public class BookShelfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookshelfService bookshelfService;

    @Test
    public void testGetFavorDocs_FT_Success() throws Exception {
        FavorDoc doc1 = new FavorDoc();
        doc1.setDocumentId(1);
        doc1.setTitle("Title1");
        FavorDoc doc2 = new FavorDoc();
        doc2.setDocumentId(2);
        doc2.setTitle("Title2");
        List<FavorDoc> favorDocs = Arrays.asList(doc1, doc2);

        when(bookshelfService.getFavorDocs_FT(1)).thenReturn(favorDocs);

        mockMvc.perform(get("/favorDoc/select/FT/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].documentId").value(1))
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[1].documentId").value(2))
                .andExpect(jsonPath("$[1].title").value("Title2"));
    }

    @Test
    public void testGetFavorDocs_FT_NotFound() throws Exception {
        when(bookshelfService.getFavorDocs_FT(1)).thenReturn(null);

        mockMvc.perform(get("/favorDoc/select/FT/{userId}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetFavorDocs_UT_Success() throws Exception {
        FavorDoc doc1 = new FavorDoc();
        doc1.setDocumentId(1);
        doc1.setTitle("Title1");
        List<FavorDoc> favorDocs = Arrays.asList(doc1);

        when(bookshelfService.getFavorDocs_UT(1)).thenReturn(favorDocs);

        mockMvc.perform(get("/favorDoc/select/UT/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].documentId").value(1))
                .andExpect(jsonPath("$[0].title").value("Title1"));
    }

    @Test
    public void testGetFavorDocs_UT_NotFound() throws Exception {
        when(bookshelfService.getFavorDocs_UT(1)).thenReturn(null);

        mockMvc.perform(get("/favorDoc/select/UT/{userId}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFavorDoc() throws Exception {
        doNothing().when(bookshelfService).deleteFavorDoc(1, 1);

        mockMvc.perform(delete("/favorDoc/delete/{userId}/{documentId}", 1, 1))
                .andExpect(status().isOk());
    }
}
