package program.Bank;
import program.Bank.Enums.AccountStatus;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public BigDecimal interest_rate;
    private String currency;
    private AccountStatus status;
    private final BigDecimal tax_rate = new BigDecimal(0.18);
    private final BigDecimal military_rate = new BigDecimal(0.015);

    public UUID getId() {
        return id;
    }

    public void setId() {
        if (id != null) {
            throw new IllegalStateException("Deposit ID is already set");
        }
        this.id = UUID.randomUUID();
    }

    public void setId(UUID id) {
        if (id == null) {
            throw new NumberFormatException("Deposit ID is null");
        }
        this.id = id;
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

    public BigDecimal getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(BigDecimal interest_rate) {
        if (interest_rate.compareTo(BigDecimal.ZERO) <= 0){
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

    public BigDecimal getTax(){
        return tax_rate.add(military_rate);
    }

    @Override
    public String toString() {
        this.InterestCalculation(LocalDate.now());
        return String.format(
                "Deposit id = " + this.id +
                        ", \nClient id = " + this.client_id +
                        ", \nOriginal sum = " + this.original_sum +
                        ", \nCurrent profit = " + this.profit +
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
        this.InterestCalculation(LocalDate.now());
        String info = "#" + this.id + " - " + this.original_sum + " " + this.currency + "(" + (this.interest_rate.multiply(BigDecimal.valueOf(100))) + "%)\n"+
                "Дата закінчення: " + this.close_date + "\n"+
                "Нараховано: " + this.profit;
    }

    abstract public void InterestCalculation(LocalDate date);
    public void Close(boolean confirm_early_close){
        this.InterestCalculation(LocalDate.now());
        Client current_client = new Client(this.client_id);

        if (this.close_date.isAfter(LocalDate.now())) {
            if (!confirm_early_close) {
                System.out.println("Операцію скасовано.");
                return;
            }
            System.out.println("Застосовано штрафну ставку через дострокове розірвання.");
            BigDecimal reduced_rate = this.interest_rate.divide(BigDecimal.valueOf(5), 4, RoundingMode.HALF_UP);
            this.setInterest_rate(reduced_rate);
            this.InterestCalculation(LocalDate.now());
        }

        BigDecimal currentProfit = (this.getProfit() == null) ? BigDecimal.ZERO : this.getProfit();

        BigDecimal taxAmount = currentProfit.multiply(this.getTax());
        BigDecimal profitAfterTax = currentProfit.subtract(taxAmount);
        BigDecimal totalToPay = this.original_sum.add(profitAfterTax);

        System.out.println("----- Закриття депозиту -----");
        System.out.println("Нараховані відсотки: " + currentProfit + " " + this.currency);
        System.out.println("Податок : " + taxAmount + " " + this.currency);
        System.out.println("До виплати клієнту: " + totalToPay + " " + this.currency);

        this.setStatus(AccountStatus.CLOSED);


//тут має бути функція яка переводить обраному рахунку суму депозита та нараховані відсотки
//  видаляє інформацію про депозит з таблиці в бд депозитів та переводить
// в архівну таблицю
    }

    public void Fetch(){
        //тут має бути метод який підтягує всю інформацію про об'єкт з бази даних за його ід та задає полям
        // даного екземпляру класу значення
    }
}