package com.example.demo.controller;

import com.example.demo.model.FavorDoc;
import com.example.demo.service.BookshelfService;

import java.util.List;
import org.springframework.http.HttpStatus;
// import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookShelfController {

    @Autowired
    private BookshelfService bookshelfService;

    @GetMapping("/favorDoc/select/FT/{userId}")
    public ResponseEntity<List<FavorDoc>> getFavorDocs_FT(@PathVariable int userId) {
        List<FavorDoc> favorDocs = bookshelfService.getFavorDocs_FT(userId);
        if(favorDocs != null){
            return new ResponseEntity<>(favorDocs, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/favorDoc/select/UT/{userId}")
    public ResponseEntity<List<FavorDoc>> getFavorDocs_UT(@PathVariable int userId) {
        List<FavorDoc> favorDocs = bookshelfService.getFavorDocs_UT(userId);
        if(favorDocs != null){
            return new ResponseEntity<>(favorDocs, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // return bookshelfService.getFavorDocs_UT(userId);
    }

    @DeleteMapping("/favorDoc/delete/{userId}/{documentId}")
    public void deleteFavorDoc(@PathVariable int userId, @PathVariable int documentId) {
        bookshelfService.deleteFavorDoc(userId, documentId);
    }
}
