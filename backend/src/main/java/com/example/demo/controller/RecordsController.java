package com.example.demo.controller;

import com.example.demo.model.Records;
import com.example.demo.service.RecordsService;
import org.springframework.http.HttpStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class RecordsController {

    @Autowired
    private RecordsService recordsService;

    @GetMapping("/records/select/{userId}")
    public List<Records> getRecords(@PathVariable int userId) {
        return recordsService.getRecords(userId);
    }

    @DeleteMapping("/records/delete/{userId}/{documentId}")
    public void deleteRecords(@PathVariable int userId, @PathVariable int documentId) {
        recordsService.deleteRecords(userId, documentId);
    }

    @PostMapping("/records/post/")
    public ResponseEntity<String> postMethodName(@RequestBody Records records) {
        recordsService.InsertRecords(records.getUserID(), records.getDocumentId());        
        return new ResponseEntity<>("success record insertion", HttpStatus.OK);
    }
    
}
