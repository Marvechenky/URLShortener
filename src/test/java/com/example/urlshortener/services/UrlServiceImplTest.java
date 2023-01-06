package com.example.urlshortener.services;

import com.example.urlshortener.dtos.UrlGenerationRequest;
import com.example.urlshortener.models.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlServiceImplTest {

    @Autowired
    private UrlService urlService;

    private UrlGenerationRequest urlGenerationRequest;

    @BeforeEach
    void setUp() {
        urlGenerationRequest = new UrlGenerationRequest();
        urlGenerationRequest.setUrl("https://www.google.com/wate%^&FGBA/manisnothothdgbddjdhd7668jshdsdo98d8d");
    }

    @Test
    void shortLinkCanBeGeneratedFromOriginalURL_Test(){
        Url url =
                urlService.createShortLink(urlGenerationRequest);
        System.out.println(url);
        assertNotNull(url);
    }

}