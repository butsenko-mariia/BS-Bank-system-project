package program.Bank;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    // --- ТЕСТИ ДЛЯ ВАЛЮТИ ---

    @Test
    void testCurrency_Valid() {
        Card card = new Card();
        card.setCurrency("UAH");
        assertEquals("UAH", card.getCurrency());
    }

    @Test
    void testCurrency_Invalid_LowerCase() {
        Card card = new Card();
        // Валюта маленькими літерами має викликати помилку
        assertThrows(IllegalArgumentException.class, () -> card.setCurrency("uah"));
    }

    @Test
    void testCurrency_Invalid_Length() {
        Card card = new Card();
        assertThrows(IllegalArgumentException.class, () -> card.setCurrency("USDT"));
    }

    // --- ТЕСТИ ДЛЯ ТИПУ КАРТИ ---

    @Test
    void testCardType_Valid() {
        Card card = new Card();
        card.setCard_type(CardType.JUNIOR);
        assertEquals(CardType.JUNIOR, card.getCard_type());
    }

    // --- ТЕСТИ ДЛЯ CLIENT ID ---

    @Test
    void testClientId_NotNull() {
        Card card = new Card();
        UUID id = UUID.randomUUID();
        card.setClient_id(id);
        assertEquals(id, card.getClient_id());
    }

    @Test
    void testClientId_Invalid_Null() {
        Card card = new Card();
        assertThrows(IllegalArgumentException.class, () -> card.setClient_id(null));
    }

    @Test
    void testToString_WithNullFields() {
        Card card = new Card();
        String result = card.toString();
        assertNotNull(result);
        // Перевірити, як поводиться з null
    }

    @Test
    void testEquals_SameId() {
        Card card1 = new Card();
        card1.id();
        Card card2 = card1;
        assertEquals(card1, card2);
    }

    @Test
    void testHashCode_Consistency() {
        Card card = new Card();
        card.id();
        int hash1 = card.hashCode();
        int hash2 = card.hashCode();
        assertEquals(hash1, hash2);
    }


}
