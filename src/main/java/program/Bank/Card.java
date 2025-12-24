package program.Bank;

import program.Bank.Enums.AccountStatus;
import program.Bank.Enums.CardType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.util.UUID;

public class Card implements Account{
    private static final Logger log = LogManager.getLogger(Card.class);

    private UUID id;
    private UUID client_id;
    private String card_number;
    private CardType card_type;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;

    public Card() {
        log.debug("Creating a new Card instance");
        this.setId();
        this.balance = BigDecimal.ZERO;
        this.currency = "UAH";
        this.status = AccountStatus.ACTIVE;
        log.info("Card created: balance = 0, currency = UAH, status = ACTIVE");
    }

    public Card(UUID id) {
        log.debug("Creating a Card instance with an existing ID: {}", id);
        this.setId(id);
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        log.info("Generation of a new ID card");
        if (id != null) {
            log.error("Card ID already set: {}", id);
            throw  new IllegalStateException("Card ID is already set");
        }
        this.id = UUID.randomUUID();
        log.info("Card ID successfully generated: {}", id);
    }

    public void setId(UUID id) {
        log.info("Setting existing card ID: {}", id);
        if (id == null) {
            log.error("Attempt to set null as card ID");
            throw  new NumberFormatException("ID must be not null");
        }
        this.id =  id;
        log.debug("Card ID successfully set");
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        log.info("Setting card number: {}", card_number);
        this.card_number = card_number;
        log.debug("Card number successfully set");
    }
    public CardType getCard_type() {
        return card_type;
    }

    public void setCard_type(CardType card_type) {
        log.info("Setting card type: {}", card_type);
        if (card_type == null) {
            log.error("Card type is null");
            throw new IllegalArgumentException("Card type cannot be null.");
        }
        this.card_type = card_type;
        log.debug("Card type successfully set: {}", this.card_type);
    }

    public UUID getClient_id() {
        return client_id;
    }

    public void setClient_id(UUID client_id) {
        log.info("Setting client ID for card: {}", client_id);
        if (client_id == null) {
            log.error("Client ID is null");
            throw new IllegalArgumentException("Client ID cannot be null.");
        }
        this.client_id = client_id;
        log.debug("Client ID successfully set for card");
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        log.info("Setting card balance: {}", balance);
        this.balance = balance;
        log.debug("Card balance successfully set");
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        log.info("Setting card currency: {}", currency);
        if (currency == null || currency.trim().isEmpty()) {
            log.error("Currency is empty or null");
            throw new IllegalArgumentException("Currency cannot be empty.");
        }
        if (!currency.matches("^[A-Z]{3}$")) {
            log.error("Invalid currency format: {}", currency);
            throw new IllegalArgumentException("Currency must be 3 uppercase letters (e.g., USD, EUR).");
        }
        this.currency = currency;
        log.debug("Card currency successfully set: {}", this.currency);
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        log.info("Setting card status: {}", status);
        if (status == null) {
            log.error("Card status is null");
            throw new IllegalArgumentException("Card status cannot be null.");
        }
        this.status = status;
        log.debug("Card status successfully set: {}", this.status);
    }

    @Override
    public String toString() {
        log.debug("Converting card data to text representation");
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
        log.info("Printing full info for card ID: {}", id);
        System.out.println(this);
        log.debug("Full card info printed");
    }

    public void PrintInfo(){
        log.info("Printing short info for card ID: {}", id);
        System.out.println("#" + this.id + " - " + this.card_number + "\n" +
                "card type - " + this.getCard_type().toString() + "\n" +
                "Current balance: " + this.balance + " " + this.currency + "\n" +
                "Current status: " + this.status);
        log.debug("Short card info printed");
    }

    public void Withdraw(BigDecimal amount){
        log.info("Attempting to withdraw funds from card ID: {}. Amount: {}", id, amount);
        if (this.getStatus() == AccountStatus.BLOCKED){
            log.error("Attempting to withdraw funds from blocked card ID: {}", id);
            throw new IllegalStateException("Cannot withdraw funds from a blocked card.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Invalid withdrawal amount: {}", amount);
            throw new IllegalArgumentException("Amount must be positive.");
        }
        if (amount.compareTo(this.balance) > 0) {
            log.error("Insufficient funds on card ID: {}. Balance: {}, attempt to withdraw: {}", id, this.balance, amount);
            throw new ArithmeticException("Insufficient funds in the account for withdrawal.");
        }
        this.balance = this.balance.subtract(amount);
        log.info("Funds successfully withdrawn from card ID: {}. Amount: {}. New balance: {}", id, amount, this.balance);
    }

    public void TopUp(BigDecimal amount){
        log.info("Topping up card ID: {}. Amount: {}", id, amount);
        if (this.getStatus() == AccountStatus.BLOCKED){
            log.error("Attempting to top up blocked card ID: {}", id);
            throw new IllegalStateException("Cannot top up a blocked card.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Invalid top-up amount: {}", amount);
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.balance = this.balance.add(amount);
        log.info("Card ID: {} successfully topped up. Amount: {}. New balance: {}", id, amount, this.balance);
    }

    public void Block(){
        log.info("Blocking card ID: {}", id);
        this.setStatus(AccountStatus.BLOCKED);
        log.warn("Card ID: {} is blocked", id);
    }

    public BigDecimal Close(){
        log.info("Closing card ID: {}", id);
        if (this.getStatus() == AccountStatus.BLOCKED) {
            log.error("Attempting to close blocked card ID: {}", id);
            throw new IllegalStateException("Cannot close a blocked card.");
        }
        this.setStatus(AccountStatus.CLOSED);
        BigDecimal remainder = this.balance; // Saving remainder
        log.info("Card ID: {} closed. Funds returned: {}", id, remainder);
        this.balance = BigDecimal.ZERO;
        log.debug("Card balance reset to 0");

        return remainder;
    }
}