package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Card;
import program.Bank.Enums.AccountStatus;
import program.Bank.Enums.CardType;

import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CardBuilderTest {
    /**
     * Перевіряє створення картки з певним балансом і типом.
     */
    @Test
    void testBuildCard() {
        Card card = CardBuilder.create()
                .card_number("1111-2222-3333-4444")
                .card_type(CardType.UNIVERSAL)
                .balance(new BigDecimal("1000.00"))
                .status(AccountStatus.ACTIVE)
                .build();

        assertEquals("1111-2222-3333-4444", card.getCard_number());
        assertEquals(CardType.UNIVERSAL, card.getCard_type());
        assertEquals(0, new BigDecimal("1000.00").compareTo(card.getBalance()));
    }

    /**
     * Перевіряє функцію reset().
     * Вона повинна очищати поточний стан білдера і готувати його
     * до створення абсолютно нової картки.
     */
    @Test
    void testResetBuilder() {
        CardBuilder builder = CardBuilder.create();

        Card card1 = builder.card_number("1234").build();

        builder.reset();

        Card card2 = builder.build();

        assertEquals("1234", card1.getCard_number());
        assertNull(card2.getCard_number(), "After reset() the new map should be clean");
        assertNotSame(card1, card2);
    }
}