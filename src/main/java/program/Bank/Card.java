package program.Bank;

import program.Bank.Enums.AccountStatus;
import program.Bank.Enums.CardType;

import java.math.BigDecimal;
import java.util.UUID;

public class Card implements Account{
    private UUID id;
    private UUID client_id;
    private String card_number;
    private CardType card_type;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;

    public Card() {
        this.setId();
        this.balance = BigDecimal.ZERO;
        this.currency = "GRN";
        this.status = AccountStatus.ACTIVE;
    }
    public Card(UUID id) {
        this.setId(id);
    }

    public UUID getId() {
        return id;
    }
    public void setId() {
        if (id != null) {
            throw  new IllegalStateException("Card ID is already set");
        }
        this.id = UUID.randomUUID();
    }
    public void setId(UUID id) {
        if (id == null) {
            throw  new NumberFormatException("ID must be not null");
        }
        this.id =  id;
    }
    public String getCard_number() {
        return card_number;
    }
    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }
    public CardType getCard_type() {
        return card_type;
    }
    public void setCard_type(CardType card_type) {
        if (card_type == null) {
            throw new IllegalArgumentException("Card type cannot be null.");
        }
        this.card_type = card_type;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
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
        if (status == null) {
            throw new IllegalArgumentException("Card status cannot be null.");
        }
        this.status = status;
    }

    public void Fetch(){
        //тут має бути метод який підтягує всю інформацію про об'єкт з бази даних за його ід та задає полям
        // даного екземпляру класу значення
    }

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

    public void PrintFullInfo() {
        System.out.println(this.toString());
    }

    public void PrintInfo(){
        System.out.println("#" + this.id + " - " + this.card_number + "\n" +
                "тип карти - " + this.getCard_type().toString() + "\n" +
                "Поточний баланс: " + this.balance + " " + this.currency);
    }

    public void Withdraw(BigDecimal amount){
        if (this.getStatus() == AccountStatus.BLOCKED){
            throw new IllegalStateException("Неможливо зняти кошти з заблокованої корти.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new ArithmeticException("Суми на рахунку не достатньо для зняття коштів.");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void TopUp(BigDecimal amount){
        if (this.getStatus() == AccountStatus.BLOCKED){
            throw new IllegalStateException("Неможливо поповнити кошти на заблоковану корту.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.balance = this.balance.add(amount);
    }

    public void Block(){
        this.setStatus(AccountStatus.BLOCKED);
    }

    public BigDecimal Close(){
        if (this.getStatus() == AccountStatus.BLOCKED) {
            throw new IllegalStateException("Неможливо закрити заблоковану картку.");
        }
        this.setStatus(AccountStatus.CLOSED);
        BigDecimal remainder = this.balance; // Запам'ятовуємо решту
        this.balance = BigDecimal.ZERO;
        return remainder;
    }
}