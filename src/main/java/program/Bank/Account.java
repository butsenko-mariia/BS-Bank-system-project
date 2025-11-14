package program.Bank;

import java.util.Scanner;
import java.util.UUID;

public class Account {
    private String account_id;
    private String client_id;
    private AccountType account_type;
    private double balance;
    private String currency;
    private AccountStatus status;

    public Account() {
        Scanner scanner = new Scanner(System.in);

        this.account_id = UUID.randomUUID().toString();
        this.balance = 0;
        this.status = AccountStatus.OPEN;

        System.out.println("Enter owner id");
        this.client_id = scanner.nextLine();
        System.out.println("Enter account type");
        this.account_type = SetAccountType(scanner.nextLine());
        System.out.println("Enter account currency");
        this.currency = scanner.nextLine();
    }


    private AccountType SetAccountType(String line) {
        switch (line) {
            case "UNIVERSAL":
                return AccountType.UNIVERSAL;
            case "PAYMENT":
                return AccountType.PAYMENT;
            case "JUNIOR ":
                return AccountType.JUNIOR;
            case "ESUPPORT":
                return AccountType.ESUPPORT;
            case "NATIONAL_CASHBACK":
                return AccountType.NATIONAL_CASHBACK;
        }
        return AccountType.UNIVERSAL;
    }


    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        if (client_id == null || client_id.trim().isEmpty()) {
            throw new IllegalArgumentException("Client ID cannot be null or empty.");
        }
        this.client_id = client_id;
    }
    public AccountType getAccount_type() {
        return account_type;
    }

    public void setAccount_type(AccountType account_type) {
        if (account_type == null) {
            throw new IllegalArgumentException("Account type cannot be null.");
        }
        this.account_type = account_type;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = balance;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be empty.");
        }
        if (!currency.matches("^[A-Z]{3}$")) { // наприклад, USD, EUR, UAH
            throw new IllegalArgumentException("Currency must be 3 uppercase letters (e.g., USD, EUR).");
        }
        this.currency = currency;
    }
}