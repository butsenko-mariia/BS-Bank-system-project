package program.Bank;

import org.junit.jupiter.api.Test;
import program.Bank.Enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DepositCloseTest {

    @Test
    void testEarlyClose_Confirmed_ShouldApplyPenalty() {
        StandardDeposit deposit = new StandardDeposit();

        deposit.setClient_id(UUID.randomUUID());
        deposit.setOriginal_sum(new BigDecimal("10000"));
        deposit.setInterest_rate(new BigDecimal("0.12"));
        deposit.setCurrency("UAH");
        deposit.setStatus(AccountStatus.ACTIVE);

        deposit.setOpen_date(LocalDate.now().minusMonths(2));
        deposit.setClose_date(LocalDate.now().plusMonths(10));

        try {
            deposit.Close(true);
        } catch (Exception e) {
            fail("Exception thrown during close: " + e.getMessage());
        }

        assertEquals(AccountStatus.CLOSED, deposit.getStatus());
        assertNotNull(deposit.getProfit());
    }

    @Test
    void testNormalClose_ShouldCalculateProfitAndSetStatus() {
        StandardDeposit deposit = new StandardDeposit();

        deposit.setClient_id(UUID.randomUUID());
        deposit.setOriginal_sum(new BigDecimal("10000"));
        deposit.setInterest_rate(new BigDecimal("0.10"));
        deposit.setCurrency("UAH");
        deposit.setStatus(AccountStatus.ACTIVE);

        deposit.setOpen_date(LocalDate.now().minusYears(1));
        deposit.setClose_date(LocalDate.now().minusDays(1));

        deposit.Close(false);

        assertEquals(AccountStatus.CLOSED, deposit.getStatus());
        assertEquals(0, new BigDecimal("1000.00").compareTo(deposit.getProfit()));
    }

    @Test
    void testEarlyClose_NotConfirmed_ShouldCancelClosure() {
        StandardDeposit deposit = new StandardDeposit();

        deposit.setClient_id(UUID.randomUUID());
        deposit.setOriginal_sum(new BigDecimal("5000"));
        deposit.setInterest_rate(new BigDecimal("0.05"));
        deposit.setCurrency("UAH");
        deposit.setStatus(AccountStatus.ACTIVE);

        deposit.setOpen_date(LocalDate.now().minusDays(10));
        deposit.setClose_date(LocalDate.now().plusDays(100));

        deposit.Close(false);

        assertEquals(AccountStatus.ACTIVE, deposit.getStatus());
    }
}