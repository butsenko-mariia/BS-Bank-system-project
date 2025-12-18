package program.Bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import program.Bank.CapitalizationDeposit;

import java.math.BigDecimal;
import java.time.LocalDate;

class CapitalizationDepositTest {

    @Test
    void testInterestCalculation_CompoundInterest() {
        CapitalizationDeposit deposit = new CapitalizationDeposit();
        deposit.setOriginal_sum(new BigDecimal("1000.00"));
        deposit.setInterest_rate(new BigDecimal("0.12"));

        LocalDate open = LocalDate.now().minusMonths(2);
        deposit.setOpen_date(open);

        deposit.InterestCalculation(LocalDate.now());

        BigDecimal expectedProfit = new BigDecimal("20.10");
        Assertions.assertEquals(expectedProfit, deposit.getProfit());
    }

    @Test
    void testInterestCalculation_LessThenMonth_ShouldBeZero() {
        CapitalizationDeposit deposit = new CapitalizationDeposit();
        deposit.setOriginal_sum(new BigDecimal("1000"));
        deposit.setInterest_rate(new BigDecimal("0.12"));

        deposit.setOpen_date(LocalDate.now().minusDays(15));

        deposit.InterestCalculation(LocalDate.now());

        Assertions.assertEquals(BigDecimal.ZERO, deposit.getProfit());
    }
}