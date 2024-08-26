package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author oop起洞
 * @Description:
 * @date 2024/5/28 14:56
 */
@RestController
@EnableAutoConfiguration
public class userControllerExtra {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JavaMailSenderImpl sender;
    /**
     * @Description 通过检查数据库是否有用户名密码一致的行
     * @param username
     * @param password
     * @return java.lang.String
     * @author oop启洞
     * @date 2024/6/4 14:25
    **/
    @RequestMapping("/checkLogin/{username}/{password}")
    public String checkLogin(@PathVariable("username") String username,@PathVariable("password") String password){
        String sql= """
                SELECT COUNT(*) as num
                FROM UserAccount
                WHERE userName=? AND passWords=?
                """;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql,username+"",password+"");
        Long num = (Long) getNum(list);
        if(num == 0)return "No such user account !";
        return "well";
    }
    /**
     * @Description 得到用户数据，在前端的到的是一个JSON格式的数组
     * @param username
     * @param password
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author oop启洞
     * @date 2024/6/4 15:37
    **/
    @RequestMapping("/getLoginMsg/{username}/{password}")
    public List<Map<String, Object>> getUser_Login(@PathVariable("username") String username,@PathVariable("password") String password){
        String sql = """
                SELECT * FROM UserAccount
                WHERE userName=? and passWords=?
                """;
        //加上“”是为了让ide的参数列表提示显示出来
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql,username+"",password+"");
        return list;
    }
    /**
     * @Description 通过邮箱查找对应用户
     * @param email
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author oop启洞
     * @date 2024/6/4 17:34
    **/
    @RequestMapping("/getUserByEmail/{email}")
    public List<Map<String, Object>> getUserByEmail(@PathVariable("email") String email){
        String sql = """
                SELECT * FROM UserAccount
                WHERE Email=?
                """;
        //加上“”是为了让ide的参数列表提示显示出来
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql,email+"");
        return list;
    }
    /**
     * @Description 更新邮箱对应用户的密码
     * @param email
     * @param password
     * @return java.lang.String
     * @author oop启洞
     * @date 2024/6/4 21:41
    **/
    @RequestMapping("/updatePassword/{email}/{password}")
    public String updatePassword(@PathVariable("email") String email,@PathVariable("password") String password){
        String sql = """
                UPDATE  UserAccount 
                SET passWords= ?
                WHERE Email=?
                """;
        //加上“”是为了让ide的参数列表提示显示出来
        jdbcTemplate.update(sql,password,email+"");
        return "well";
    }
    /**
     * @Description 由查询得到的list得到对应的值（只适合一列一行）
     * @param list
     * @return java.lang.Integer
     * @author oop启洞
     * @date 2024/6/4 14:22
    **/
    private Object getNum(List<Map<String, Object>> list) {
        if (list.isEmpty()) {
            return 0; // 如果列表为空，返回0
        }
        Set<Map.Entry<String, Object>> entryies = list.get(0).entrySet();
        
        Object num = null;
        for (Map.Entry<String, Object> entry : entryies) {
            Object value = entry.getValue();
            if(value instanceof Long){
                num = (Long) value;
            }else {
                num = (Integer) value;
            }
        }
        return num;
    }

    /**
     * @Description 利用提供的邮箱发送验证码
     * @param email
     * @return java.lang.String
     * @author oop启洞
     * @date 2024/6/4 14:26
    **/
    @RequestMapping("/checkEmail/{email}")
    public String checkEmailValid(@PathVariable("email") String email){
        // 生成令牌并发送验证邮件
        Random random = new Random();
        int number = random.nextInt(10000); // 生成 0 到 9999 之间的随机数
        String key = String.format("%04d", number); // 格式化为四位数
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("xuexia.xzp@qq.com");
        message.setTo(email);
        message.setText("Below is your key:\n\n"
                +  key);
        sender.send(message);
        return key;
    }

    /**
     * @Description 检查用户名是否重复
     * @param name
     * @return java.lang.String
     * @author oop启洞
     * @date 2024/6/4 14:27
    **/
    @RequestMapping("/checkUsername/{username}")
    public String checkRepeatName(@PathVariable("username") String name){
        String sql= """
                SELECT COUNT(*) as num
                FROM UserAccount
                WHERE userName = ?
                """;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql, name+"");
        Long num = (Long) getNum(list);
        System.out.println(num+" "+name);
        if(num != 0)return "repeat!";
        return "well";
    }
    /**
     * @Description 检查邮箱是否注册过
     * @param email
     * @return java.lang.String
     * @author oop启洞
     * @date 2024/6/4 17:37
    **/
    @RequestMapping("/checkEmailUsed/{email}")
    public String checkEmailUsed(@PathVariable("email") String email){
        String sql= """
                SELECT COUNT(*) as num
                FROM UserAccount
                WHERE Email = ?
                """;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql, email+"");
        Long num = ((Number) getNum(list)).longValue(); // 修改这里
        if(num != 0)return "repeat!";
        return "well";
    }
    /**
     * @Description 注册用户
     * @param formRequest
     * @return java.lang.String
     * @author oop启洞
     * @date 2024/6/4 14:28
    **/
    @RequestMapping("/userInsert")
    public String createUser(@RequestBody User formRequest){
        String sql1= """
                SELECT MAX(userID) as maxid
                FROM UserAccount
                """;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql1);
        Integer maxId = (Integer) getNum(list);
        String sql = """
                INSERT INTO 
                UserAccount(userID, userName, passWords, Email, creatDate, salt) 
                VALUES (?,?,?,?,date(NOW()),?)
                """;
        jdbcTemplate.update(sql,maxId+1,formRequest.getUsername(),formRequest.getPassword(),
        formRequest.getEmail(),formRequest.getPassword());
        return "yes";
    }
    @RequestMapping("/getUsrByID/{id}")
    public List<Map<String, Object>> getUserById(@PathVariable("id") int id){
        String sql = """
                SELECT * FROM UserAccount
                WHERE userID=?
                """;
        //加上“”是为了让ide的参数列表提示显示出来
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql,id);
        return list;
    }

    /**
     * @Description 这是测试的函数
     * @param id
     * @return java.lang.String
     * @author oop启洞
     * @date 2024/6/4 14:28
    **/
    @RequestMapping("/testInsert/{id}")
    public String testInsert(@PathVariable Long id){
        String sql1= """
                SELECT MAX(DocumentID) as maxid
                FROM Document
                """;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql1);
        Integer maxId = (Integer) getNum(list);
        String sql = "INSERT INTO Document(DocumentID,Title,Tags,AuthorID,Summary,Popularity," +
                "chapterNum,Content,CurrentModifyTime,publishTime,CategoryID) VALUES (?, ?, ?, ?, ?, " +
                "0,1,?,now(),now(),1 )";

        String fileContent = "";
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get("src/content"+id+".txt"));
            fileContent = new String(fileBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "insert error!";
        }
        jdbcTemplate.update(sql, maxId+1+"", "testDoc"+(maxId+1),"test","1001","这是文件"+(maxId+1)+"的简介",fileContent);
        return "man!";
    }

}
