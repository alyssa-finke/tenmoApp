package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;
    private UserDao userDao;
    private AccountsDAO accountsDAO;

    public TransferController(TransferDao transferDao, UserDao userDao, AccountsDAO accountsDAO){
        this.transferDao = transferDao;
        this.userDao = userDao;
        this.accountsDAO = accountsDAO;
    }

@RequestMapping(value = "users", method = RequestMethod.GET)
    public List<User> findAllUsers(){
        return transferDao.findAllUsers();

}

}
