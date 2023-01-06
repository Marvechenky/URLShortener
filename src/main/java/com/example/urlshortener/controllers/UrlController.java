package com.example.urlshortener.controllers;

import com.example.urlshortener.dtos.UrlErrorResponse;
import com.example.urlshortener.dtos.UrlGenerationRequest;
import com.example.urlshortener.models.Url;
import com.example.urlshortener.services.UrlService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(("api/v1/url"))
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody UrlGenerationRequest urlGenerationRequest){

        Url response
                = urlService.createShortLink(urlGenerationRequest);

        if(response != null){
            Url urlResponse = new Url();
            urlResponse.setOriginalURL(response.getOriginalURL());
            urlResponse.setExpirationDate(response.getExpirationDate());
            urlResponse.setShortLink(response.getShortLink());
            return new ResponseEntity<Url>(urlResponse, HttpStatus.OK);
        }

        UrlErrorResponse urlErrorResponse = new UrlErrorResponse();
        urlErrorResponse.setStatusCode("404");
        urlErrorResponse.setErrorMessage("There was an error processing your request. Please try again.");
        return new ResponseEntity<UrlErrorResponse>(urlErrorResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink){

        if(StringUtils.isEmpty(shortLink)){
            UrlErrorResponse urlErrorResponse = new UrlErrorResponse();
            urlErrorResponse.setErrorMessage("Invalid Url");
            urlErrorResponse.setStatusCode("404");
            return new ResponseEntity<UrlErrorResponse>(urlErrorResponse, HttpStatus.OK);
        }

        Url urlToReturn = urlService.getEncodedUrl(shortLink);

        if(urlToReturn == null){

            UrlErrorResponse urlErrorResponse = new UrlErrorResponse();
            urlErrorResponse.setErrorMessage("Url does not exist or it might have expired!");
            urlErrorResponse.setStatusCode("400");
            return new ResponseEntity<UrlErrorResponse>(urlErrorResponse, HttpStatus.OK);
        }

        if(urlToReturn.getExpirationDate().isBefore(LocalDateTime.now())) {

            urlService.deleteShortLink(urlToReturn);
            UrlErrorResponse urlErrorResponse = new UrlErrorResponse();
            urlErrorResponse.setErrorMessage("Seems url has expired. Please generate a new one.");
            urlErrorResponse.setStatusCode("200");
            return new ResponseEntity<UrlErrorResponse>(urlErrorResponse, HttpStatus.OK);

        }
        redirectToOriginalUrl(urlToReturn.getOriginalURL());
        return null;
    }

}
