package com.infra.service.controller;

import com.infra.service.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    private final S3Service s3Service;

    public S3Controller(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @PostMapping("/upload-url")
    public ResponseEntity<?> generateUrl(@RequestParam String fileName){
        return ResponseEntity.ok(s3Service.generateUploadURL(fileName));
    }
}
