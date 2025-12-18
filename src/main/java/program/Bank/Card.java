package program.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import program.Bank.Enums.AccountStatus;
import program.Bank.Enums.CardType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.UUID;

public class Card implements Account{
    private static Logger log = LogManager.getLogger(Card.class);

    private UUID id;
    private UUID client_id;
    private String card_number;
    private CardType card_type;
    private BigDecimal balance;
    private String currency;
    private AccountStatus status;

    public Card() {
        log.debug("Створення нового екземпляру Card");
        this.setId();
        this.balance = BigDecimal.ZERO;
        this.currency = "UAH";
        this.status = AccountStatus.ACTIVE;
        log.info("Card створено: баланс = 0, валюта = GRN, статус = ACTIVE");
    }

    public Card(UUID id) {
        log.debug("Створення екземпляру Card з існуючим ID: {}", id);
        this.setId(id);
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        log.info("Генерація нового ID картки");
        if (id != null) {
            log.error("ID картки вже встановлено: {}", id);
            throw  new IllegalStateException("Card ID is already set");
        }
        this.id = UUID.randomUUID();
        log.info("ID картки успішно згенеровано: {}", id);
    }

    public void setId(UUID id) {
        log.info("Встановлення існуючого ID картки: {}", id);
        if (id == null) {
            log.error("Спроба встановити null як ID картки");
            throw  new NumberFormatException("ID must be not null");
        }
        this.id =  id;
        log.debug("ID картки успішно встановлено");
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        log.info("Встановлення номеру картки: {}", card_number);
        this.card_number = card_number;
        log.debug("Номер картки успішно встановлено");
    }
    public CardType getCard_type() {
        return card_type;
    }

    public void setCard_type(CardType card_type) {
        log.info("Встановлення типу картки: {}", card_type);
        if (card_type == null) {
            log.error("Тип картки є null");
            throw new IllegalArgumentException("Card type cannot be null.");
        }
        this.card_type = card_type;
        log.debug("Тип картки успішно встановлено: {}", this.card_type);
    }

    public UUID getClient_id() {
        return client_id;
    }

    public void setClient_id(UUID client_id) {
        log.info("Встановлення ID клієнта для картки: {}", client_id);
        if (client_id == null) {
            log.error("ID клієнта є null");
            throw new IllegalArgumentException("Client ID cannot be null.");
        }
        this.client_id = client_id;
        log.debug("ID клієнта успішно встановлено для картки");
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        log.info("Встановлення балансу картки: {}", balance);
        this.balance = balance;
        log.debug("Баланс картки успішно встановлено");
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        log.info("Встановлення валюти картки: {}", currency);
        if (currency == null || currency.trim().isEmpty()) {
            log.error("Валюта порожня або null");
            throw new IllegalArgumentException("Currency cannot be empty.");
        }
        if (!currency.matches("^[A-Z]{3}$")) {
            log.error("Невірний формат валюти: {}", currency);
            throw new IllegalArgumentException("Currency must be 3 uppercase letters (e.g., USD, EUR).");
        }
        this.currency = currency;
        log.debug("Валюту картки успішно встановлено: {}", this.currency);
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        log.info("Встановлення статусу картки: {}", status);
        if (status == null) {
            log.error("Статус картки є null");
            throw new IllegalArgumentException("Card status cannot be null.");
        }
        this.status = status;
        log.debug("Статус картки успішно встановлено: {}", this.status);
    }

    @Override
    public String toString() {
        log.debug("Перетворення даних картки в текстове представлення");
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
        log.info("Виведення повної інформації про картку ID: {}", id);
        System.out.println(this.toString());
        log.debug("Повну інформацію про картку виведено");
    }

    public void PrintInfo(){
        log.info("Виведення короткої інформації про картку ID: {}", id);
        System.out.println("#" + this.id + " - " + this.card_number + "\n" +
                "тип карти - " + this.getCard_type().toString() + "\n" +
                "Поточний баланс: " + this.balance + " " + this.currency);
        log.debug("Коротку інформацію про картку виведено");
    }

    public void Withdraw(BigDecimal amount){
        log.info("Спроба зняття коштів з картки ID: {}. Сума: {}", id, amount);
        if (this.getStatus() == AccountStatus.BLOCKED){
            log.error("Спроба зняття коштів з заблокованої картки ID: {}", id);
            throw new IllegalStateException("Неможливо зняти кошти з заблокованої корти.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Невірна сума для зняття: {}", amount);
            throw new IllegalArgumentException("Amount must be positive.");
        }
        if (amount.compareTo(this.balance) > 0) {
            log.error("Недостатньо коштів на картці ID: {}. Баланс: {}, спроба зняти: {}", id, this.balance, amount);
            throw new ArithmeticException("Суми на рахунку не достатньо для зняття коштів.");
        }
        this.balance = this.balance.subtract(amount);
        log.info("Кошти успішно знято з картки ID: {}. Сума: {}. Новий баланс: {}", id, amount, this.balance);
    }

    public void TopUp(BigDecimal amount){
        log.info("Поповнення картки ID: {}. Сума: {}", id, amount);
        if (this.getStatus() == AccountStatus.BLOCKED){
            log.error("Спроба поповнення заблокованої картки ID: {}", id);
            throw new IllegalStateException("Неможливо поповнити кошти на заблоковану корту.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Невірна сума для поповнення: {}", amount);
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.balance = this.balance.add(amount);
        log.info("Картку ID: {} успішно поповнено. Сума: {}. Новий баланс: {}", id, amount, this.balance);
    }

    public void Block(){
        log.info("Блокування картки ID: {}", id);
        this.setStatus(AccountStatus.BLOCKED);
        log.warn("Картку ID: {} заблоковано", id);
    }

    public BigDecimal Close(){
        log.info("Закриття картки ID: {}", id);
        if (this.getStatus() == AccountStatus.BLOCKED) {
            log.error("Спроба закриття заблокованої картки ID: {}", id);
            throw new IllegalStateException("Неможливо закрити заблоковану картку.");
        }
        this.setStatus(AccountStatus.CLOSED);
        BigDecimal remainder = this.balance; // Запам'ятовуємо решту
        log.info("Картку ID: {} закрито. Повернуто коштів: {}", id, remainder);
        this.balance = BigDecimal.ZERO;
        log.debug("Баланс картки скинуто до 0");

        return remainder;
    }
}