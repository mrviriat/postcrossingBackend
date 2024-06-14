package com.example.postcrossingbackend.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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

    @PostMapping("/uploadImages")
    public BaseResponse uploadFiles(
            @RequestPart("files") MultipartFile[] files,
            @RequestPart("users") String usersJson
    ) {

        String url = "http://10.144.55.42:8000/uploadImages";

        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            for (MultipartFile file : files) {
                ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename();
                    }
                };
                body.add("files", byteArrayResource);
            }

            body.add("users", usersJson);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            return new BaseResponse("Success", response.getBody());
        } catch (IOException e) {
            return new BaseResponse("Fail", "Unknown error");
        }
    }

}
