package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.BaseService;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class TransferService extends BaseService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String url){
        this.baseUrl = url;
    }


    //request to list all transfers
    public Transfer[] listAllTransfers(String token) {
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(baseUrl + "account/transfer", HttpMethod.GET, makeAuthEntity(token), Transfer[].class).getBody();
        } catch (Exception ex) {
            System.out.println("Cannot print users.");
            return null;
        }
        return transfers;

    }
    //request to create new transfer
    public Transfer createNewTransfer(int userTo, BigDecimal transferAmount, String token) {
        Transfer transfer = new Transfer(userTo, transferAmount);
        try{
            transfer = restTemplate.exchange(baseUrl + "account/transfer", HttpMethod.PUT, makeTransferEntity(token, transfer), Transfer.class).getBody();
        } catch (Exception ex) {
            System.out.println("Cannot make new transfer.");
            return null;
        } return transfer;
    }

    private HttpEntity makeTransferEntity(String token, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(transfer, headers);
        return entity;
    }

}
