package program.Bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import program.Bank.StandardDeposit;

import java.math.BigDecimal;
import java.time.LocalDate;

class StandardDepositTest {

    @Test
    void testInterestCalculation_SimpleInterest() {
        StandardDeposit deposit = new StandardDeposit();
        deposit.setOriginal_sum(new BigDecimal("1000.00"));
        deposit.setInterest_rate(new BigDecimal("0.10")); // 10%

        LocalDate open = LocalDate.now().minusDays(365);
        deposit.setOpen_date(open);

        deposit.InterestCalculation(LocalDate.now());

        BigDecimal expectedProfit = new BigDecimal("100.00");
        Assertions.assertEquals(expectedProfit, deposit.getProfit());
    }

    @Test
    void testInterestCalculation_ZeroDays() {
        StandardDeposit deposit = new StandardDeposit();
        deposit.setOriginal_sum(new BigDecimal("1000"));
        deposit.setInterest_rate(new BigDecimal("0.10"));

        deposit.setOpen_date(LocalDate.now());

        deposit.InterestCalculation(LocalDate.now());

        Assertions.assertEquals(0, BigDecimal.ZERO.compareTo(deposit.getProfit()));
    }
}