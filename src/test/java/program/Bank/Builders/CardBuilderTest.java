package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Card;
import program.Bank.Enums.AccountStatus;
import program.Bank.Enums.CardType;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CardBuilderTest {

    @Test
    void testBuild_Valid() {
        UUID clientId = UUID.randomUUID();
        String number = "1234567890123456";

        Card card = CardBuilder.create()
                .client_id(clientId)
                .card_number(number)
                .card_type(CardType.NATIONAL_CASHBACK)
                .balance(BigDecimal.ZERO)
                .currency("USD")
                .status(AccountStatus.ACTIVE)
                .build();

        assertNotNull(card);
        assertNotNull(card.getId());
        assertEquals(clientId, card.getClient_id());
        assertEquals(number, card.getCard_number());
        assertEquals(CardType.NATIONAL_CASHBACK, card.getCard_type());
    }

    @Test
    void testBuild_MissingCardNumber_ShouldThrowException() {
        CardBuilder builder = CardBuilder.create()
                .client_id(UUID.randomUUID());

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void testBuild_MissingClientId_ShouldThrowException() {
        CardBuilder builder = CardBuilder.create()
                .card_number("1111222233334444");

        assertThrows(IllegalStateException.class, builder::build);
    }
}