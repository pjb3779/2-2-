package com.example.demo.controller;

import com.example.demo.model.Bookshelf;
import com.example.demo.model.Chapter;
import com.example.demo.model.Comment;
import com.example.demo.model.Document;
import com.example.demo.model.Draft;
import com.example.demo.model.User;
import com.example.demo.model.UserAccount;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;






@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173") // 允许访问的前端域名
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/documents")
    public ResponseEntity<Integer> publishDoc(@RequestBody Document doc) {
        userService.publishDoc(doc);
        // doc对象的id属性现在包含了新插入记录的ID
        if(doc==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(doc.getDocumentID(), HttpStatus.OK);
    }

    //编辑完草稿之后，将草稿保存至数据库
    @PostMapping("/api/addToDraft")
    public ResponseEntity<String> saveDraft(@RequestBody Draft draft){
        userService.saveDraft(draft);
        if(draft==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        return new ResponseEntity<>("Save draft successfully", HttpStatus.OK);
    }

    @GetMapping("/api/getDrafts/")
    public ResponseEntity<List<Draft>> getAllDraft(@RequestParam int userID) {
        List<Draft> draft = userService.getAllDraft(userID);
        if(userID==0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(draft, HttpStatus.OK);
    }

    //用户进行删除文档的操作
    @DeleteMapping("/api/deleteDraft/")
    public ResponseEntity<String> deleteDraft(@RequestParam int id){
        userService.deleteDraft(id);
        if(id==0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        return new ResponseEntity<>("Delete Draft successfully", HttpStatus.OK);
    }

    @PutMapping("/api/updateDraft/")
    public ResponseEntity<String> updateDraft(@RequestBody Draft draft){
        if(draft==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        userService.updateDraft(draft.getContent(), draft.getId());
        
        return new ResponseEntity<>("Update Draft successfully", HttpStatus.OK);
    }

    //获取所有文档
    @GetMapping("/api/getDocs/")
    public ResponseEntity<List<Document>> getAllDoc(@RequestParam int userID) {
        if(userID==0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        List<Document> doc = userService.getAllDocuments(userID);
        return new ResponseEntity<>(doc, HttpStatus.OK);
    }

    //作者删除文档
    @DeleteMapping("/api/deleteDoc/")
    public ResponseEntity<String> deleteDoc(@RequestParam int id){
        if(id==0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        userService.deleteDoc(id);
        return new ResponseEntity<>("Delete Doc successfully", HttpStatus.OK);
    } 

    @PostMapping("/api/chapters/")
    public ResponseEntity<String> publishChapter(@RequestBody Chapter chapter) {
        //TODO: process POST request
        if(chapter==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        userService.publishChapter(chapter);
        return new ResponseEntity<>("Publish chapter successfully", HttpStatus.OK);
    }
    //获取所有文档
    @GetMapping("/api/getChapters/")
    public ResponseEntity<List<Chapter>> getChapter(@RequestParam int documentID) {
        if(documentID==0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        List<Chapter> chapter = userService.getChpaters(documentID);
        return new ResponseEntity<>(chapter, HttpStatus.OK);
    }
    
    //作者删除文档
    @DeleteMapping("/api/deleteChapters/")
    public ResponseEntity<String> deleteChapter(@RequestParam int documentID){
        if(documentID==0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        userService.deleteChapter(documentID);
        return new ResponseEntity<>("Delete chapter successfully", HttpStatus.OK);
    } 

    @GetMapping("/api/getDocCount/")
    public int docCount(@RequestParam int userID) {
        return userService.docCount(userID);
    }

    @GetMapping("/api/getPopularity/")
    public int getPopularity(@RequestParam int userID) {
        return userService.docPopularity(userID);
    }
    @PutMapping("/api/updateChapterNum/")
    public ResponseEntity<String> updateChapterNum(@RequestBody Document doc){
        if(doc==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        userService.updateChapterNum(doc.getChapterNum(),doc.getDocumentID());
        return new ResponseEntity<>("Delete chapter successfully", HttpStatus.OK);
    } 
    

    //杨智方
    @GetMapping("/api/Document/")
    public ResponseEntity<Document> getDocumentById(@RequestParam Long id) {
        Document document = userService.getDocumentById(id);
        if (document != null) {
            return new ResponseEntity<>(document, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/api/UserAccount/")
    public ResponseEntity<UserAccount> getMethodName(@RequestParam Long param) {
        UserAccount user = userService.getUserById(param);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/api/Bookshelf/")
    public ResponseEntity<Bookshelf> isBookInBookshelf(@RequestParam int UserID, @RequestParam int DocumentID) {
        Bookshelf bookshelf = userService.getBook(UserID, DocumentID);
        if(bookshelf!=null){
            return new ResponseEntity<>(bookshelf, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/Bookshelf/")
    public ResponseEntity<String> addBookToBookshelf(@RequestBody Bookshelf bookshelf) {
        userService.addBookToBookshelf(bookshelf);
        return new ResponseEntity<>("Book added to bookshelf successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/api/Bookshelf/")
    public ResponseEntity<String> removeBookFromBookshelf(@RequestBody Bookshelf bookshelf){
        userService.deleteBookFromBookshelf(bookshelf);
        return new ResponseEntity<>("Book removed from bookshelf successfully", HttpStatus.OK);
    }   

    @PostMapping("/api/Comment/")
    public ResponseEntity<String> addComment(@RequestBody Comment comment) {
        userService.addComment(comment);
        return new ResponseEntity<>("Comment added to comments successfully", HttpStatus.OK);
    }


    @GetMapping("/api/Comment/")
    public ResponseEntity<List<Comment>> getComments(@RequestParam int DocumentID, @RequestParam int chapterNum) {
        List<Comment> list = userService.getComments(DocumentID, chapterNum);
        if(list != null){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/api/chapters/")
    public ResponseEntity<List<Chapter>> publishChapter(@RequestParam int DocumentID) {
        List<Chapter> list = userService.getChapters(DocumentID);        
        if(list !=null){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/chapterContent/")
    public ResponseEntity<Chapter> getChapterById(@RequestParam int DocumentID, @RequestParam int chapterNum) {
        Chapter chapter = userService.getChapterById(DocumentID, chapterNum);
        if (chapter != null) {
            return new ResponseEntity<>(chapter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}