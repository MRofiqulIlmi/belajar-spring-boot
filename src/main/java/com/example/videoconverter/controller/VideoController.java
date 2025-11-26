package com.example.videoconverter.controller;

import com.example.videoconverter.service.VideoConverterService;
import com.example.videoconverter.service.VideoMovConverterService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    @Autowired
    private VideoConverterService converterService;

    @Autowired
    private VideoMovConverterService movConverterService;

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

    /**
     * Convert uploaded video to a small-size MOV file.
     *
     * Query params:
     * - codec: "h264" or "hevc" (default: h264)
     * - maxWidth: resize width limit (default: 1280)
     * - crf: quality (higher = smaller). For x264: 28-35 recommended. Default: 30
     *
     * Returns the converted file as attachment.
     */
    @PostMapping(value = "/convertMovSmall")
    public ResponseEntity<String> convertMovSmall(@RequestParam("file") MultipartFile file) {
        try {
            // Step 1: save to temp
            String tempDir = System.getProperty("java.io.tmpdir") + "/upload-temp/";
            File tempUploadDir = new File(tempDir);
            if (!tempUploadDir.exists()) tempUploadDir.mkdirs();

            File inputFile = new File(tempDir + file.getOriginalFilename());
            file.transferTo(inputFile);
            System.out.println("Saved input to: " + inputFile.getAbsolutePath());

            
            // Step 2: Define output path in user's Downloads
            String userHome = System.getProperty("user.home"); // e.g., /Users/mco
            String downloadsDir = userHome + "/Downloads/";
            File outputFile = new File(downloadsDir + "converted_small.mp4");

            // Step 3: convert smallest MOV
            movConverterService.convertToSmallestMov(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
            System.out.println("Saved output to: " + outputFile.getAbsolutePath());
    
            return ResponseEntity.ok("Conversion done. Saved to: " + outputFile.getAbsolutePath());
            // Step 4: return downloadable file

            
            // InputStreamResource resource = new InputStreamResource(new FileInputStream(outputFile));

            // return ResponseEntity.ok()
            //         .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted-small.mov")
            //         .contentType(MediaType.APPLICATION_OCTET_STREAM)
            //         .contentLength(outputFile.length())
            //         .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
