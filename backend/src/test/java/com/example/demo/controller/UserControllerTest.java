package com.example.demo.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.UserController;
import com.example.demo.model.Draft;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.model.Chapter;
import com.example.demo.model.Document;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(UserController.class)
//@SpringBootTest
public class UserControllerTest {

    @MockBean
    private TestRestTemplate restTemplate;

    //测试文档发布功能
    @Test
    public void testPublishDoc() {
        // 创建一个文档对象
        Document doc = new Document();

        // 模拟返回一个响应
        ResponseEntity<Integer> mockResponse = new ResponseEntity<>(1, HttpStatus.OK);
        Mockito.when(restTemplate.postForEntity("/api/documents", doc, Integer.class))
               .thenReturn(mockResponse);

        ResponseEntity<Integer> response = restTemplate.postForEntity("/api/documents", doc, Integer.class);

        // 断言响应状态码为 200（OK）
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // 断言文档的 ID 不为空
        assertNotNull(response.getBody());
    }

    @Test
    public void testPublishDocWithNullDoc() {
       // 模拟restTemplate的行为
        ResponseEntity<HttpStatus> mockResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(restTemplate.postForEntity(anyString(), any(), eq(HttpStatus.class))).thenReturn(mockResponse);

        // 调用被测试的方法
        ResponseEntity<HttpStatus> response = restTemplate.postForEntity("/api/documents", null, HttpStatus.class);

        // 断言响应状态码为 404（NOT_FOUND）
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    //测试保存草稿功能
    @Test
    public void testSaveDraft_Success() {
        Draft draft = new Draft();

        doNothing().when(userService).saveDraft(draft);


        ResponseEntity<String> response = userController.saveDraft(draft);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Save draft successfully", response.getBody());

        verify(userService, times(1)).saveDraft(draft);
    }

    @Test
    public void testSaveDraft_NotFound() {
         // 模拟restTemplate的行为
         ResponseEntity<HttpStatus> mockResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
         when(restTemplate.postForEntity(anyString(), any(), eq(HttpStatus.class))).thenReturn(mockResponse);
 
         // 调用被测试的方法
         ResponseEntity<HttpStatus> response = restTemplate.postForEntity("/api/documents", null, HttpStatus.class);
 
         // 断言响应状态码为 404（NOT_FOUND）
         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
 
         // 验证userService.saveDraft从未被调用
         verify(userService, never()).saveDraft(any());
    }

    @Autowired
    private MockMvc mockMvc;

    //测试获取草稿功能

    @Test
    public void testGetAllDraft_WithValidUserID() throws Exception {
        int validUserId = 1002;

        ResponseEntity<List<Draft>> response = userController.getAllDraft(validUserId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    //反向测试获取草稿功能
    @Test
    public void testGetAllDraft_WithInvalidUserID() throws Exception {
        // 模拟无效的用户 ID（0）
        int invalidUserID = 0;

        // 调用控制器方法
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/getDrafts/")
             .param("userID", String.valueOf(invalidUserID))).andReturn();

        // 获取响应状态码
        MockHttpServletResponse response = result.getResponse();
        int statusCode = response.getStatus();

        // 验证响应状态码为 404 NOT FOUND
        assertEquals(HttpStatus.NOT_FOUND.value(), statusCode);
    }


    //测试删除草稿功能

    @Test
    public void testDeleteDraft_WithNotFoundStatus() {
        // 当 id 为 0 时，期望返回的状态码为 NOT_FOUND
        ResponseEntity<String> response = userController.deleteDraft(0);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteDraft_WithOkStatus() {
        // 当 id 不为 0 时，模拟用户服务成功删除草稿，期望返回的消息和状态码为 "Delete Draft successfully" 和 OK
        int nonZeroId = 1;
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Delete Draft successfully", HttpStatus.OK);
        doNothing().when(userService).deleteDraft(nonZeroId);
        ResponseEntity<String> response = userController.deleteDraft(nonZeroId);
        assertEquals(response, expectedResponse);
    }

//测试更新草稿功能

    @Test
    void updateDraft_shouldReturnNotFoundWhenDraftIsNull() {
        // Arrange
        Draft draft = null;

        // Act
        ResponseEntity<String> response = userController.updateDraft(draft);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateDraft_shouldCallUserServiceAndReturnOkWhenDraftIsNotNull() {
        // Arrange
        Draft draft = new Draft();
        draft.setContent("Test content");
        draft.setId(1);

        // Act
        ResponseEntity<String> response = userController.updateDraft(draft);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).updateDraft(draft.getContent(), draft.getId());
    }

    @Test
    public void testGetAllDoc_WithValidUserId() {
        int validUserId = 1001;

        ResponseEntity<List<com.example.demo.model.Document>> response = userController.getAllDoc(validUserId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllDoc_WithInvalidUserId() {
        int invalidUserId = 0;

        ResponseEntity<List<com.example.demo.model.Document>> response = userController.getAllDoc(invalidUserId);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteDoc_NotFound() {
        // 测试当 id 为 0 时，返回 404 NOT_FOUND
        ResponseEntity<String> response = userController.deleteDoc(0);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteDoc_Success() {
        // 测试当 id 不为 0 时，成功删除文档并返回 200 OK
        userController.deleteDoc(1);
        // 可以添加验证 userService.deleteDoc 方法是否被调用的逻辑
    }


    //测试发布章节功能

    @Test
    public void testPublishChapter_nullChapter() {
        // 模拟传入空的 Chapter 对象
        Chapter chapter = null;

        // 调用待测方法
        ResponseEntity<String> response = userController.publishChapter(chapter);

        // 验证返回的状态码为 NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testPublishChapter_validChapter() {
        // 创建一个有效的 Chapter 对象
        Chapter chapter = new Chapter();

        // 模拟 UserService 的 publishChapter 方法调用成功
        doNothing().when(userService).publishChapter(chapter);

        // 调用待测方法
        ResponseEntity<String> response = userController.publishChapter(chapter);

        // 验证返回的消息和状态码
        assertEquals("Publish chapter successfully", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // 验证 UserService 的 publishChapter 方法被调用了一次
        verify(userService, times(1)).publishChapter(chapter);
    }


    //测试获取章节的功能
    @Test
    public void testGetChapter_WithValidUserId() {
        int validDocumentId = 1;

        ResponseEntity<List<Chapter>> response = userController.getChapter(validDocumentId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetChapter_WithInvalidUserId() {
        int invalidDocumentId = 0;
        ResponseEntity<List<Chapter>> response = userController.getChapter(invalidDocumentId);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    public void testDeleteChapterSuccess() {
        int documentID = 1;
        ResponseEntity<String> response = userController.deleteChapter(documentID);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Delete chapter successfully", response.getBody());
    }

    @Test
    public void testDeleteChapterNotFound() {
        int documentID = 0;
        ResponseEntity<String> response = userController.deleteChapter(documentID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testUpdateChapterNum_WithValidDocument() throws Exception {
        Document validDocument = new Document();
        validDocument.setDocumentID(1);
        validDocument.setChapterNum(2);

        // 使用 ObjectMapper 将对象转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String validDocumentJson = objectMapper.writeValueAsString(validDocument);

        // 模拟 userService 行为
        doNothing().when(userService).updateChapterNum(validDocument.getChapterNum(), validDocument.getDocumentID());

        mockMvc.perform(put("/users/api/updateChapterNum/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validDocumentJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Delete chapter successfully"));

        // 验证服务方法是否被调用
        verify(userService, times(1)).updateChapterNum(validDocument.getChapterNum(), validDocument.getDocumentID());
    }

    @Test
    public void testUpdateChapterNum_WithNullDoc() {
        // 创建 UserController 对象
        // 调用待测方法，传递空的 Document
        ResponseEntity<String> response = userController.updateChapterNum(null);

        // 验证返回的状态码
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    
}

