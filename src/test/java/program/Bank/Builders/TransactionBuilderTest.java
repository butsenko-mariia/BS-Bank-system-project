package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Enums.TransactionStatus;
import program.Bank.Transaction;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TransactionBuilderTest {
    /**
     * Перевіряє створення простої транзакції.
     * Важливо, що білдер дозволяє нам задати лише потрібні поля,
     * а решту (наприклад, час) клас ініціалізує сам.
     */
    @Test
    void testBuildTransaction() {
        Transaction tx = TransactionBuilder.create()
                .sum(new BigDecimal("500.00"))
                .currency("GRN")
                .operation_info("Payment for services")
                .status(TransactionStatus.COMPLETED)
                .build();

        assertEquals(0, new BigDecimal("500.00").compareTo(tx.getSum()));
        assertEquals("GRN", tx.getCurrency());
        assertEquals("Payment for services", tx.getOperation_info());
        assertEquals(TransactionStatus.COMPLETED, tx.getStatus());

         assertNotNull(tx.getOpen_date());
    }

    /**
     * Перевіряє ізольованість об'єктів.
     * Два виклики build() або create() мають створювати різні об'єкти в пам'яті.
     */
    @Test
    void testBuilderCreatesNewInstances() {
        Transaction tx1 = TransactionBuilder.create().sum(new BigDecimal("100")).build();
        Transaction tx2 = TransactionBuilder.create().sum(new BigDecimal("100")).build();

        assertNotSame(tx1, tx2, "The builder should create different objects, not return a reference to the same one");
        assertNotEquals(tx1.getId(), tx2.getId(), "Different transactions must have different IDs");
    }

}