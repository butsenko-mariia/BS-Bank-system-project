package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Deposit;
import program.Bank.Enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CapitalizationDepositBuilderTest {

    @Test
    void testBuild_Valid() {
        // Arrange
        UUID clientId = UUID.randomUUID();
        BigDecimal sum = new BigDecimal("5000");
        LocalDate open = LocalDate.now();
        LocalDate close = open.plusMonths(12);
        BigDecimal rate = new BigDecimal("0.1");

        // Act
        Deposit deposit = CapitalizationDepositBuilder.create()
                .client_id(clientId)
                .original_sum(sum)
                .open_date(open)
                .close_date(close)
                .interest_rate(rate)
                .currency("UAH")
                .status(AccountStatus.ACTIVE)
                .build();

        // Assert
        assertNotNull(deposit);
        assertNotNull(deposit.getId()); // ID генерується в конструкторі депозиту
        assertEquals(clientId, deposit.getClient_id());
        assertEquals(sum, deposit.getOriginal_sum());
        assertEquals("UAH", deposit.getCurrency());
    }

    @Test
    void testBuild_MissingFields_ShouldThrowException() {
        // Спробуємо побудувати без client_id та дат
        CapitalizationDepositBuilder builder = CapitalizationDepositBuilder.create()
                .original_sum(new BigDecimal("1000"))
                .currency("USD");

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void testReset() {
        CapitalizationDepositBuilder builder = new CapitalizationDepositBuilder();
        builder.client_id(UUID.randomUUID());

        builder.reset();

        // Після reset об'єкт очищується, тому build має викинути помилку через null поля
        assertThrows(IllegalStateException.class, builder::build);
    }
}