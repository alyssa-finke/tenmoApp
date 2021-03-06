package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountsService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.view.ConsoleService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
    private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
    private static final String[] LOGIN_MENU_OPTIONS = {LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};
    private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
    private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
    private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
    private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};

    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private AccountsService accountsService;
    private TransferService transferService;

    public static void main(String[] args) {
        App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new AccountsService(API_BASE_URL), new TransferService(API_BASE_URL));
        app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, AccountsService accountsService, TransferService transferService) {
        this.console = console;
        this.authenticationService = authenticationService;
        this.accountsService = accountsService;
        this.transferService = transferService;
    }

    public void run() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");

        registerAndLogin();
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
                viewCurrentBalance();
            } else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
                viewTransferHistory();
            } else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
                viewPendingRequests();
            } else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
                sendBucks();
            } else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
                requestBucks();
            } else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else {
                // the only other option on the main menu is to exit
                exitProgram();
            }
        }
    }

    private void viewPendingRequests() {
    }

    Transfer transfer = new Transfer();

    private void viewTransferHistory() {
        //can retrieve info with account ids still working on formatting
        System.out.println("--------------------------------------------");
        System.out.println("Transfer     From     To     Amount");
        System.out.println("ID  ");
        System.out.println("--------------------------------------------");
        for (Transfer transfer : transferService.listAllTransfers(currentUser.getToken())) {
          System.out.println(transfer.getTransferId() + "       " + transfer.getFromUsername() + "     " + transfer.getToUsername() + "     " + "$" + transfer.getTransferAmount());
            //added fromUsername and toUsername and getters to Transfer Class.
            }
        }


    private void viewCurrentBalance() {
        // TODO Auto-generated method stub
        System.out.println("Your current account balance is: $" + accountsService.getAccountBalance(currentUser.getToken()));

    }

    User user = new User(); //declare inside method

    private void sendBucks() {
        // TODO Auto-generated method

        Scanner scanner = new Scanner(System.in);
        int userTo;
        BigDecimal transferAmount;

        System.out.println("--------------------------------------------");
        System.out.println("User ID : Name");
        System.out.println("--------------------------------------------");
        for (User user : accountsService.findAllUsersTransfer(currentUser.getToken())) {
            System.out.println(user.getId() + " : " + user.getUsername());
        }

        System.out.println("\nEnter ID of user you are sending to (0 to cancel):");

        if (scanner.hasNextInt()) {
            userTo = scanner.nextInt();
            scanner.nextLine();
            transfer.setUserTo(userTo);

        } else {
            userTo = 0;
        }
        System.out.println("\nEnter amount:");
        if (scanner.hasNextBigDecimal()) {
            transferAmount = scanner.nextBigDecimal();
            scanner.nextLine();
            transfer.setTransferAmount(transferAmount);

            transferService.createNewTransfer(userTo, transferAmount, currentUser.getToken());//Call method to transfer

            /// if menuSelection = userId then that becomes accountToUserId
            //then input $ becomes transferAmount
        } else {
            transferAmount = BigDecimal.valueOf(0);
        }

    }

    private void requestBucks() {
        // TODO Auto-generated method stub

    }

    private void exitProgram() {
        System.exit(0);
    }

    private void registerAndLogin() {
        while (!isAuthenticated()) {
            String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
            if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
                register();
            } else {
                // the only other option on the login menu is to exit
                exitProgram();
            }
        }
    }

    private boolean isAuthenticated() {
        return currentUser != null;
    }

    private void register() {
        System.out.println("Please register a new user account");
        boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                authenticationService.register(credentials);
                isRegistered = true;
                System.out.println("Registration successful. You can now login.");
            } catch (AuthenticationServiceException e) {
                System.out.println("REGISTRATION ERROR: " + e.getMessage());
                System.out.println("Please attempt to register again.");
            }
        }
    }

    private void login() {
        System.out.println("Please log in");
        currentUser = null;
        while (currentUser == null) //will keep looping until user is logged in
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                currentUser = authenticationService.login(credentials);
            } catch (AuthenticationServiceException e) {
                System.out.println("LOGIN ERROR: " + e.getMessage());
                System.out.println("Please attempt to login again.");
            }
        }
    }

    private UserCredentials collectUserCredentials() {
        String username = console.getUserInput("Username");
        String password = console.getUserInput("Password");
        return new UserCredentials(username, password);
    }
}
