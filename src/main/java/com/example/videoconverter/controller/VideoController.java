package com.example.videoconverter.controller;

import com.example.videoconverter.service.VideoConverterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    @Autowired
    private VideoConverterService converterService;

    @PostMapping("/test-upload")
    public ResponseEntity<String> test(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = System.getProperty("java.io.tmpdir") + "/upload-temp/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs(); // Create the directory if it doesn't exist
            }
    
            File destination = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(destination);
            System.out.println("Saved file to: " + destination.getAbsolutePath());
    
            return ResponseEntity.ok("Upload OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

//    curl -v \
//    -H "Expect:" \
//    -F "file=@/Users/mco/Downloads/test.wmv" \
//    http://localhost:8080/api/video/test-upload

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@RequestParam("file") MultipartFile file) {
        try {
            // Step 1: Save input file to a temp location
            String tempDir = System.getProperty("java.io.tmpdir") + "/upload-temp/";
            File tempUploadDir = new File(tempDir);
            if (!tempUploadDir.exists()) tempUploadDir.mkdirs();
    
            File inputFile = new File(tempDir + file.getOriginalFilename());
            file.transferTo(inputFile);
            System.out.println("Saved input to: " + inputFile.getAbsolutePath());
    
            // Step 2: Define output path in user's Downloads
            String userHome = System.getProperty("user.home"); // e.g., /Users/mco
            String downloadsDir = userHome + "/Downloads/";
            File outputFile = new File(downloadsDir + "converted.mp4");
    
            // Step 3: Perform conversion
            converterService.convertWmvToMp4(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
            System.out.println("Saved output to: " + outputFile.getAbsolutePath());
    
            return ResponseEntity.ok("Conversion done. Saved to: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
