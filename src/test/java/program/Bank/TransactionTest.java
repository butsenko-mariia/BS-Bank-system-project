package program.Bank;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    /**
     * Перевіряє правильність ініціалізації транзакції.
     * Поля ID, дата та час мають заповнюватися автоматично при створенні об'єкта.
     */
    @Test
    void testTransactionInitialization() {
        Transaction tx = new Transaction();

        assertNotNull(tx.getId(), "ID should be generated automatically");
        assertNotNull(tx.getOpen_date(), "The date should be set automatically");
        assertNotNull(tx.getOpen_time(), "The time should be set automatically");
    }

    /**
     * Перевіряє логіку відображення знаку операції.
     * Для додатних сум у string-представленні має з'являтися знак "+".
     */
    @Test
    void testSumSignLogicPositive() {
        Transaction tx = new Transaction();

        tx.setSum(new BigDecimal("100.00"));

        String info = tx.toString();
        assertTrue(info.contains("+100.00"), "For a positive amount there must be a + sign");
    }

    /**
     * Перевіряє логіку відображення знаку операції для витрат.
     * Для від'ємних сум має фіксуватися знак "-".
     */
    @Test
    void testSumSignLogicNegative() {
        Transaction tx = new Transaction();

        tx.setSum(new BigDecimal("-50.00"));

        String info = tx.toString();
        assertTrue(info.contains("--50.00"), "For a negative sum, the sign - must be assigned.");
    }

    /**
     * Перевіряє заборону на створення пустих транзакцій.
     * Сума транзакції не може дорівнювати нулю.
     */
    @Test
    void testZeroSumNotAllowed() {
        Transaction tx = new Transaction();

        assertThrows(IllegalArgumentException.class, () -> {
            tx.setSum(BigDecimal.ZERO);
        }, "Transaction amount cannot be zero.");
    }

    /**
     * Перевіряє правильність вводу коду валюти.
     * Валюта має складатися рівно з 3-х великих літер (наприклад, USD, UAH).
     */
    @Test
    void testCurrencyValidation() {
        Transaction tx = new Transaction();

        assertDoesNotThrow(() -> tx.setCurrency("USD"));

        assertThrows(IllegalArgumentException.class, () -> tx.setCurrency("us"));
        assertThrows(IllegalArgumentException.class, () -> tx.setCurrency("usd"));
        assertThrows(IllegalArgumentException.class, () -> tx.setCurrency("USDT"));
    }

    /**
     * Перевіряє незмінність унікального ідентифікатора (ID).
     * Якщо ID вже встановлено, повторна спроба його задати має викликати помилку.
     */
    @Test
    void testSetIdOnce() {
        Transaction tx = new Transaction();

        assertThrows(IllegalStateException.class, () -> {
            tx.setId();
        }, "You cannot change the ID if it is already set.");
    }
}