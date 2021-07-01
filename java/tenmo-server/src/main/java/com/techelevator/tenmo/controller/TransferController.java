package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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


    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<User> findAllUsers() {
        return userDao.findAllUsersTransfer();

    }

    @RequestMapping(value = "account/transfer", method = RequestMethod.PUT)
    public void transferTransaction(@RequestBody Transfer transfer, BigDecimal transferAmount, Principal principal) {
        if(accounts.canTransfer()){
            accounts.setBalance(accounts.getBalance().subtract(transferAmount));
        }
    }

    //get account based on principal
    //create account method within the method

     //can't figure out what the setBalance wants below
    //Do i need to add Principal principal in parameters? If so, add to params in AccountsDAO, too.



}
