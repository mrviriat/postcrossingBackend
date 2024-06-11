package com.example.postcrossingbackend.controller;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class BaseResponse {
    private final Integer status;
    private final String message;
}

