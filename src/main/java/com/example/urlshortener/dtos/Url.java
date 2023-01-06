package com.example.urlshortener.dtos;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Url {

    private String shortLink;

    private String originalURL;

    private LocalDateTime expirationDate;
}
