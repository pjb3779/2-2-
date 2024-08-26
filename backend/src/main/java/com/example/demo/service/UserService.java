package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Bookshelf;
import com.example.demo.model.Chapter;
import com.example.demo.model.Comment;
import com.example.demo.model.Document;
import com.example.demo.model.Draft;
import com.example.demo.model.User;
import com.example.demo.model.UserAccount;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int publishDoc(Document doc){
        return userMapper.publishDoc(doc);
    }

    //编辑好草稿之后保存至草稿箱
    public void  saveDraft(Draft draft){
        userMapper.saveDraft(draft);
    }

    //获取数据中中存储的草稿
    public List<Draft> getAllDraft(int id){
        return userMapper.getAllDrafts(id);
    }

    //删除草稿操作
    public void deleteDraft(int id){
        userMapper.deleteDraft(id);
    }

    public void updateDraft(String content,int id){
        userMapper.updateDraft(content, id);
    }

    //获取所有的文档元素
    public List<Document> getAllDocuments(int userID){
        return userMapper.getAllDocuments(userID);
    }

    //作者下架文档
    public void deleteDoc(int id){
        userMapper.deleteDoc(id);
    }

    public void publishChapter(Chapter chapter){
        userMapper.publishChapter(chapter);
    }

    public List<Chapter> getChpaters(int id){
        return userMapper.getChapters(id);
    }

    public List<Chapter> getChapters(int DocumentID){
        return userMapper.getChapters(DocumentID);
    }

    public void deleteChapter(int id){
        userMapper.deleteChapter(id);
    }

    public int docCount(int id){
        return userMapper.docCount(id);
    }

    public int docPopularity(int id){
        return userMapper.docPopularity(id);
    }

    public void updateChapterNum(int chapterNum,int documentID){
        userMapper.updateChapterNum(chapterNum, documentID);
    }





    //杨智方
    public Document getDocumentById( Long id) {
        return userMapper.getDocumentById(id);
    }

    public Chapter getChapterById(int DocumentID, int chapterNum) {
        return userMapper.getChapterById(DocumentID, chapterNum);
    }

    public UserAccount getUserById(Long id){
        return userMapper.getUserById(id);
    }

    public Bookshelf getBook(int UserID, int DocumentID) {
        return userMapper.getBook(UserID, DocumentID);
    }

    public void addBookToBookshelf(Bookshelf book) {
        userMapper.addBookToBookshelf(book);
    }

    public void deleteBookFromBookshelf(Bookshelf bookshelf){
        userMapper.deleteBookFromBookshelf(bookshelf);
    }
    public void addComment(Comment comment) {
        userMapper.insertComment(comment);
    }

    public List<Comment> getComments(int DocumentID, int chapterNum){
        return  userMapper.getCommentsByUserIdAndDocumentId(DocumentID, chapterNum);
    }
}