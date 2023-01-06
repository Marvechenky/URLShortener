package com.example.urlshortener.dtos;

import lombok.Data;

@Data
public class UrlGenerationRequest {

    private String url;

    private String expirationDate;
}
