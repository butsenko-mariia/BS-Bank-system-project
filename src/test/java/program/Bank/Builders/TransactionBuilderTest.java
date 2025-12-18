package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Transaction;
import program.Bank.Enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionBuilderTest {

    @Test
    void testBuildTransaction() {
        // Arrange & Act
        Transaction tx = TransactionBuilder.create()
                .id(UUID.randomUUID()) // Обов'язкове поле
                .account_id_from(UUID.randomUUID()) // Обов'язкове поле
                .account_id_to(UUID.randomUUID())   // Обов'язкове поле
                .sum(new BigDecimal("500.00"))
                .currency("UAH") // Використовуємо коректну валюту
                .operation_info("Payment for services")
                .open_date(LocalDate.now()) // Обов'язкове поле
                .open_time(LocalTime.now()) // Обов'язкове поле
                .status(TransactionStatus.COMPLETED)
                .build();

        // Assert
        assertEquals(0, new BigDecimal("500.00").compareTo(tx.getSum()));
        assertEquals("UAH", tx.getCurrency());
        assertEquals("Payment for services", tx.getOperation_info());
        assertEquals(TransactionStatus.COMPLETED, tx.getStatus());
        assertNotNull(tx.getOpen_date());
    }

    @Test
    void testBuilderCreatesNewInstances() {
        // Для успішного білду треба заповнити всі поля, навіть якщо перевіряємо тільки унікальність
        UUID from = UUID.randomUUID();
        UUID to = UUID.randomUUID();

        Transaction tx1 = TransactionBuilder.create()
                .id(UUID.randomUUID())
                .account_id_from(from).account_id_to(to)
                .open_date(LocalDate.now()).open_time(LocalTime.now())
                .currency("UAH").status(TransactionStatus.COMPLETED)
                .sum(new BigDecimal("100"))
                .build();

        Transaction tx2 = TransactionBuilder.create()
                .id(UUID.randomUUID())
                .account_id_from(from).account_id_to(to)
                .open_date(LocalDate.now()).open_time(LocalTime.now())
                .currency("UAH").status(TransactionStatus.COMPLETED)
                .sum(new BigDecimal("100"))
                .build();

        assertNotSame(tx1, tx2, "The builder should create different objects");
        assertNotEquals(tx1.getId(), tx2.getId(), "Transactions should have different IDs");
    }

    @Test
    void testBuild_Valid() {
        UUID fromId = UUID.randomUUID();
        UUID toId = UUID.randomUUID();
        BigDecimal sum = new BigDecimal("500.00");

        Transaction transaction = TransactionBuilder.create()
                .id(UUID.randomUUID())
                .account_id_from(fromId)
                .account_id_to(toId)
                .sum(sum)
                .currency("UAH")
                .open_date(LocalDate.now())
                .open_time(LocalTime.now())
                .status(TransactionStatus.COMPLETED)
                .build();

        assertNotNull(transaction);
        assertEquals(fromId, transaction.getAccount_id_from());
        assertEquals(toId, transaction.getAccount_id_to());
        assertEquals(sum, transaction.getSum());
    }


    @Test
    void testBuild_MissingAccountIdFrom_ShouldThrowException() {
        TransactionBuilder builder = TransactionBuilder.create()
                .id(UUID.randomUUID())
                .account_id_to(UUID.randomUUID())
                .sum(new BigDecimal("100"))
                .currency("USD")
                .open_date(LocalDate.now())
                .open_time(LocalTime.now());

        assertThrows(IllegalStateException.class, builder::build);
    }
}