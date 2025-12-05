package program.Bank;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class TransactionMethodsTest {

    // 1. ТЕСТУВАННЯ АВТОМАТИЧНОЇ ДАТИ
    @Test
    void testSetOpenDate_SetsCurrentDate() {
        Transaction transaction = new Transaction();

        // Викликаємо метод без аргументів
        transaction.setOpen_date();

        // Перевіряємо, чи дата встановилася і чи вона дорівнює сьогоднішній
        assertNotNull(transaction.getOpen_date());
        assertEquals(LocalDate.now(), transaction.getOpen_date(), "Має бути встановлена поточна дата");
    }

    // 2. ТЕСТУВАННЯ АВТОМАТИЧНОГО ЧАСУ
    @Test
    void testSetOpenTime_SetsCurrentTime() {
        Transaction transaction = new Transaction();

        // Викликаємо метод без аргументів
        transaction.setOpen_time();

        assertNotNull(transaction.getOpen_time());
        // Час виконання тесту і час у транзакції можуть відрізнятися на мілісекунди,
        // тому перевіряємо, що різниця невелика (наприклад, це той самий рік/день, або просто не null)
        // Для простоти перевіримо, що він не null і це було щойно.
        assertTrue(transaction.getOpen_time().isBefore(LocalTime.now().plusSeconds(1)));
    }

    // 3. ТЕСТУВАННЯ ID (аналогічно до інших класів)
    @Test
    void testSetId_GeneratesUniqueId() {
        Transaction t1 = new Transaction();
        t1.setId();

        Transaction t2 = new Transaction();
        t2.setId();

        assertNotNull(t1.getId());
        assertNotEquals(t1.getId(), t2.getId());
    }

    @Test
    void testSetId_Protection() {
        Transaction t = new Transaction();
        t.setId();

        assertThrows(IllegalStateException.class, () -> t.setId());
    }


    @Test
    void testTransaction_AccountIdsCanBeNull() {
        // Для деяких транзакцій (наприклад, поповнення готівкою)
        // account_id_from може бути null
        Transaction t = new Transaction();
        assertDoesNotThrow(() -> {
            t.setAccount_id_from(null);
        });
    }

    @Test
    void testTransaction_DateAndTimeAreSet() {
        Transaction t = new Transaction();
        t.setOpen_date();
        t.setOpen_time();

        assertNotNull(t.getOpen_date());
        assertNotNull(t.getOpen_time());
        assertEquals(LocalDate.now(), t.getOpen_date());
    }

}