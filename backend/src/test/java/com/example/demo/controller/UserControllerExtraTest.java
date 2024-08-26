package com.example.demo.controller;


import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.controller.UserController;
import com.example.demo.controller.userControllerExtra;
import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@ExtendWith(SpringExtension.class)
//@SpringBootTest
@WebMvcTest(userControllerExtra.class)
public class UserControllerExtraTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private JavaMailSenderImpl sender;

    //
    @Test
    public void testCheckLoginExistingUser() {
        // 模拟数据库查询结果，存在用户
        Mockito.when(jdbcTemplate.queryForList(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
             .thenReturn(Collections.singletonList(Collections.singletonMap("num", 1L)));

        String result = userControllerExtra.checkLogin("username", "password");

        assertEquals("well", result);
    }

    @Test
    public void testCheckLoginNonExistingUser() {
        // 模拟数据库查询结果，不存在用户
        Mockito.when(jdbcTemplate.queryForList(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
             .thenReturn(Collections.singletonList(Collections.singletonMap("num", 0L)));

        String result = userControllerExtra.checkLogin("username", "password");

        assertEquals("No such user account !", result);
    }

    
    
    private Object getNum(List<Map<String, Object>> list) {
        if (list.isEmpty()) {
            return 0;
        }
        return list.get(0).get("num");
    }


    @Autowired
    private userControllerExtra userControllerExtra;

    @Test
    public void testGetUser_Login() {
        // 正常情况，存在匹配的用户
        String username = "testUser";
        String password = "testPassword";
        List<Map<String, Object>> result = userControllerExtra.getUser_Login(username, password);
        assertNotNull(result);

        // 用户名或密码错误，没有匹配的用户
        username = "invalidUser";
        password = "invalidPassword";
        result = userControllerExtra.getUser_Login(username, password);
        assertTrue(result.isEmpty());

        // 边界情况，用户名和密码为空
        username = "";
        password = "";
        result = userControllerExtra.getUser_Login(username, password);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUserByEmail() {
        // 正常情况，存在匹配的用户
        String email = "test@example.com";
        List<Map<String, Object>> result = getUserByEmail(email);
        assertNotNull(result);

        // 边界情况，不存在匹配的用户
        email = "nonexistent@example.com";
        result = getUserByEmail(email);
        assertTrue(result.isEmpty());
    }

    private List<Map<String, Object>> getUserByEmail(String email) {
        String sql = """
                SELECT * FROM UserAccount
                WHERE Email=?
                """;
        return jdbcTemplate.queryForList(sql, email);
    }


    @Test
    public void testUpdatePassword() {
        // 准备测试数据
        String email = "test@example.com";
        String password = "newPassword";

        // 调用待测函数
        String result = updatePassword(email, password);

        // 验证结果
        assertEquals("well", result);

        // 可以进一步查询数据库以验证密码是否更新成功
    }

    private String updatePassword(String email, String password) {
        String sql = """
                UPDATE  UserAccount 
                SET passWords=?
                WHERE Email=?
                """;
        jdbcTemplate.update(sql, password, email);
        return "well";
    }

    

    @Test
    public void testGetNum() {
        // 创建一个包含一个元素的列表
        List<Map<String, Object>> list = createTestList();

        // 调用待测方法获取结果
        Object result = getNum(list);

        // 断言结果不为 null
        assertNotNull(result);
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    public void testGetNumWithDifferentValues(Long value, Object expected) {
        // 创建测试数据的列表
        List<Map<String, Object>> list = createTestListWithValue(value);

        // 调用待测方法获取结果
        Object result = getNum(list);

        // 断言结果与预期值相等
        assertEquals(expected, result);
    }

    // 用于提供测试数据的方法
    static List<Arguments> provideTestData() {
        return Arrays.asList(
                Arguments.of(1L, 1L),
                Arguments.of(2L, 2L),
                Arguments.of(3L, 3L),
                Arguments.of((long) 4, (long) 4)
        );
    }

    // 创建包含指定值的测试列表
    private List<Map<String, Object>> createTestListWithValue(Long value) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = Map.of("num", value);
        list.add(map);
        return list;
    }

    // 创建一个简单的测试列表
    private List<Map<String, Object>> createTestList() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = Map.of("num", 1);
        list.add(map);
        return list;
    }


    @Test
    public void checkEmailValid() {
        String email = "test@example.com";

        // 调用被测方法
        String key = userControllerExtra.checkEmailValid(email);

        // 验证邮件发送相关操作
        Mockito.verify(sender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));

        assertNotNull(key); // 验证生成的密钥不为空
        assertEquals(4, key.length()); // 验证密钥长度为 4
    }


     @Test
    public void testCheckRepeatName_Repeat() {
        // 模拟查询结果，表示用户名已存在
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("num", 1L);
        list.add(map);

        when(jdbcTemplate.queryForList(anyString(), anyString())).thenReturn(list);

        String result = userControllerExtra.checkRepeatName("testUsername");

        assertEquals("repeat!", result);
    }

    @Test
    public void testCheckRepeatName_NotRepeat() {
        // 模拟查询结果，表示用户名不存在
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("num", 0L);
        list.add(map);

        when(jdbcTemplate.queryForList(anyString(), anyString())).thenReturn(list);

        String result = userControllerExtra.checkRepeatName("testUsername");

        assertEquals("well", result);
    }


    

    @Test
    public void testCheckEmailUsed_ExistingEmail() {
        String email = "existing@example.com";
        String sql = """
                SELECT COUNT(*) as num
                FROM UserAccount
                WHERE Email = ?
                """;

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("num", 1);
        List<Map<String, Object>> resultList = List.of(resultMap);

        when(jdbcTemplate.queryForList(sql, email)).thenReturn(resultList);

        String result = userControllerExtra.checkEmailUsed(email);
        assertEquals("repeat!", result);
    }

    @Test
    public void testCheckEmailUsed_NonExistingEmail() {
        String email = "nonexisting@example.com";
        String sql = """
                SELECT COUNT(*) as num
                FROM UserAccount
                WHERE Email = ?
                """;

        List<Map<String, Object>> resultList = List.of();

        when(jdbcTemplate.queryForList(sql, email)).thenReturn(resultList);

        String result = userControllerExtra.checkEmailUsed(email);
        assertEquals("well", result);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userControllerExtra).build();
    }

    @Test
    public void testCreateUser() {
        User formRequest = new User();
        formRequest.setUsername("testUser");
        formRequest.setPassword("testPassword");
        formRequest.setEmail("test@example.com");

        // Mock the queryForList method to return a specific result
        List<Map<String, Object>> list = List.of(new HashMap<>() {{
            put("maxid", 100);
        }});
        when(jdbcTemplate.queryForList(anyString())).thenReturn(list);

        // Call the method under test
        String result =userControllerExtra.createUser(formRequest);

        // Verify that the query and update methods were called with the correct parameters
        verify(jdbcTemplate, times(1)).queryForList(anyString());
        verify(jdbcTemplate, times(1)).update(anyString(), eq(101), eq("testUser"), eq("testPassword"), eq("test@example.com"), eq("testPassword"));

        // Assert the expected result
        assertEquals("yes", result);
    }

    @Test
    public void testCreateUserWithEmptyRequest() {
        User formRequest = new User();

        // Call the method with an empty request
        String result =userControllerExtra.createUser(formRequest);

        // Assert that the method returns the expected result
        assertEquals("yes", result);
    }
    @Test
    void testGetUserById() {
        // 测试正常情况，存在对应 ID 的用户
        int validId = 1;
        List<Map<String, Object>> result = getUserById(validId);
        assertNotNull(result);
        // 可以根据实际情况添加更多的断言

        // 测试边界情况，ID 为 0
        int zeroId = 0;
        result = getUserById(zeroId);
        assertNotNull(result);

        // 测试异常情况，ID 为负数
        int negativeId = -1;
        result = getUserById(negativeId);
        assertNotNull(result);
    }

    private List<Map<String, Object>> getUserById(int id) {
        String sql = """
                SELECT * FROM UserAccount
                WHERE userID=?
                """;
        return jdbcTemplate.queryForList(sql, id);
    }

    @Test
    public void testTestInsert_Success() throws Exception {
        Long id = 1L;
        String sql1 = """
                SELECT MAX(DocumentID) as maxid
                FROM Document
                """;
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("maxid", 1);
        List<Map<String, Object>> resultList = List.of(resultMap);

        when(jdbcTemplate.queryForList(sql1)).thenReturn(resultList);
        String fileContent = "This is a test content";
        Files.write(Paths.get("src/content" + id + ".txt"), fileContent.getBytes(StandardCharsets.UTF_8));

        String result = userControllerExtra.testInsert(id);
        assertEquals("man!", result);
    }

   

    @Test
    public void testTestInsert() throws Exception {
        // Prepare mock data
        Map<String, Object> mockResult = Collections.singletonMap("maxid", 1);
        when(jdbcTemplate.queryForList(anyString())).thenReturn(Collections.singletonList(mockResult));
        
        // Prepare file content
        String mockFileContent = "Sample content";
        Files.write(Paths.get("src/content1.txt"), mockFileContent.getBytes(StandardCharsets.UTF_8));

        // Execute method
        String result = userControllerExtra.testInsert(1L);

        // Verify interactions
        verify(jdbcTemplate).update(eq("INSERT INTO Document(DocumentID,Title,Tags,AuthorID,Summary,Popularity," +
                "chapterNum,Content,CurrentModifyTime,publishTime,CategoryID) VALUES (?, ?, ?, ?, ?, 0,1,?,now(),now(),1 )"), 
                any(), any(), any(), any(), any(), any());
        
        // Check the result
        assertEquals("man!", result);
    }
}
