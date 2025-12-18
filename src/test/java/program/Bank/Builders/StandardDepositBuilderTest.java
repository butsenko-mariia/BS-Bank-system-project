package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Deposit;
import program.Bank.Enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StandardDepositBuilderTest {

    @Test
    void testBuild_Valid() {
        UUID clientId = UUID.randomUUID();

        Deposit deposit = StandardDepositBuilder.create()
                .client_id(clientId)
                .original_sum(new BigDecimal("10000"))
                .open_date(LocalDate.now())
                .close_date(LocalDate.now().plusMonths(6))
                .interest_rate(new BigDecimal("0.08"))
                .currency("UAH")
                .status(AccountStatus.ACTIVE)
                .build();

        assertNotNull(deposit);
        assertNotNull(deposit.getId());
        assertEquals(clientId, deposit.getClient_id());
        assertEquals(new BigDecimal("0.08"), deposit.getInterest_rate());
    }

    @Test
    void testBuild_MissingOriginalSum_ShouldThrowException() {
        StandardDepositBuilder builder = StandardDepositBuilder.create()
                .client_id(UUID.randomUUID())
                .currency("UAH")
                .interest_rate(new BigDecimal("0.05"))
                .open_date(LocalDate.now())
                .close_date(LocalDate.now().plusYears(1));

        assertThrows(IllegalStateException.class, builder::build);
    }
}