package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {


    Account getAccountByUserId(int userId);

    void createNewTransfer(Transfer transfer, String fromUsername);

    List<Transfer> listMyTransfers(int userId);

    Transfer viewTransferDetails(int id);


}
