package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
                "FROM accounts WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }
//take in user_id and transfer amount
    private Account creditBalance(Account account, int accountToUserId, BigDecimal transferAmount) {
        String sql = "UPDATE accounts " +
                "SET balance = balance + ?" +
                "WHERE user_id = ?;";
        jdbcTemplate.update(sql, transferAmount, account.getUserId());
        return account;
    }

    private Account debitBalance(Account account, int userId, BigDecimal transferAmount) {
        String sql = "UPDATE accounts " +
                "SET balance = balance - ?" +
                "WHERE user_id = ?;";
        jdbcTemplate.update(sql, transferAmount, account.getUserId());
        return account;
    }

    @Override
    public void createNewTransfer(Transfer transfer, String fromUsername) {
        int userId = userDAO.findIdByUsername(fromUsername);
       int accountId = accountsDAO.getAccountId(userId);
        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal accountBalance = accountsDAO.getAccountBalance(userId);
        int accountToUserId = transfer.getAccountTo();
        int accountToAccountId = accountsDAO.getAccountId(accountToUserId);
        if (accountBalance.compareTo(transferAmount) == 1) {
            String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (?, ?, ?, ?, ?);"; //hard code first two if need to
            jdbcTemplate.update(sql, transfer.getTransferType(), transfer.getTransferStatus(), accountId, accountToAccountId, transfer.getTransferAmount());
            creditBalance(getAccountByUserId(transfer.getAccountTo()), accountToUserId, transferAmount); //
            debitBalance(getAccountByUserId(transfer.getAccountFrom()), userId, transferAmount);


            //    jdbcTemplate.update(sql, debitBalance(getAccountByUserId(transfer.getAccountFrom())));
        //    jdbcTemplate.update(sql, creditBalance(getAccountByUserId(transfer.getAccountTo())));
        }
    }
        // #5 see all transfers ive sent or received// now using loggedInUserId as param here
    @Override
    public List<Transfer> listMyTransfers(int loggedInUserId){
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers " +
                "JOIN accounts ON transfers.account_from = accounts.account_id " +
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, loggedInUserId);
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
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId); //needed to add transferId here
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
        //made this map for viewTransfers method//added transfer type id and transfer status id so they would map
    private Transfer mapRowToListMyTransfers(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferType(rs.getInt("transfer_type_id"));
        transfer.setTransferStatus(rs.getInt("transfer_status_id"));
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




//client should just create a request


}

