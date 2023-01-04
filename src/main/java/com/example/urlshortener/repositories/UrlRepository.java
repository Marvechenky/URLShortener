package com.example.urlshortener.repositories;

import com.example.urlshortener.models.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String>{
}
