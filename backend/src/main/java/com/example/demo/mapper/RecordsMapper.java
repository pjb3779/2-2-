package com.example.demo.mapper;

import com.example.demo.model.Records;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface RecordsMapper {

    @Select("SELECT r.DocumentID, d.Title, d.Tags, d.Cover " +
            "FROM ReadRecord r " +
            "INNER JOIN Document d ON r.DocumentID = d.DocumentID " +
            "WHERE r.UserID = #{userId} " +
            "ORDER BY r.readTime DESC")
    List<Records> getRecords(int userId);

    @Delete("DELETE FROM ReadRecord WHERE UserID = #{userId} and DocumentID = #{documentId}")
    void deleteRecords(int userId, int documentId);

    @Insert("INSERT INTO ReadRecord (userID,DocumentID,readTime) VALUES(#{userID},#{documentID},NOW())")
    void InsertRecords(int userID, int documentID);
}