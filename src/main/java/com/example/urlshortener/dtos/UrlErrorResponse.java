package com.example.urlshortener.dtos;

import lombok.Data;

@Data
public class UrlErrorResponse {

    private String statusCode;
    private String errorMessage;
}
