package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author oop起洞
 * @Description:
 * @date 2024/5/28 12:09
 */
@RestController
@EnableAutoConfiguration //开启自动配置
public class testcontroller {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("/mysql/{id}")
    public List<Map<String, Object>> getDbType(@PathVariable Long id){
        String sql = "select * from Document WHERE DocumentId = ?";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql,id);
        for (Map<String, Object> map : list) {
            Set<Map.Entry<String, Object>> entries = map.entrySet( );
            if(entries != null) {
                for (Map.Entry<String, Object> entry : entries) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    System.out.println(key + ":" + value);
                }
            }
        }
        return list;
    }


}
