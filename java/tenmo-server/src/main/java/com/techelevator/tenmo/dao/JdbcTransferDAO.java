package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDAO;
    private AccountsDAO accountsDAO;

    //Constructor
    public JdbcTransferDAO(JdbcTemplate jdbcTemplate, AccountsDAO accountsDAO, UserDao userDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountsDAO = accountsDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance " +
                "FROM account WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    private Account creditBalance(Account account) {
        String sql = "UPDATE accounts " +
                "SET balance = balance + ?" +
                "WHERE user_id = ?;";
        jdbcTemplate.update(sql, account.getBalance(), account.getUserId());
        return account;
    }

    private Account debitBalance(Account account) {
        String sql = "UPDATE accounts " +
                "SET balance = balance - ?" +
                "WHERE user_id = ?;";
        jdbcTemplate.update(sql, account.getBalance(), account.getUserId());
        return account;
    }

    @Override
    public void createNewTransfer(Transfer transfer, String fromUsername) {
        int userId = userDAO.findIdByUsername(fromUsername);
        if (accountsDAO.getAccountBalance(userId).compareTo(transfer.getTransferAmount()) == 1) {
            String sql = "INSERT transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                    "VALUES (?, 2, ?, ?, ?);";
            jdbcTemplate.update(sql, transfer);
            creditBalance(getAccountByUserId(transfer.getAccountFrom()));
            debitBalance(getAccountByUserId(transfer.getAccountFrom()));
        //    jdbcTemplate.update(sql, debitBalance(getAccountByUserId(transfer.getAccountFrom())));
        //    jdbcTemplate.update(sql, creditBalance(getAccountByUserId(transfer.getAccountTo())));
        }
    }
        // #5 see all transfers ive sent or received
    @Override
    public List<Transfer> listMyTransfers(int userId){
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, account_from, account_to, amount " +
                "FROM transfers " +
                "JOIN accounts ON transfers.account_from = accounts.account_id " +
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transfer = mapRowToListMyTransfers(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    // #6 see transfer details for transfer_id
    @Override
    public Transfer viewTransferDetails(int transferId) {
        Transfer transferDetails = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount" +
                "fROm TRANSFERS" +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if(results.next()) {
            transferDetails = mapRowToViewTransferDetails(results);
        }
        return transferDetails;
    }


    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
        //made this map for viewTransfers method
    private Transfer mapRowToListMyTransfers(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setTransferAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
    private Transfer mapRowToViewTransferDetails(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferType(rs.getInt("transfer_type_id"));
        transfer.setTransferStatus(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setTransferAmount(rs.getBigDecimal("amount"));
        return transfer;
    }


//do i have the money to tranfer? if so, subtract from mine, add to yours, and insert a row into transfer
//run sql insert into database
//create 1 method to insert transfer
//1 method to update account balances
//then call for both

//client should just create a request


}

