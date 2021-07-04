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


//Switched this to an array instead of a list, like was done for users.
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

//account/transfer OR account/transfer/{id} Should it be going to the userId or accountId?
    //do I need to be sure I've connected accounts to transfers?
    public Transfer createNewTransfer(int selectId, BigDecimal moneyToSend, String token) {
        Transfer transfer = new Transfer(selectId, moneyToSend);// pass in selectId and moneyToSend
        //may need to make extra constructor
        try{
            transfer = restTemplate.exchange(baseUrl + "account/transfer", HttpMethod.PUT, makeTransferEntity(token, transfer), Transfer.class).getBody();//add transfer entity. won't need makeauthentity(token)
        } catch (Exception ex) {
            System.out.println("Cannot make new transfer.");
            return null;
        } return transfer;
    }

    //added this
    private HttpEntity makeTransferEntity(String token, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(transfer, headers);
        return entity;
    }



//Is how this working is that the app calls the method above, this request is recognized by server bc of url and httpmethod,
    //and from there it knows what to do?


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
