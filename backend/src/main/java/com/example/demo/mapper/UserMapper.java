package com.example.demo.mapper;

import com.example.demo.model.Bookshelf;
import com.example.demo.model.Chapter;
import com.example.demo.model.Comment;
import com.example.demo.model.Document;
import com.example.demo.model.Draft;
import com.example.demo.model.User;
import com.example.demo.model.UserAccount;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO Document(Title,Tags,AuthorID,Summary,Popularity,chapterNum,CurrentModifyTime,publishTime,CategoryID,cover) VALUES(#{title},#{tags},#{autherID},#{summary},0,#{chapterNum},NOW(),NOW(),#{categoryID},#{cover})")
    @Options(useGeneratedKeys = true, keyProperty = "DocumentID")
    int publishDoc(Document document);

    @Insert("INSERT INTO Draft(Title,Content,UserID,currentModityTime) VALUES (#{title},#{content},#{userID},NOW())")
    void saveDraft(Draft draft);

    @Select("SELECT * FROM Draft WHERE UserID=${userID}")
    List<Draft> getAllDrafts(int userID);

    @Delete("DELETE FROM Draft WHERE id=${id}")
    void deleteDraft(int id);

    @Update("UPDATE Draft SET content = #{content} WHERE id = #{id}")
    void updateDraft(String content,int id);

    @Select("SELECT * FROM Document WHERE AuthorID=#{userID}")
    List<Document> getAllDocuments(int userID);

    @Delete("DELETE FROM Document WHERE DocumentID=#{id}")
    void deleteDoc(int id);

    @Insert("INSERT INTO chapter(Num,creatTime,DocumentID,content,title) VALUES (#{Num},NOW(),#{DocumentID},#{content},#{title})")
    void publishChapter(Chapter chapter);

    @Delete("DELETE FROM chapter WHERE DocumentID=#{documentID}")
    void deleteChapter(int documentID);

    @Select("SELECT COUNT(*) FROM Document WHERE AuthorID = #{userID}")
    int docCount(int userID);

    @Select("SELECT SUM(Popularity) FROM Document WHERE AuthorID = #{userID}")
    int docPopularity(int userID);

    @Select("SELECT * FROM Document WHERE DocumentID=#{id}")
    Document getDocumentById(Long id);

    @Select("SELECT * FROM chapter WHERE DocumentID=#{DocumentID} AND Num=#{chapterNum}")
    Chapter getChapterById(int DocumentID, int chapterNum);

    @Select("SELECT * FROM UserAccount WHERE userID=#{id}")
    UserAccount getUserById(Long id);

    @Select("SELECT * FROM Bookshelf WHERE UserID=#{UserID} AND DocumentID=#{DocumentID}")
    Bookshelf getBook(int UserID, int DocumentID);

    @Insert("Insert INTO Bookshelf(UserID, DocumentID, FavorTime) VALUES(#{UserID}, #{DocumentID}, NOW())")
    void addBookToBookshelf(Bookshelf bookshelf);

    @Delete("DELETE FROM Bookshelf WHERE UserID=#{UserID} AND DocumentID=#{DocumentID}")
    void deleteBookFromBookshelf(Bookshelf bookshelf);

    @Insert("INSERT INTO Comment(CommentID, UserID, Content, PublishTime, DocumentID, likes, chapterNum, userName) VALUES(#{CommentID}, #{UserID}, #{Content}, NOW(), #{DocumentID}, #{likes}, #{chapterNum}, #{userName})")
    void insertComment(Comment comment);

    @Select("SELECT * FROM Comment WHERE DocumentID = #{DocumentID} AND chapterNum=#{chapterNum}")
    List<Comment> getCommentsByUserIdAndDocumentId(int DocumentID, int chapterNum);

    @Select("SELECT * FROM chapter WHERE DocumentID = #{DocumentID}")
    List<Chapter> getChapters(int DocumentID);

    @Update("UPDATE Document SET chapterNum = #{chapterNum} WHERE DocumentID = #{DocumentID}")
    void updateChapterNum(int chapterNum,int DocumentID);
}