package program.Bank;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CardMethodsTest {

    // 1. ТЕСТУВАННЯ ЛОГІКИ ГЕНЕРАЦІЇ ID
    @Test
    void testId_GeneratesValue() {
        Card card = new Card();
        assertNull(card.getId());

        card.id();
        assertNotNull(card.getId(), "ID має бути згенеровано");
    }

    @Test
    void testId_ThrowsExceptionIfAlreadySet() {
        Card card = new Card();
        card.id();

         assertThrows(IllegalStateException.class, () -> {
            card.id();
        }, "Метод id() має забороняти повторну генерацію");
    }

    // 2. ТЕСТУВАННЯ МЕТОДУ toString()
    @Test
    void testToString_ContainsInfo() {
        Card card = new Card();
        card.id();
        card.setCurrency("USD");
        card.setBalance(new BigDecimal("1500.00"));
        card.setCard_type(CardType.UNIVERSAL);

        String result = card.toString();

        // Перевіряємо, чи потрапили дані в рядок
        assertTrue(result.contains("USD"));
        assertTrue(result.contains("1500.00"));
        assertTrue(result.contains("UNIVERSAL"));
    }


    @Test
    void testCard_BalanceCanBeZero() {
        Card card = new Card();
        card.setBalance(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, card.getBalance());
    }

    @Test
    void testCard_CurrencyCannotBeNull() {
        Card card = new Card();
        assertThrows(IllegalArgumentException.class, () -> {
            card.setCurrency(null);
        });
    }

    @Test
    void testCard_StatusTransition() {
        Card card = new Card();
        card.setStatus(AccountStatus.OPEN);
        card.setStatus(AccountStatus.BLOCKED);

        assertEquals(AccountStatus.BLOCKED, card.getStatus());
    }
}