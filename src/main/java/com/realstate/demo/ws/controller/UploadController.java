package com.realstate.demo.ws.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\uploads";


    @GetMapping("/uploadimage") public String displayUploadForm() {
        System.out.println(UPLOAD_DIRECTORY);
        return "index.html";
    }

    @PostMapping("/upload") public ResponseEntity<String> uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        return new ResponseEntity<String>(fileNameAndPath.toString(),HttpStatus.OK);
    }
}
