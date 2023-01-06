package com.example.urlshortener.services;


import com.example.urlshortener.dtos.UrlGenerationRequest;
import com.example.urlshortener.models.Url;


public interface UrlService {

    Url createShortLink(UrlGenerationRequest urlGenerationRequestRequest);

    Url saveShortLink(Url url);

    Url getEncodedUrl(String url);

    void deleteShortLink(Url url);
}
