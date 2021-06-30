package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.BaseService;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountsService extends BaseService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountsService(String url) {
        this.baseUrl = url;
    } //is this constructor/dependency injection right?

    //Is UserCredentials the right thing to put in the parameters?
    //Do we need to do HTTP entity to create the entity?




    public BigDecimal getAccountBalance(String token) {
        BigDecimal balance = null;
        try {
            balance = restTemplate.exchange(baseUrl + "accounts/balance", HttpMethod.GET, makeAuthEntity(token), BigDecimal.class).getBody();
        } catch(Exception ex) {
            System.out.println("Account not found.");
            return BigDecimal.ZERO;
        }
       return balance;
    }
}

