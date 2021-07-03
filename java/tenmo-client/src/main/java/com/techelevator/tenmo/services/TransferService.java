package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.BaseService;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpMethod;
<<<<<<< HEAD
import org.springframework.web.client.RestTemplate;

import java.util.List;
=======
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Random;
>>>>>>> 6da26938dcda08f65dbee55ba960638299078a21

public class TransferService extends BaseService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

<<<<<<< HEAD
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
=======
    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void sendMoney(String token, BigDecimal transferAmount) {
        try {
            restTemplate.put(baseUrl + "transfer", HttpMethod.PUT, makeAuthEntity(token));
        } catch (RestClientResponseException ex) {
        }

    }



}


/*
private Transfer makeTransfer(String CSV) {
        String[] parsed = CSV.split(",");

        if (parsed.length < 5 || parsed.length > 6) {
            return null;
        }

        // Add method does not include an id and only has 5 strings
        if (parsed.length == 5) {
            // Create a string version of the id and place into an array to be concatenated
            String[] withId = new String[6];
            String[] idArray = new String[]{new Random().nextInt(1000) + ""};
            // place the id into the first position of the data array
            System.arraycopy(idArray, 0, withId, 0, 1);
            System.arraycopy(parsed, 0, withId, 1, 5);
            parsed = withId;
        }

        return new Transfer(
                Integer.parseInt(parsed[0].trim()),
                Integer.parseInt(parsed[1].trim()),
                Integer.parseInt(parsed[2].trim()),
                Integer.parseInt(parsed[3].trim()),
                Integer.parseInt(parsed[4].trim()),
                Integer.parseInt(parsed[5].trim()));
    }
 */
>>>>>>> 6da26938dcda08f65dbee55ba960638299078a21
