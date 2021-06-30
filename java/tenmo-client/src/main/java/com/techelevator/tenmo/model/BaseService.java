package com.techelevator.tenmo.model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class BaseService {
    protected HttpEntity makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

}
