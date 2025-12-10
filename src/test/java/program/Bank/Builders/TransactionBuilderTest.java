package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Transaction;
import program.Bank.TransactionStatus;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TransactionBuilderTest {

    @Test
    void testBuild_SetsAutoFields() {
        // Act
        Transaction transaction = TransactionBuilder.create()
                .sum(new BigDecimal("500.00"))
                .currency("USD")
                .build();

        // Assert
        assertNotNull(transaction.getId(), "ID транзакції має бути");
        assertNotNull(transaction.getOpen_date(), "Дата має бути автозаповнена");
        assertNotNull(transaction.getOpen_time(), "Час має бути автозаповнений");
        assertEquals(TransactionStatus.COMPLETED, transaction.getStatus(), "Дефолтний статус — COMPLETED");
        assertEquals("USD", transaction.getCurrency());
    }

    @Test
    void testChaining_ComplexTransaction() {
        Transaction transaction = TransactionBuilder.create()
                .sum(new BigDecimal("100"))
                .operation_type("TRANSFER")
                .currency("UAH")
                .status(TransactionStatus.CANCELLED)
                .build();

        assertEquals(TransactionStatus.CANCELLED, transaction.getStatus());
        assertEquals("TRANSFER", transaction.getOperation_type());
    }

    @Test
    void testReset_CreatesNewTransaction() {
        TransactionBuilder builder = TransactionBuilder.create();

        Transaction t1 = builder.sum(new BigDecimal("100")).build();
        builder.reset();
        Transaction t2 = builder.sum(new BigDecimal("200")).build();

        assertNotEquals(t1.getId(), t2.getId());
        assertNotEquals(t1.getSum(), t2.getSum());
    }
}