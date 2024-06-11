package com.example.postcrossingbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sayHello")
public class ExampleController {

    @GetMapping
    public BaseResponse sayHello() {
        return new BaseResponse(200, "Hello everyone");
    }
}