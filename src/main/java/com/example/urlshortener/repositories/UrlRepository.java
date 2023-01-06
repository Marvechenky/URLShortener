package com.example.urlshortener.repositories;

import com.example.urlshortener.models.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends MongoRepository<Url, String>{

    Url findByShortLink(String shortLink);
}
