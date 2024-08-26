package com.example.demo.controller;

import com.example.demo.controller.service.UploadImage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author oop起洞
 * @Description:
 * @date 2024/6/8 21:31
 */
@RestController
public class UploadController {
    //test uploadImage
    @PostMapping("/upload/{imageName}")
    public String upload(MultipartFile file, @PathVariable("imageName") String name) throws Exception {
        String originalFilename = file.getOriginalFilename();
        //保证文件的名字是唯一的,从而防止文件覆盖
        String filename = name +UUID.randomUUID() +originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = UploadImage.uploadFile(filename,file.getInputStream());
        return url;
    }
}
