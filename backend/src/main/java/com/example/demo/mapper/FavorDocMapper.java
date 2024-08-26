package com.example.demo.mapper;

import com.example.demo.model.FavorDoc;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FavorDocMapper {

        @Select("SELECT b.DocumentID, d.Title, d.Tags, d.Cover " +
                        "FROM Bookshelf b " +
                        "INNER JOIN Document d ON b.DocumentID = d.DocumentID " +
                        "WHERE b.UserID = #{userId} " +
                        "ORDER BY b.UpdateTime DESC")
        List<FavorDoc> getFavorDocs_UT(int userId);

        @Select("SELECT b.DocumentID, d.Title, d.Tags, d.Cover " +
                        "FROM Bookshelf b " +
                        "INNER JOIN Document d ON b.DocumentID = d.DocumentID " +
                        "WHERE b.UserID = #{userId} " +
                        "ORDER BY b.FavorTime DESC")
        List<FavorDoc> getFavorDocs_FT(int userId);

        @Delete("DELETE FROM Bookshelf WHERE UserID = #{userId} and DocumentID = #{documentId}")
        void deleteFavorDoc(int userId, int documentId);
}
