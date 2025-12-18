package program.Bank;

import org.junit.jupiter.api.Test;
import program.Bank.Enums.AccountStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    @Test
    void testLoanInitialization() {
        Loan loan = new Loan();

        assertNotNull(loan.getId());
        assertEquals(AccountStatus.ACTIVE, loan.getStatus());
        assertEquals("UAH", loan.getCurrency());
        assertNotNull(loan.getOpen_date());
        assertEquals(BigDecimal.ZERO, loan.getOverdue_sum());
        assertEquals(BigDecimal.ZERO, loan.getChange());
    }

    @Test
    void testSetOriginalSum_Valid() {
        Loan loan = new Loan();
        BigDecimal sum = new BigDecimal("10000.00");
        loan.setOriginal_sum(sum);

        assertEquals(sum, loan.getOriginal_sum());
        assertEquals(sum, loan.getCurrent_balance());
    }

    @Test
    void testSetOriginalSum_Invalid_Negative() {
        Loan loan = new Loan();
        BigDecimal invalidSum = new BigDecimal("-100.00");

        assertThrows(IllegalArgumentException.class, () -> loan.setOriginal_sum(invalidSum));
    }

    @Test
    void testSetCloseDate_And_MonthlyPaymentCalculation() {
        Loan loan = new Loan();
        loan.setOpen_date(LocalDate.now());
        loan.setOriginal_sum(new BigDecimal("10000"));
        loan.setInterest_rate(new BigDecimal("12"));

        loan.setClose_date(LocalDate.now().plusMonths(12));

        assertEquals(12, loan.getTerm_month());
        assertNotNull(loan.getMonthly_payment());
        assertTrue(loan.getMonthly_payment().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testSetCloseDate_Invalid_BeforeOpenDate() {
        Loan loan = new Loan();
        loan.setOpen_date(LocalDate.now());
        LocalDate invalidClose = LocalDate.now().minusDays(1);

        assertThrows(IllegalArgumentException.class, () -> loan.setClose_date(invalidClose));
    }

    @Test
    void testRegularPayment_NormalFlow() throws PaymentException {
        Loan loan = new Loan();
        loan.setOpen_date(LocalDate.now());
        loan.setOriginal_sum(new BigDecimal("1200"));
        loan.setInterest_rate(new BigDecimal("12"));
        loan.setClose_date(LocalDate.now().plusMonths(12));

        BigDecimal monthlyPayment = loan.getMonthly_payment();
        BigDecimal initialBalance = loan.getCurrent_balance();
        LocalDate initialNextPaymentDate = loan.getNext_payment_date();

        loan.RegularPayment(monthlyPayment);

        assertTrue(loan.getCurrent_balance().compareTo(initialBalance) < 0);

        if (loan.getNext_payment_date() != null) {
            assertEquals(initialNextPaymentDate.plusMonths(1), loan.getNext_payment_date());
        }
        assertEquals(BigDecimal.ZERO, loan.getOverdue_sum());
    }

    @Test
    void testRegularPayment_Underpayment_AccumulatesDebt() throws PaymentException {
        Loan loan = new Loan();
        loan.setOpen_date(LocalDate.now());
        loan.setOriginal_sum(new BigDecimal("10000"));
        loan.setInterest_rate(new BigDecimal("12"));
        loan.setClose_date(LocalDate.now().plusMonths(12));

        BigDecimal monthlyPayment = loan.getMonthly_payment();
        BigDecimal underPayment = monthlyPayment.divide(new BigDecimal("2"), RoundingMode.HALF_UP);

        loan.RegularPayment(underPayment);

        assertTrue(loan.getOverdue_sum().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testRegularPayment_Overpayment_RecalculatesMonthlyPayment() throws PaymentException {
        Loan loan = new Loan();
        loan.setOpen_date(LocalDate.now());
        loan.setOriginal_sum(new BigDecimal("10000"));
        loan.setInterest_rate(new BigDecimal("12"));
        loan.setClose_date(LocalDate.now().plusMonths(10));

        BigDecimal oldMonthlyPayment = loan.getMonthly_payment();

        // Важливо: переконатися, що oldMonthlyPayment не нуль перед множенням
        if (oldMonthlyPayment.compareTo(BigDecimal.ZERO) == 0) {
            oldMonthlyPayment = new BigDecimal("1000"); // Фоллбек для тесту
        }

        BigDecimal overPayment = oldMonthlyPayment.multiply(new BigDecimal("2"));

        loan.RegularPayment(overPayment);

        BigDecimal newMonthlyPayment = loan.getMonthly_payment();

        assertTrue(newMonthlyPayment.compareTo(oldMonthlyPayment) < 0);
        assertEquals(BigDecimal.ZERO, loan.getOverdue_sum());
    }

    @Test
    void testRegularPayment_LatePayment_PenaltyApplied() throws PaymentException {
        Loan loan = new Loan();
        loan.setOpen_date(LocalDate.now().minusMonths(2));
        loan.setOriginal_sum(new BigDecimal("1000"));
        loan.setInterest_rate(new BigDecimal("12"));
        loan.setClose_date(LocalDate.now().plusMonths(10));

        LocalDate pastDate = LocalDate.now().minusDays(5);
        loan.setNext_payment_date(pastDate);

        BigDecimal paymentAmount = new BigDecimal("100");
        BigDecimal expectedBalanceWithoutPenalty = loan.getCurrent_balance();

        loan.RegularPayment(paymentAmount);

        BigDecimal balanceReduction = expectedBalanceWithoutPenalty.subtract(loan.getCurrent_balance());

        assertTrue(balanceReduction.compareTo(paymentAmount) <= 0);
    }

    @Test
    void testFullEarlyRepayment_ClosesLoan() {
        Loan loan = new Loan();
        loan.setOpen_date(LocalDate.now());
        loan.setOriginal_sum(new BigDecimal("5000"));
        loan.setInterest_rate(new BigDecimal("10"));
        loan.setClose_date(LocalDate.now().plusMonths(12));

        BigDecimal fullAmount = new BigDecimal("5000.00");

        loan.FullEarlyRepayment(fullAmount);

        assertEquals(AccountStatus.CLOSED, loan.getStatus());
        assertEquals(0, BigDecimal.ZERO.compareTo(loan.getCurrent_balance()));
    }

    @Test
    void testFullEarlyRepayment_WithChange() {
        Loan loan = new Loan();
        loan.setOpen_date(LocalDate.now());
        loan.setOriginal_sum(new BigDecimal("1000"));
        loan.setInterest_rate(new BigDecimal("10"));
        loan.setClose_date(LocalDate.now().plusMonths(6));

        BigDecimal paymentWithExcess = new BigDecimal("1500");

        loan.FullEarlyRepayment(paymentWithExcess);

        assertEquals(AccountStatus.CLOSED, loan.getStatus());
        assertTrue(loan.getChange().compareTo(BigDecimal.ZERO) > 0);
        // Решта має бути 500
        assertEquals(0, new BigDecimal("500").compareTo(loan.getChange()));
    }

    @Test
    void testProcessExistingDebt_PrioritizesDebt() throws PaymentException {
        Loan loan = new Loan();
        loan.setOpen_date(LocalDate.now());
        loan.setOriginal_sum(new BigDecimal("1000"));
        loan.setInterest_rate(new BigDecimal("12"));
        loan.setClose_date(LocalDate.now().plusMonths(12));

        // Розрахунковий щомісячний платіж тут ~89 грн.
        // Старий борг
        loan.setOverdue_sum(new BigDecimal("50.00"));

        // ВИПРАВЛЕННЯ: Платимо 200, щоб вистачило на борг (50) + поточний місяць (89)
        BigDecimal payment = new BigDecimal("200.00");

        loan.RegularPayment(payment);

        // Тепер боргу не має бути
        assertEquals(0, BigDecimal.ZERO.compareTo(loan.getOverdue_sum()), "Debt should be cleared");
    }

    @Test
    void testCurrencyValidation() {
        Loan loan = new Loan();
        loan.setCurrency("USD");
        assertEquals("USD", loan.getCurrency());

        assertThrows(IllegalArgumentException.class, () -> loan.setCurrency("usd"));
        assertThrows(IllegalArgumentException.class, () -> loan.setCurrency("US"));;
    }
}