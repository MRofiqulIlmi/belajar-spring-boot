package com.example.videoconverter.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class VideoConverterService {
   public void convertWmvToMp4(String inputPath, String outputPath) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg", "-i", inputPath,
                "-c:v", "libx264", "-preset", "fast", "-crf", "22",
                outputPath
        );
        pb.redirectErrorStream(true);
        Process p = pb.start();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            br.lines().forEach(line -> System.out.println("[ffmpeg] " + line));
        }

        int exit = p.waitFor();
        if (exit != 0) {
            throw new RuntimeException("FFmpeg exit code " + exit);
        }
    }
}
