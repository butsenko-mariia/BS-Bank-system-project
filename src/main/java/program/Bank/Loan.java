package program.Bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.Enums.AccountStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

class PaymentException extends Exception {
    public PaymentException(String message) {
        super(message);
    }
}

public class Loan implements Account {
    private UUID id;
    private UUID client_id;
    private BigDecimal original_sum;
    private BigDecimal current_balance;
    private LocalDate open_date;
    private LocalDate close_date;
    private LocalDate next_payment_date;
    private long term_month;
    private int payment_day;
    private BigDecimal monthly_payment;
    private BigDecimal interest_rate;
    private BigDecimal monthly_rate;
    private String currency;
    private AccountStatus status;
    private BigDecimal overdue_sum;
    private BigDecimal change;

    // Статичний логер
    private static final Logger log = LogManager.getLogger(Loan.class);

    public Loan(){
        this.setId();
        this.setOpen_date();
        this.overdue_sum = BigDecimal.ZERO;
        this.change = BigDecimal.ZERO;
        this.setStatus(AccountStatus.ACTIVE);
        this.setCurrency("UAH");
        log.info("Created new Loan with ID: {}.", this.id);
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        this.id =  UUID.randomUUID();
    }

    public UUID getClient_id() {
        return client_id;
    }

    public void setClient_id(UUID client_id) {
        if (client_id == null) {
            log.error("Error: Client ID is null.");
            throw new IllegalArgumentException("Client id cannot be null");
        }
        this.client_id = client_id;
        log.debug("Client ID set for Loan: {}.", client_id);
    }

    public BigDecimal getOriginal_sum() {
        return original_sum;
    }

    public void setOriginal_sum(BigDecimal original_sum) {
        if (original_sum == null || original_sum.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Error: Invalid original sum.");
            throw new IllegalArgumentException("Sum must be positive");
        }
        this.original_sum = original_sum;
        this.current_balance = original_sum;
        log.debug("Original sum set: {}.", original_sum);
    }

    public BigDecimal getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(BigDecimal current_balance) {
        if (current_balance == null || current_balance.compareTo(this.original_sum) > 0) {
            log.error("Error: Invalid balance set attempt.");
            throw new IllegalArgumentException("Balance must be bigger than original sum");
        }
        this.current_balance = current_balance;
    }

    public LocalDate getOpen_date() {
        return open_date;
    }

    public void setOpen_date() {
        this.open_date = LocalDate.now();
        this.payment_day = this.open_date.getDayOfMonth();
        this.setNext_payment_date();
    }

    public void setOpen_date(LocalDate open_date) {
        if (open_date == null) {
            log.error("Error: Open date is null.");
            throw new IllegalArgumentException("Open date cannot be null");
        }
        this.open_date = open_date;
        this.payment_day = this.open_date.getDayOfMonth();
        this.setNext_payment_date();
        log.debug("Open date set: {}.", open_date);
    }

    public LocalDate getClose_date() {
        return close_date;
    }

    public void setClose_date(LocalDate close_date) {
        if (close_date == null || close_date.isBefore(this.open_date)) {
            log.error("Error: Invalid close date.");
            throw new IllegalArgumentException("Close date must be before open date and can not be null");
        }
        this.close_date = close_date;
        this.term_month = ChronoUnit.MONTHS.between(this.open_date, this.close_date);
        this.CalculateMonthlyPayment(original_sum, (int) term_month);
        log.debug("Close date set: {}. Term calculated: {} months.", close_date, term_month);
    }

    public LocalDate getNext_payment_date() {
        return next_payment_date;
    }

    public void setNext_payment_date() {
        this.next_payment_date = this.open_date;
        this.next_payment_date = this.next_payment_date.plusMonths(1);
        this.next_payment_date = this.next_payment_date.withDayOfMonth(this.payment_day);
    }

    public long getTerm_month() {
        return term_month;
    }

    public void setTerm_month(int term_month) {
        if (term_month <= 0) {
            log.error("Error: Invalid term month.");
            throw new IllegalArgumentException("Term month must be positive");
        }
    }

    public int getPayment_day() {
        return payment_day;
    }

    public void setPayment_day(int payment_day) {
        if (payment_day <= 0 || payment_day > 28) {
            log.error("Error: Invalid payment day.");
            throw new IllegalArgumentException("Payment day must be positive and less than 28");
        }
    }

    public BigDecimal getMonthly_payment() {
        return monthly_payment;
    }

    public void setMonthly_payment(BigDecimal monthly_payment) {
        if (monthly_payment.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Error: Invalid monthly payment.");
            throw new IllegalArgumentException("Monthly payment must be positive");
        }
        this.monthly_payment = monthly_payment;
    }

    public void setMonthly_payment(){
        this.monthly_payment = CalculateMonthlyPayment(this.original_sum, (int) this.term_month);
        log.debug("Monthly payment set: {}.", this.monthly_payment);
    }

