package program.Bank;

import program.Bank.Enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public abstract class Deposit implements Account{
    private UUID id;
    private UUID client_id;
    private BigDecimal original_sum;
    private BigDecimal profit;
    private LocalDate open_date;
    private LocalDate close_date;
    public double interest_rate;
    private String currency;
    private AccountStatus status;
    private final double tax_rate = 0.18;
    private final double military_rate = 0.18;

    public UUID getId() {
        return id;
    }
    public void setId() {
        if (id != null) {
            throw  new IllegalStateException("Deposit ID is already set");
        }
        this.id =  UUID.randomUUID();
    }public void setId(UUID id) {
        if (id == null) {
            throw  new NumberFormatException("Deposit ID is null");
        }
        this.id =  id;
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
    public BigDecimal getProfit() {
        this.InterestCalculation(LocalDate.now());
        return profit;
    }
    public void setProfit(BigDecimal profit) {

        if (profit == null || profit.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Balance must be bigger than zero and must not be null");
        this.profit = profit;
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
    public double getTax(){
        return tax_rate + military_rate;
    }
    @Override
    public String toString() {
        return String.format(
                "Deposit id = " + this.id +
                        ", \nClient id = " + this.client_id +
                        ", \nOriginal sum = " + this.original_sum +
                        ", \nCurrent profit = " + this.getProfit() +
                        ", \nOpen date = " + this.open_date +
                        ", \nClose date = " + this.close_date +
                        ", \nInterest rate = " + this.interest_rate +
                        ", \nCurrency = " + this.currency +
                        ", \nStatus = " + this.status
        );
    }
    public void PrintFullInfo() {
        System.out.println(this.toString());
    }
    public void PrintInfo() {
        String info = "#" + this.id + " - " + this.original_sum + " " + this.currency + "(" + this.interest_rate + "%)\n"+
        "Дата закінчення: " + this.close_date + "\n"+
        "Нараховано: " +  this.getProfit();
    }
    abstract public void InterestCalculation(LocalDate date);
    public void TaxSubtract(){

    }
    public void Close(){
        Client current_client = new Client(this.client_id);
        Scanner scanner = new Scanner(System.in);
        if (this.close_date.isBefore(LocalDate.now())) {
            System.out.println("При знятті депозиту раніше його дати закінчення, відсоток нарахувань зіставлятиме 1/5 від початкового відсотку. " +
                    "Ви впевнені що хочете продовжити? Y/N");
            String choice = scanner.next().trim();
            if (choice.equalsIgnoreCase("N")) {
                return;
            }
            else{
                this.setInterest_rate(this.interest_rate/5);
            }
        }
        System.out.println("Оберіть одну з ваших активних карт для нарахування коштів:\n");
        //тут має бути функція яка виводить коротку інформацію про всі активні картки клієнта
        //тут має бути функція яка переводить обраному рахунку оригінальну суму депозита та нараховані відсотки
        // і статус переволить у закритий, видаляє інформацію про депозит з таблиці в бд депозитів та переводить
        // в архівну таблицю
        BigDecimal after_tax_profit = this.getProfit().subtract(this.getProfit().multiply(BigDecimal.valueOfthis.setStatus(AccountStatus.CLOSED(this.getTax())));
        this.setStatus(AccountStatus.CLOSED);

    }
}
