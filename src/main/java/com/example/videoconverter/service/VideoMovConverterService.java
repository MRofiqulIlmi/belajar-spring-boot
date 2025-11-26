package com.example.videoconverter.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service
public class VideoMovConverterService {
    public void convertToSmallestMov(String inputPath, String outputPath) throws Exception {

        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg", "-y" , "-i", inputPath,

                // Smallest MOV
                "-vcodec", "libx264",
                "-preset", "veryfast",
                "-crf", "30",
                "-b:v", "800k",
                "-pix_fmt", "yuv420p",

                outputPath
        );

        pb.redirectErrorStream(true);
        Process p = pb.start();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()))) {
            br.lines().forEach(line -> System.out.println("[ffmpeg] " + line));
        }

        int exit = p.waitFor();
        if (exit != 0) {
            throw new RuntimeException("FFmpeg exit code " + exit);
        }
    }
}
