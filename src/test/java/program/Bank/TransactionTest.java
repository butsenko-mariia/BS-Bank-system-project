package program.Bank;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    // --- ТЕСТИ ДЛЯ СУМИ ---

    @Test
    void testSum_Valid() {
        Transaction transaction = new Transaction();
        transaction.setSum(new BigDecimal("100.50"));
        assertEquals(new BigDecimal("100.50"), transaction.getSum());
    }

    @Test
    void testSum_Invalid_Negative() {
        Transaction transaction = new Transaction();
        assertThrows(IllegalArgumentException.class, () -> {
            transaction.setSum(new BigDecimal("-50"));
        });
    }

    // --- ТЕСТИ ДЛЯ СТАТУСУ ---

    @Test
    void testStatus_Valid() {
        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.COMPLETED);
        assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
    }

}