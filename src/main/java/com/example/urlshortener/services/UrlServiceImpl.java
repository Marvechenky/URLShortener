package com.example.urlshortener.services;

import com.example.urlshortener.dtos.UrlGenerationRequest;
import com.example.urlshortener.models.Url;
import com.example.urlshortener.repositories.UrlRepository;
import com.google.common.hash.Hashing;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;



@Service
public class UrlServiceImpl implements UrlService{

    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Url createShortLink(UrlGenerationRequest urlGenerationRequest) {

        if(StringUtils.isNotEmpty(urlGenerationRequest.getUrl())){

            String encodedUrl = encodedUrl(urlGenerationRequest.getUrl());

            Url urlToSave = new Url();
            urlToSave.setCreationDate(LocalDateTime.now());
            urlToSave.setOriginalURL(urlGenerationRequest.getUrl());
            urlToSave.setShortLink(encodedUrl);
            urlToSave.setExpirationDate(getExpirationDate(urlGenerationRequest.getExpirationDate(),urlToSave.getCreationDate()));

            return saveShortLink(urlToSave);
        }
        return null;
    }



    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {

        if(StringUtils.isBlank(expirationDate)) {
            return creationDate.plusSeconds(120);
        }

        return LocalDateTime.parse(expirationDate);

    }

    private String encodedUrl(String url) {
        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()),StandardCharsets.UTF_8)
                        .toString();
        return encodedUrl;
    }

    @Override
    public Url saveShortLink(Url url){
        return urlRepository.save(url);
    }


    @Override
    public Url getEncodedUrl(String url) {
        return urlRepository.findByShortLink(url);
    }


    @Override
    public void deleteShortLink(com.example.urlshortener.models.Url url) {
        urlRepository.delete(url);
    }
}
