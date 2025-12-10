package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.AccountStatus;
import program.Bank.Card;
import program.Bank.CardType;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CardBuilderTest {

    // 1. ПЕРЕВІРКА ЛАНЦЮЖКА ВИКЛИКІВ (FLUENT INTERFACE)
    @Test
    void testBuild_CreatesCardWithCorrectFields() {
        UUID clientId = UUID.randomUUID();

        // Act
        Card card = CardBuilder.create()
                .client_id(clientId)
                .card_type(CardType.JUNIOR)
                .balance(new BigDecimal("1000.50"))
                .currency("USD")
                .status(AccountStatus.BLOCKED)
                .build();

        // Assert
        assertNotNull(card.getId(), "ID карти має бути згенеровано");
        assertEquals(clientId, card.getClient_id());
        assertEquals(CardType.JUNIOR, card.getCard_type());
        assertEquals(new BigDecimal("1000.50"), card.getBalance());
        assertEquals("USD", card.getCurrency());
        assertEquals(AccountStatus.BLOCKED, card.getStatus());
    }

    // 2. ПЕРЕВІРКА ДЕФОЛТНИХ ЗНАЧЕНЬ (ті, що в методі createNew)
    @Test
    void testCreate_SetsDefaultValues() {
        Card card = CardBuilder.create().build();

        assertNotNull(card.getId(), "ID має бути");
        assertEquals(BigDecimal.ZERO, card.getBalance(), "Баланс має бути 0");
        assertEquals("GRN", card.getCurrency(), "Валюта за замовчуванням - GRN");
        assertEquals(AccountStatus.OPEN, card.getStatus(), "Статус за замовчуванням - OPEN");
    }

    // 3. ПЕРЕВІРКА РОБОТИ RESET
    @Test
    void testReset_CreatesNewInstance() {
        CardBuilder builder = CardBuilder.create();

        Card card1 = builder
                .currency("USD")
                .build();

        builder.reset();

        Card card2 = builder.build();

         assertNotSame(card1, card2);
        assertNotEquals(card1.getId(), card2.getId());
        assertEquals("USD", card1.getCurrency());
        assertEquals("GRN", card2.getCurrency(), "Після reset валюта мала скинутися на дефолтну");
    }

    // 4. ПЕРЕВІРКА ВАЛІДАЦІЇ ЧЕРЕЗ БУДІВЕЛЬНИК
    @Test
    void testBuilder_Validation() {
         assertThrows(IllegalArgumentException.class, () -> {
            CardBuilder.create()
                    .client_id(null)
                    .build();
        });
    }

    @Test
    void testBuilder_WithNullBalance() {
        // Що станеться, якщо не встановити баланс?
        Card card = CardBuilder.create()
                .currency("USD")
                .build();

        assertEquals(BigDecimal.ZERO, card.getBalance(),
                "Якщо баланс не встановлено, має бути 0");
    }
    // В CardBuilderTest
    @Test
    void testBuilder_OrderDoesNotMatter() {
        Card card1 = CardBuilder.create()
                .currency("USD")
                .balance(new BigDecimal("100"))
                .build();

        Card card2 = CardBuilder.create()
                .balance(new BigDecimal("100"))
                .currency("USD")
                .build();

        assertEquals(card1.getCurrency(), card2.getCurrency());
        assertEquals(card1.getBalance(), card2.getBalance());
    }

}