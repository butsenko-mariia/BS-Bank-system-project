package program.Bank;

import java.util.UUID;

public class Card {
    private UUID id;
    private UUID client_id;
    private CardType card_type;
    private double balance;
    private String currency;
    private AccountStatus status;

    public Card() {

    }
    public UUID getId() {
        return id;
    }
    public void setCard_id() {
        if (id != null) {
            throw  new IllegalStateException("Card ID is already set");
        }
        this.id = UUID.randomUUID();
    }

    public CardType getCard_type() {
        return card_type;
    }
    public void setCard_type(CardType card_type) {

        boolean ifMatchType = false;
        for (CardType type : CardType.values()) {
            if (card_type == type) {
                this.card_type = card_type;
                ifMatchType = true;

            }
        }
        if (!ifMatchType) {
            throw new IllegalArgumentException("Invalid account type.");
        }
    }

    public UUID getClient_id() {
        return client_id;
    }

    public void setClient_id(UUID client_id) {
        if (client_id == null) {
            throw new IllegalArgumentException("Client ID cannot be null.");
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

//реалізувати метод стрінг
    @Override
    public String toString() {
        return String.format(
                "Card id = " + id +
                        ", \nClient id = " + client_id +
                        ", \nCard type = " + card_type +
                        ", \nBalance = " + balance +
                        ", \nCurrency = " + currency +
                        ", \nStatus = " + status
        );
    }
    public void Print() {
        System.out.println(this.toString());
    }
}