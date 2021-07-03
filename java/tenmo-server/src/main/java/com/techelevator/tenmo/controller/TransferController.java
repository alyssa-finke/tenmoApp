package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;
    private UserDao userDao;
    private AccountsDAO accountsDAO;


    public TransferController(TransferDao transferDao, UserDao userDao, AccountsDAO accountsDAO) {
        this.transferDao = transferDao;
        this.userDao = userDao;
        this.accountsDAO = accountsDAO;
    }

    //View all users
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<User> findAllUsers() {
        return userDao.findAllUsersTransfer();

    }

    //Transfer money
    @RequestMapping(value = "account/transfer", method = RequestMethod.PUT)
    public void transferTransaction(@RequestBody Transfer transfer, Principal p) {
        String loggedInUsername = p.getName();
        transferDao.createNewTransfer(transfer, loggedInUsername);
    }

    // mapping for #5 viewTransfers// Switched params from userId to loggedInUserId
    @RequestMapping(value = "account/transfer", method = RequestMethod.GET)
    public List<Transfer> listMyTransfers(Principal p) {
        String loggedInUserName = p.getName();
        int loggedInUserId = userDao.findIdByUsername(loggedInUserName);
        return transferDao.listMyTransfers(loggedInUserId);
    }


    //mapping for #6 view transfer details//first param matches id and added principal for authentication
    @RequestMapping(value = "account/transfer/{id}", method = RequestMethod.GET)
    public Transfer viewTransferDetails(@PathVariable int id, Principal p) {
        String loggedInUserName = p.getName();
        int loggedInUserId = userDao.findIdByUsername(loggedInUserName);
        return transferDao.viewTransferDetails(id);
    }

}




