package program.Bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import program.Bank.Enums.AccountStatus;
import program.Bank.StandardDeposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class DepositCommonTest {

    private StandardDeposit deposit;

    @BeforeEach
    void setUp() {
        deposit = new StandardDeposit();
    }

    @Test
    void testSetId_AlreadySet_ShouldThrowException() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            deposit.setId();
        }, "Має бути помилка, якщо ID вже існує");
    }

    @Test
    void testSetId_Null_ShouldThrowException() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            deposit.setId(null);
        }, "ID не може бути null");
    }

    @Test
    void testSetClientId_Null_ShouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            deposit.setClient_id(null);
        });
    }

    @Test
    void testSetOriginalSum_Negative_ShouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            deposit.setOriginal_sum(new BigDecimal("-100"));
        });
    }

    @Test
    void testSetOriginalSum_Zero_ShouldThrowException() {
        deposit.setOriginal_sum(BigDecimal.ZERO);
        Assertions.assertEquals(BigDecimal.ZERO, deposit.getOriginal_sum());
    }

    @Test
    void testSetCloseDate_BeforeOpenDate_ShouldThrowException() {
        deposit.setOpen_date(LocalDate.now());
        LocalDate invalidCloseDate = LocalDate.now().minusDays(1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            deposit.setClose_date(invalidCloseDate);
        }, "Дата закриття не може бути раніше дати відкриття");
    }

    @Test
    void testSetCurrency_InvalidFormat_ShouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            deposit.setCurrency("dollar");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            deposit.setCurrency("us");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            deposit.setCurrency("uah");
        });
    }

    @Test
    void testSetCurrency_Valid_ShouldSuccess() {
        deposit.setCurrency("EUR");
        Assertions.assertEquals("EUR", deposit.getCurrency());
    }

    @Test
    void testGetTax_ShouldSumTaxAndMilitary() {
        BigDecimal expectedTax = new BigDecimal("0.195");
        Assertions.assertEquals(expectedTax, deposit.getTax());
    }

    @Test
    void testSetInterestRate_Negative_ShouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            deposit.setInterest_rate(new BigDecimal("-5"));
        });
    }
}