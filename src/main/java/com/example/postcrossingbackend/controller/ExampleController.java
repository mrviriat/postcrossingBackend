package com.example.postcrossingbackend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/postcard")
public class ExampleController {

    @GetMapping("/sayHello")
    public BaseResponse sayHello() {
        return new BaseResponse("Success", "Hello everyone");
    }

    @PostMapping("/uploadImage")
    public BaseResponse uploadFile(@RequestPart("file") MultipartFile file) {
//        try {
//            File convertFile = new File(
//                    new File("src/main/resources/static/images").getAbsolutePath(),
//                    file.getOriginalFilename()
//            );
//
//            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//                IOUtils.copy(file.getInputStream(), fos);
//            }

        Path uploadPath = Paths.get("src/main/resources/static/images");
        Path filePath = uploadPath.resolve(file.getOriginalFilename());

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath.toFile()))) {
            bos.write(file.getBytes());
            bos.flush();

            return new BaseResponse("Success", "The file was saved successfully");
        } catch (IOException e) {
            return new BaseResponse("Fail", "We couldn't save the file");
        }
    }
}