    public BigDecimal getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(BigDecimal interest_rate) {
        if (interest_rate.compareTo(BigDecimal.ZERO) <= 0){
            log.error("Error: Invalid interest rate.");
            throw new IllegalArgumentException("Interest rate must be positive");
        }
        this.interest_rate = interest_rate;
        this.setMonthly_rate();
        log.debug("Interest rate set: {}. Monthly rate calculated.", interest_rate);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency == null || currency.trim().isEmpty()) {
            log.error("Error: Currency is null or empty.");
            throw new IllegalArgumentException("Currency cannot be empty.");
        }
        if (!currency.matches("^[A-Z]{3}$")) {
            log.error("Error: Invalid currency.");
            throw new IllegalArgumentException("Currency must be 3 uppercase letters (e.g., USD, EUR).");
        }
        this.currency = currency;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
        log.debug("Loan status changed to: {}.", status);
    }

    public void setMonthly_rate(){
        this.monthly_rate = this.interest_rate.divide(new BigDecimal(1200), 4, RoundingMode.HALF_UP);
        log.debug("Monthly rate set: {}.", this.monthly_rate);
    }

    public void Fetch(){
        log.debug("Fetching loan data from DB for ID: {}.", this.id);
        // DB Logic
    }

    @Override
    public String toString() {
        return String.format(
                "Loan id = " + id +
                        ", \nClient id = " + client_id +
                        ", \nOriginal sum = " + original_sum +
                        ", \nCurrent balance = " + current_balance +
                        ", \nOpen date = " + open_date +
                        ", \nClose date = " + close_date +
                        ", \nTerm (months) = " + term_month +
                        ", \nPayment day = " + payment_day +
                        ", \nNext payment day = " + next_payment_date +
                        ", \nInterest rate = " + interest_rate +
                        ", \nCurrency = " + currency +
                        ", \nStatus = " + status
        );
    }

    public void PrintFullInfo() {
        String info = this.toString();
        System.out.println(info);
        log.debug("Full loan info printed for ID: {}.", this.id);
    }

    public void PrintInfo(){
        String info = "#" + this.id + " - " + this.original_sum + " " + this.currency + " (" + this.interest_rate + "%)\n" +
                "Залишок: " + this.current_balance + " "+ this.currency +"\n" +
                "Щомісячний платіж: "+ this.monthly_payment+ " " + this.currency +"\n" +
                "Наступний платіж: " + this.next_payment_date;

        System.out.println(info);
        log.info("Short Loan Info printed:\n{}", info);
    }

    public BigDecimal CalculateMonthlyPayment(BigDecimal sum, int months){
        BigDecimal temp = (this.monthly_rate.add(BigDecimal.valueOf(1))).pow(months);
        BigDecimal payment = sum.multiply(this.monthly_rate.multiply(temp).divide(temp.subtract(BigDecimal.valueOf(1)), 4, RoundingMode.HALF_UP));
        log.debug("Monthly payment calculated: {}.", payment);
        return payment;
    }

    public void CalculateNextPaymentDate(){
        this.next_payment_date = this.next_payment_date.plusMonths(1);
        log.debug("Next payment date calculated: {}.", this.next_payment_date);
    }

    public void RegularPayment(BigDecimal payment) throws PaymentException {
        if (payment == null || payment.compareTo(BigDecimal.ZERO) <= 0){
            log.error("Error: Attempted zero or null payment.");
            throw new PaymentException("Payment date must be positive");
        }

        log.info("Starting RegularPayment processing. Amount: {}.", payment);

        try {
            payment = processOverdueFine(payment);
            payment = processExistingDebt(payment);
            payment = processMonthlyInterest(payment);
            processPrincipalBody(payment);
        }
        catch (PaymentException e){
            String msg = "Увага (Exception): " + e.getMessage();
            System.out.println(msg);
            log.warn(msg);
        }
    }

    private BigDecimal processOverdueFine(BigDecimal payment) throws PaymentException {
        if (this.next_payment_date.isBefore(LocalDate.now())){
            long days_overdue = ChronoUnit.DAYS.between(this.next_payment_date, LocalDate.now());
            BigDecimal daily_rate = this.interest_rate.divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP);
            BigDecimal days_overdue_interest = this.current_balance.multiply(daily_rate).multiply(BigDecimal.valueOf(days_overdue));

            String message = ("Нараховано пеню за " + days_overdue +
                    " днів:  " + days_overdue_interest + " " + this.currency + ".");

            System.out.println(message);
            log.warn(message);

            if (payment.compareTo(days_overdue_interest) < 0){
                BigDecimal debt = days_overdue_interest.subtract(payment);
                this.overdue_sum = this.overdue_sum.add(debt);

                String debtMsg = "Внесеної суми недостатньо щоб покрити прострочку. Коштів не вистачило на покриття пені. Залишок боргу: " + debt;
                log.warn(debtMsg);
                throw new PaymentException(debtMsg);
            }

            return payment.subtract(days_overdue_interest);
        }
        return payment;
    }

    private BigDecimal processExistingDebt(BigDecimal payment) throws PaymentException {
        if (this.overdue_sum.compareTo(BigDecimal.ZERO) > 0) {
            if (payment.compareTo(this.overdue_sum) < 0){
                this.overdue_sum = this.overdue_sum.subtract(payment);
                String message = ("Внесеної суми недостатньо щоб покрити весь борг за минулі штрафи. Покриється лише " +
                        "частина боргу. Поточний борг тепер зіставлятиме - " + this.overdue_sum + ".");

                System.out.println(message);
                log.warn(message);
                throw new PaymentException(message);
            }

            BigDecimal remaining = payment.subtract(this.overdue_sum);
            this.overdue_sum = BigDecimal.ZERO;

            String message = ("Старий борг за прострочку погашено повністю.");
            System.out.println(message);
            log.info(message);

            return remaining;
        }
        return payment;
    }

    private BigDecimal processMonthlyInterest(BigDecimal payment) throws PaymentException {
        BigDecimal interest = current_balance.multiply(monthly_rate);

        if (payment.compareTo(interest) < 0){
            BigDecimal debt_sum = interest.subtract(payment);
            this.overdue_sum = this.overdue_sum.add(debt_sum);

            String message = ("Внесеної суми недостатньо щоб покрити місячний відсоток. Частина відсотків буде" +
                    " погашена, недостаюча сума (" + debt_sum + " " + this.currency +") перейдe в борг.");

            System.out.println(message);
            log.warn(message);
            throw new PaymentException(message);
        }

        CalculateNextPaymentDate();

        return payment.subtract(interest);
    }

    private void processPrincipalBody(BigDecimal payment) {
        BigDecimal interest = current_balance.multiply(monthly_rate);
        BigDecimal plannedBody = this.monthly_payment.subtract(interest);

        if (payment.compareTo(plannedBody) >= 0){
            if (payment.compareTo(this.current_balance) >= 0){
                FullEarlyRepayment(payment);
                return;
            }

            String message = ("Внесена сума перевищує місячний платіж. Переплата піде на погашення тіла. Сума щомісячного платежу буде зменшена.");
            System.out.println(message);
            log.info(message);

            this.current_balance = this.current_balance.subtract(payment);
            long months_left = ChronoUnit.MONTHS.between(this.next_payment_date, this.close_date);
            int term = months_left <= 0 ? 1 : (int) months_left;
            this.monthly_payment = CalculateMonthlyPayment(this.current_balance, term);

            String recalcMsg = ("Щомісячний платіж перераховано: " + this.monthly_payment);
            System.out.println(recalcMsg);
            log.info(recalcMsg);
        }
        else{
            this.current_balance = this.current_balance.subtract(payment);
            BigDecimal debt = plannedBody.subtract(payment);
            this.overdue_sum = this.overdue_sum.add(debt);

            String message = "Внесена сума менша за встановлений місячний платіж. Відсотки та частина кредиту" +
                    " буде погашена, недостаюча сума: " + debt + " перейде в борг.";
            System.out.println(message);
            log.warn(message);
        }
    }

    public void FullEarlyRepayment(BigDecimal payment){
        String message;
        if (payment.compareTo(this.current_balance) >= 0){
            message = "Внесеної суми достатньо для закриття кредиту. Ваш кредит закрито:)";
            System.out.println(message);
            log.info(message);

            if (payment.compareTo(this.current_balance) > 0) {
                this.change = payment.subtract(this.current_balance);
                String changeMsg = "Ваша решта: " + this.change;
                System.out.println(changeMsg);
                log.info(changeMsg);
            }
            this.current_balance = BigDecimal.ZERO;
            this.Close();
        }
        else{
            message = "Внесеної суми недостатньо для закриття кредиту:(. Вам не вистачає: " +
                    this.current_balance.subtract(payment);
            System.out.println(message);
            log.warn(message);
        }
    }

    public void DisplaySchedule(){
        log.info("Displaying payment schedule for Loan {}.", this.id);
        BigDecimal temp_balance = this.current_balance;
        long payment_index =  ChronoUnit.MONTHS.between(this.open_date, this.next_payment_date)+1;
        long months_left = ChronoUnit.MONTHS.between(this.next_payment_date, this.close_date);

        for (int i = 0; i <= months_left; i++){
            BigDecimal interest = temp_balance.multiply(this.monthly_rate);
            BigDecimal body = this.monthly_payment.subtract(interest);
            temp_balance = temp_balance.subtract(body);

            String line = "Щомісячний платіж №"+(payment_index + i)+":\n" +
                    "           Платіж "+this.monthly_payment+" (Відсотки: "+interest+", Тіло: "+body+"). " +
                    "Залишок: "+temp_balance+". Дата виплати: "+this.next_payment_date.plusMonths(i)+".";

            System.out.println(line);
            log.debug(line);
        }
    }

    public void Close(){
        this.status = AccountStatus.CLOSED;
        String msg = "Loan " + this.id + " has been CLOSED.";
        System.out.println(msg);
        log.info(msg);

        if (this.change.compareTo(BigDecimal.ZERO) > 0){
            log.info("Client has change to collect: {}.", this.change);
        }
    }
}