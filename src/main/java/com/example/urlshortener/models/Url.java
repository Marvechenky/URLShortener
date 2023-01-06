package com.example.urlshortener.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Urls")
@Data
public class Url {

    @Id
    private String id;

    private String originalURL;

    private String shortLink;

    private LocalDateTime creationDate;

    private LocalDateTime expirationDate;

}
