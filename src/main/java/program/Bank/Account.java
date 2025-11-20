package program.Bank;

import java.util.Scanner;
import java.util.UUID;

public class Card {
    private String card_id;
    private String client_id;
    private AccountType card_type;
    private double balance;
    private String currency;
    private AccountStatus status;

    public Card() {
        Scanner scanner = new Scanner(System.in);

        this.card_id = UUID.randomUUID().toString();
        this.balance = 0;
        this.status = AccountStatus.OPEN;

        System.out.println("Enter owner id");
        this.client_id = scanner.nextLine();
        System.out.println("Enter account type");
        this.card_type = setAccountType(scanner.nextLine());
        System.out.println("Enter account currency");
        this.currency = scanner.nextLine();
    }
    public String getCard_id() {
        return card_id;
    }
    public void setCard_id(String account_id) {
        if (card_id == null || card_id.trim().isEmpty()) {
            throw new IllegalArgumentException("Card ID cannot be empty.");
        }
        this.card_id = card_id;
    }

    public AccountType getCard_type() {
        return card_type;
    }
    public void setCard_type(AccountType card_type) {

        boolean ifMatchType = false;
        for (AccountType type : AccountType.values()) {
            if (card_type == type) {
                this.card_type = card_type;
                ifMatchType = true;

            }
        }
        if (!ifMatchType) {
            throw new IllegalArgumentException("Invalid account type.");
        }
    }
    private AccountType setCardType(String line) {
        switch (line) {
            case "UNIVERSAL":
                return AccountType.UNIVERSAL;
            case "PAYMENT":
                return AccountType.PAYMENT;
            case "JUNIOR":
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be empty.");
        }
        if (!currency.matches("^[A-Z]{3}$")) {
            throw new IllegalArgumentException("Currency must be 3 uppercase letters (e.g., USD, EUR).");
        }
        this.currency = currency;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {

        boolean ifMatchStatus = false;
        for (AccountStatus accountStatus : AccountStatus.values()) {
            if (status == accountStatus) {
                this.status = status;
                ifMatchStatus = true;

            }
        }
        if (!ifMatchStatus) {
            throw new IllegalArgumentException("Invalid account status.");
        }
    }

}
public interface Account {
    public String toString();
    public void Print();
}
