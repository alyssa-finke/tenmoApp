package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.BaseService;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountsService extends BaseService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountsService(String url) {
        this.baseUrl = url;
    } //is this constructor/dependency injection right?


    public BigDecimal getAccountBalance(String token) {
        BigDecimal balance = null;
        try {
            balance = restTemplate.exchange(baseUrl + "accounts/balance", HttpMethod.GET, makeAuthEntity(token), BigDecimal.class).getBody();
        } catch (Exception ex) {
            System.out.println("Account not found.");
            return BigDecimal.ZERO;
        }
        return balance;
    }

    public User[] findAllUsersTransfer(String token) {
        User[] users = null;
        try {
            users = restTemplate.exchange(baseUrl + "users", HttpMethod.GET, makeAuthEntity(token), User[].class).getBody();
        } catch (Exception ex) {
            System.out.println("Cannot print users.");
            return null;
        }
        return users;

    }
}

