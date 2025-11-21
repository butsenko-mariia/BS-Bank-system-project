package program.Bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Deposit implements Account{
    private UUID id;
    private UUID client_id;
    private BigDecimal original_sum;
    private BigDecimal current_balance;
    private LocalDate open_date;
    private LocalDate close_date;
    public double interest_rate;
    private String currency;
    private AccountStatus status;

    public Deposit() {

    }

    public UUID getId() {
        return id;
    }
    public void setId() {
        if (id != null) {
            throw  new IllegalStateException("Deposit ID is already set");
        }
        this.id =  UUID.randomUUID();
    }
    public UUID getClient_id() {
        return client_id;
    }
    public void setClient_id(UUID client_id) {
        //додати перевірку на існування клієнта
        if (client_id == null)
            throw new IllegalArgumentException("Client id cannot be null");
        this.client_id = client_id;
    }
    public BigDecimal getOriginal_sum() {
        return original_sum;
    }
    public void setOriginal_sum(BigDecimal original_sum) {
        if (original_sum == null || original_sum.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Sum must be positive");
        this.original_sum = original_sum;
    }
    public BigDecimal getCurrent_balance() {
        return current_balance;
    }
    public void setCurrent_balance(BigDecimal current_balance) {

        if (current_balance == null || current_balance.compareTo(this.original_sum) > 0)
            throw new IllegalArgumentException("Balance must be bigger than original sum");
        this.current_balance = current_balance;
    }
    public LocalDate getOpen_date() {
        return open_date;
    }
    public void setOpen_date() {
        this.open_date = LocalDate.now();
    }
    public void setOpen_date(LocalDate open_date) {
        this.open_date = open_date;
    }
    public LocalDate getClose_date() {
        return close_date;
    }
    public void setClose_date(LocalDate close_date) {
        if (close_date == null || close_date.isBefore(this.open_date))
            throw new IllegalArgumentException("Close date must be before open date and can not be null");
        this.close_date = close_date;
    }
    public double getInterest_rate() {
        return interest_rate;
    }
    public void setInterest_rate(double interest_rate) {
        if (interest_rate <= 0){
            throw new IllegalArgumentException("Interest rate must be positive");
        }
        this.interest_rate = interest_rate;
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
        boolean if_match_status = false;
        for (var account_status: AccountStatus.values()){
            if (status == account_status) {
                this.status = account_status;
                if_match_status = true;
            }
        }
        if (!if_match_status) {
            throw new IllegalArgumentException("AccountStatus must match TransactionStatus.");
        }
    }
    @Override
    public String toString() {
        return String.format(
                "Deposit id = " + id +
                        ", \nClient id = " + client_id +
                        ", \nOriginal sum = " + original_sum +
                        ", \nCurrent balance = " + current_balance +
                        ", \nOpen date = " + open_date +
                        ", \nClose date = " + close_date +
                        ", \nInterest rate = " + interest_rate +
                        ", \nCurrency = " + currency +
                        ", \nStatus = " + status
        );
    }
    public void Print() {
        System.out.println(this.toString());
    }

}
