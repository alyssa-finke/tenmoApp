package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.Accounts;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountsController {

    private JdbcUserDao userDAO;
    private AccountsDAO accountsDAO;
 //   private TransfersDAO transfersDAO;

    public AccountsController(AccountsDAO accountDAO, JdbcUserDao userDAO){ //TransfersDAO transfersDAO) {
        this.accountsDAO = accountDAO;
        this.userDAO = userDAO;
//        this.transfersDAO = transfersDAO;
    }


    @RequestMapping(value = "accounts/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(Principal p) {
        String loggedInUserName = p.getName();
        int loggedInUserId = userDAO.findIdByUsername(loggedInUserName);
        return accountsDAO.getAccountBalance(loggedInUserId);
    }

}
