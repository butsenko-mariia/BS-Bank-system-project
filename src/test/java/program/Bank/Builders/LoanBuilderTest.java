package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Enums.AccountStatus;
import program.Bank.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LoanBuilderTest {

    @Test
    void testBuild_Valid() {
        UUID clientId = UUID.randomUUID();
        BigDecimal sum = new BigDecimal("20000");

        Loan loan = LoanBuilder.create()
                .client_id(clientId)
                .original_sum(sum)
                .interest_rate(new BigDecimal("0.15"))
                .open_date(LocalDate.now())
                .close_date(LocalDate.now().plusMonths(24))
                .currency("EUR")
                .status(AccountStatus.ACTIVE)
                .build();

        assertNotNull(loan);
        assertNotNull(loan.getId());
        assertEquals(clientId, loan.getClient_id());
        assertEquals(sum, loan.getOriginal_sum());
        assertEquals("EUR", loan.getCurrency());
    }

    @Test
    void testBuild_MissingCurrency_ShouldUseDefault() {
        LoanBuilder builder = LoanBuilder.create()
                .client_id(UUID.randomUUID())
                .original_sum(new BigDecimal("1000"))
                .interest_rate(new BigDecimal("0.1"))
                .open_date(LocalDate.now())
                .close_date(LocalDate.now().plusMonths(12))
                .status(AccountStatus.ACTIVE);

        Loan loan = builder.build();

        assertNotNull(loan);
        assertEquals("UAH", loan.getCurrency(), "Currency should default to UAH if not provided");
    }

    @Test
    void testBuild_MissingDates_ShouldThrowException() {
        LoanBuilder builder = LoanBuilder.create()
                .client_id(UUID.randomUUID())
                .original_sum(new BigDecimal("1000"))
                .interest_rate(new BigDecimal("0.1"))
                .currency("UAH");

        assertThrows(IllegalStateException.class, builder::build);
    }
}