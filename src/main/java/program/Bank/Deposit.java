package program.Bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.Enums.AccountStatus;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Deposit implements Account {

    private UUID id;
    private UUID client_id;
    private BigDecimal original_sum;
    private BigDecimal profit;
    private LocalDate open_date;
    private LocalDate close_date;
    public BigDecimal interest_rate;
    private String currency;
    private AccountStatus status;
    private BigDecimal tax_rate = new BigDecimal("0.18");
    private BigDecimal military_rate = new BigDecimal("0.015");
    private static final Logger log = LogManager.getLogger(Deposit.class);

    public UUID getId() {
        return id;
    }

    public void setId() {
        if (id != null) {
            log.error("Error occurred: trying to set an existing ID.");
            throw new IllegalStateException("Deposit ID is already set.");
        }
        this.id = UUID.randomUUID();
        log.debug("Deposit ID was randomly set: {}.", id);
    }

    public void setId(UUID id) {
        if (id == null) {
            log.error("Error occurred: trying to set null ID.");
            throw new NumberFormatException("Deposit ID must not be null.");
        }
        this.id = id;
        log.debug("Deposit ID was set: {}.", id);
    }

    public UUID getClient_id() {
        return client_id;
    }

    public void setClient_id(UUID client_id) {
        if (client_id == null) {
            log.error("Error occurred: trying to set null client's ID.");
            throw new IllegalArgumentException("Client id cannot be null.");
        }
        this.client_id = client_id;
        log.debug("Client's ID was set: {}.", client_id);
    }

    public BigDecimal getOriginal_sum() {
        return original_sum;
    }

    public void setOriginal_sum(BigDecimal original_sum) {
        if (original_sum == null || original_sum.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Error occurred: trying to set original sum null or bellow the zero.");
            throw new IllegalArgumentException("Sum must be positive.");
        }
        this.original_sum = original_sum;
        log.debug("Original deposit sum was set: {}.", original_sum);
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        if (profit == null) {
            log.error("Error occurred: trying to set deposit profit null.");
            throw new IllegalArgumentException("Balance must not be null.");
        }
        this.profit = profit;
        log.debug("Deposit profit was set: {}.", profit);
    }

    public LocalDate getOpen_date() {
        return open_date;
    }

    public void setOpen_date() {
        this.open_date = LocalDate.now();
        log.debug("Open date was set with today's date: {}.", open_date);
    }

    public void setOpen_date(LocalDate open_date) {
        this.open_date = open_date;
        log.debug("Open date was set: {}.", open_date);
    }

    public LocalDate getClose_date() {
        return close_date;
    }

    public void setClose_date(LocalDate close_date) {
        if (close_date == null || close_date.isBefore(this.open_date)) {
            log.error("Error occurred: trying to set close date null or before open date.");
            throw new IllegalArgumentException("Close date must be after open date and can not be null.");
        }
        this.close_date = close_date;
        log.debug("Close date was set: {}.", close_date);
    }

    public BigDecimal getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(BigDecimal interest_rate) {
        if (interest_rate.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Error occurred: trying to set interest rate bellow the zero.");
            throw new IllegalArgumentException("Interest rate must be positive.");
        }
        this.interest_rate = interest_rate;
        log.debug("Interest rate was set: {}.", interest_rate);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency == null || currency.trim().isEmpty()) {
            log.error("Error occurred: trying to set currency null.");
            throw new IllegalArgumentException("Currency cannot be empty.");
        }
        if (!currency.matches("^[A-Z]{3}$")) {
            log.error("Error occurred: trying to set currency invalid.");
            throw new IllegalArgumentException("Currency must be 3 uppercase letters (e.g., USD, EUR).");
        }
        this.currency = currency;
        log.debug("Currency was set: {}.", currency);
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
        log.debug("Status was set: {}.", status);
    }

    public BigDecimal getTax() {
        return tax_rate.add(military_rate);
    }
    public BigDecimal getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(BigDecimal tax_rate) {
        this.tax_rate = tax_rate;
    }

    public BigDecimal getMilitary_rate() {
        return military_rate;
    }

    public void setMilitary_rate(BigDecimal military_rate) {
        this.military_rate = military_rate;
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
        System.out.println(this);
        log.debug("Object information was printed: {}.", this);
    }

    public void PrintInfo() {
        this.InterestCalculation(LocalDate.now());
        log.debug("Current profit was calculated in 'printInfo method': {}.", this.profit);
        String info = "#" + this.id + " - " + this.original_sum + " " + this.currency + "(" + (this.interest_rate.multiply(BigDecimal.valueOf(100))) + "%)\n" +
                "Дата закінчення: " + this.close_date + "\n" +
                "Нараховано: " + this.profit;
        log.debug("Object information in 'printInfo method' was printed: {}.", info);
    }

    abstract public void InterestCalculation(LocalDate date);

    public BigDecimal Close(boolean confirm_early_close) {
        log.info("Starting close procedure for Deposit {}.", this.id);
        this.InterestCalculation(LocalDate.now());
        log.debug("Current profit was calculated in 'Close method': {}.", this.profit);

        if (this.close_date.isAfter(LocalDate.now())) {
            log.warn("Attempting early closure for Deposit {}. Planned close date: {}.", this.id, this.close_date);

            if (!confirm_early_close) {
                String mes = "Early closure cancelled by user for Deposit " + this.id + ".";
                log.info(mes);
                System.out.println(mes);
                return null;
            }
            String mes = "A penalty rate has been applied due to early termination.";
            System.out.println(mes);
            log.debug(mes);
            BigDecimal reduced_rate = this.interest_rate.divide(BigDecimal.valueOf(5), 4, RoundingMode.HALF_UP);
            log.info("Rate reduced from {} to {}", this.interest_rate, reduced_rate);
            this.setInterest_rate(reduced_rate);
            log.info(mes);
            System.out.println(mes);
            this.InterestCalculation(LocalDate.now());
        }

        BigDecimal currentProfit = (this.getProfit() == null) ? BigDecimal.ZERO : this.getProfit();

        BigDecimal taxAmount = currentProfit.multiply(this.getTax());
        BigDecimal profitAfterTax = currentProfit.subtract(taxAmount);
        BigDecimal totalToPay = this.original_sum.add(profitAfterTax);

        log.info("Closing financial summary for Deposit {}: Profit = {}, Tax = {}, PayOut = {}.",
                this.id, currentProfit, taxAmount, totalToPay);

        this.setStatus(AccountStatus.CLOSED);
        log.info("Deposit {} successfully CLOSED.", this.id);

        return totalToPay;
    }

}