package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountsController {

    private UserDao userDAO;
    private AccountsDAO accountsDAO;
    private TransferDao transferDao;

    public AccountsController(AccountsDAO accountDAO, UserDao userDAO, TransferDao transferDao){
        this.accountsDAO = accountDAO;
        this.userDAO = userDAO;
        this.transferDao = transferDao;
    }


    @RequestMapping(value = "accounts/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(Principal p) {
        String loggedInUserName = p.getName();
        int loggedInUserId = userDAO.findIdByUsername(loggedInUserName);
        return accountsDAO.getAccountBalance(loggedInUserId);
    }

}
