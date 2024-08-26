package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.controller.service.SearchService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author oop起洞
 * @Description:
 * @date 2024/6/8 14:14
 */
@RestController
@EnableAutoConfiguration //开启自动配置
public class DocController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final SearchService searchService;
    @Autowired
    public DocController(SearchService searchService) {
        this.searchService = searchService;
    }
    @RequestMapping("/Doc/getHotDocs")
    public List<Map<String, Object>> getHotDocs(){
        String sql = """
                select DocumentID from Document
                ORDER BY Popularity DESC
                LIMIT 10""";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        return list;
    }
    @RequestMapping("/Doc/getHotDocsToday")
    public List<Map<String, Object>> getHotDocsToday(){
        String sql = """
                select DocumentID from Document
                where DATEDIFF(CURRENT_TIME,publishTime)<=1
                ORDER BY Popularity DESC
                LIMIT 10""";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        return list;
    }
    @RequestMapping("/Doc/getNewDocs")
    public List<Map<String, Object>> getNewDocs(){
        String sql = """
                select DocumentID from Document
                ORDER BY publishTime DESC
                LIMIT 10""";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        return list;
    }
    /**
     * @Description 通过当前登录用户得到其阅读记录，根据阅读记录中信息推送推荐文档
     * @param id
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author oop启洞
     * @date 2024/6/9 14:15
    **/
    @RequestMapping("/Doc/getRecDocs/{id}")
    public List<Map<String, Object>> getRecDocs(@PathVariable("id") int id){

        String sql = "";
        if(id==-1){//未登录默认推荐
            sql = """
                select DocumentID from Document
                ORDER BY publishTime DESC
                LIMIT 12""";
        }else {//登录个性化推荐
            String sql1 = """
                    SELECT COUNT(*) as font,Document.CategoryID
                    FROM ReadRecord
                    JOIN Document ON ReadRecord.DocumentID=Document.DocumentID
                    WHERE ReadRecord.userID=?
                    GROUP BY Document.CategoryID
                    ORDER BY font DESC
                    """;
            List<Map<String, Object>> listTemp =  jdbcTemplate.queryForList(sql1,id);
            int[] categoryIdArray = listTemp.stream()
                    .mapToInt(map -> (int) map.get("CategoryID"))
                    .distinct()
                    .toArray();
            System.out.println(Arrays.toString(categoryIdArray));
            StringBuilder fontControl= new StringBuilder();
            if(categoryIdArray.length>0)fontControl.append("CASE ");
            for(int i=0;i<categoryIdArray.length;i++){
                fontControl.append(" WHEN Document.CategoryID=").append(categoryIdArray[i]).append(" THEN ").append(i+1).append(" ");
            }
            if(categoryIdArray.length>0)fontControl.append("ELSE ").append(categoryIdArray.length+1).append(" END, ");
            sql = """
                  SELECT *
                  FROM Document
                  ORDER BY
                  """
                    +" "+fontControl+" "+
                  """
                  Document.Popularity DESC
                  LIMIT 12; 
                  """;
        }
        System.out.println(sql);
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        return list;
    }

    @RequestMapping("/Doc/getDocById/{id}")
    public List<Map<String, Object>> getDocById(@PathVariable int id){
        String sql = """
                select * from Document
                WHERE DocumentID = ?""";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql,id);
        return list;
    }

    @RequestMapping("/Doc/getDocs/{type}/{classify}")
    public List<Map<String, Object>> getDocsInDocs(@PathVariable("type") int type,
    @PathVariable("classify") int classify){
        String classControl = (classify!=0)?String.format("WHERE CategoryID=%d ",classify):" ";
        String typeControl = "";
        if(type==0)typeControl=" ORDER BY title";//默认
        else if(type==1)typeControl=" ORDER BY publishTime DESC";//时间降序
        else if(type==2)typeControl=" ORDER BY publishTime";//时间升序
        else if(type==3)typeControl=" ORDER BY Popularity DESC";//热度降序
        else if(type==4)typeControl=" ORDER BY Popularity";//热度升序
        String sql = " SELECT * FROM Document  "+classControl+" "+typeControl;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        return list;
    }
    /**
     * @Description 根据提供的搜索关键词匹配文档
     * @param searchTerm
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author oop启洞
     * @date 2024/6/8 14:30
    **/
    @RequestMapping("/Doc/searchDoc/{searchTerm}/{type}/{classify}/{time}")
    public List<Map<String, Object>> getDocsBySearch(@PathVariable("searchTerm") String searchTerm,
    @PathVariable("type") int type,@PathVariable("classify") int classify,@PathVariable("time") int time){
        String decodedSearchTerm = URLDecoder.decode(searchTerm, StandardCharsets.UTF_8);
        List<String> keywords = searchService.analyzeKeywords(decodedSearchTerm);
        String keys = keywords
                .stream().map(keyword -> "LOWER(title) LIKE CONCAT('%', \""+keyword+"\", '%')")
                .collect(Collectors.joining(" OR "));
        String classControl = (classify!=0)?String.format("CategoryID=%d AND ",classify):"";
        String typeControl = "";
        String timeControl = "";
        if(type==0)typeControl="title";
        else if(type==1)typeControl="publishTime DESC";
        else if(type==2)typeControl="publishTime";
        else if(type==3)typeControl="Popularity DESC";
        else if(type==4)typeControl="Popularity";
        if(time==0)timeControl="";
        else if(time==1)timeControl="DATEDIFF(CURRENT_TIME,publishTime)<=1 AND ";
        else if(time==2)timeControl="DATEDIFF(CURRENT_TIME,publishTime)<=7 AND ";
        else if(time==3)timeControl="DATEDIFF(CURRENT_TIME,publishTime)<=30 AND ";
        String sql = " SELECT * FROM Document WHERE "+timeControl+classControl+"("+keys+")"+"""
                       ORDER BY
                       CASE
                        WHEN LOWER(title) LIKE CONCAT(?, '%') THEN 1
                        WHEN LOWER(title) LIKE CONCAT('%', ?, '%') THEN 2
                        ELSE 3
                       END,
"""+typeControl;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql,"\""+keywords.get(0)+"\"","\""+keywords.get(0)+"\"");
        return list;
    }
    @RequestMapping("/Doc/testClip/{url}")
    public String test(@PathVariable("url") String url){
        String decodedSearchTerm = URLDecoder.decode(url,StandardCharsets.UTF_8);
        List<String> keywords = searchService.analyzeKeywords(decodedSearchTerm);
        return keywords
                .stream().map(keyword -> "title LIKE CONCAT('%', \""+keyword+"\", '%')")
                .collect(Collectors.joining(" OR "));
    }



}
