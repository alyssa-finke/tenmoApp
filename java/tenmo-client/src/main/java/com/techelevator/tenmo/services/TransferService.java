package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.BaseService;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TransferService extends BaseService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String url){
        this.baseUrl = url;
    }

    public List<Transfer> listAllTransfers(String token) {
        List<Transfer> transfers;
        try {
            transfers = restTemplate.exchange(baseUrl + "account/transfer", HttpMethod.GET, makeAuthEntity(token), List.class).getBody();
        } catch (Exception ex) {
            System.out.println("Cannot print transfers.");
            return null;
        }
        return transfers;
    }

}
