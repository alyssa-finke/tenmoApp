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


    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<User> findAllUsers() {
        return userDao.findAllUsersTransfer();

    }
//put in a breakpoint and find out why it's null here
    @RequestMapping(value = "account/transfer", method = RequestMethod.PUT)
    public void transferTransaction(@RequestBody Transfer transfer, Principal p) {
        String loggedInUsername = p.getName();
        transferDao.createNewTransfer(transfer, loggedInUsername);
    }
    // mapping for #5 viewTransfers
    @RequestMapping(value = "account/transfer", method = RequestMethod.GET)
    public List<Transfer> listMyTransfers(@PathVariable int userId) {
        return transferDao.listMyTransfers(userId);
    }

    //mapping for #6 view transfer details
    @RequestMapping(value = "account/transfer/{id}", method = RequestMethod.GET)
    public Transfer viewTransferDetails(@PathVariable int transferId) {
        return transferDao.viewTransferDetails(transferId);
    }
}

    //get account based on principal
    //create account method within the method

     //can't figure out what the setBalance wants below
    //Do i need to add Principal principal in parameters? If so, add to params in AccountsDAO, too.




