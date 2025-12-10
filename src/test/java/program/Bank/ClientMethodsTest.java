package program.Bank;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ClientMethodsTest {

    // 1. ТЕСТУВАННЯ МЕТОДУ toString()
    @Test
    void testToString_FormatsCorrectly() {
        // Arrange (Підготовка)
        Client client = new Client();
        client.setId(); // Генеруємо ID
        client.setFull_name("Петренко Петро");
        client.setMobile_phone("+380501112233");
        client.setSex("M");

        String result = client.toString();

        assertNotNull(result);
        assertTrue(result.contains("Петренко Петро"), "toString має містити ім'я");
        assertTrue(result.contains("+380501112233"), "toString має містити телефон");
        assertTrue(result.contains("Client status ="), "toString має містити поле статусу");
    }

    // 2. ТЕСТУВАННЯ МЕТОДУ getBalance() (як бізнес-логіки)
    @Test
    void testGetBalance_ReturnsCorrectValue() {
        Client client = new Client();
        assertNull(client.getBalance(), "Новий клієнт має мати невизначений баланс (поки логіка не реалізована)");
    }

    // 3. ТЕСТУВАННЯ РОБОТИ З ID
    @Test
    void testSetId_GeneratesUniqueValues() {
        Client client1 = new Client();
        client1.setId();

        Client client2 = new Client();
        client2.setId();

        // Перевіряємо логіку: два різних клієнти повинні мати різні ID
        assertNotEquals(client1.getId(), client2.getId());
    }

    // 4. ТЕСТУВАННЯ ЗАХИСТУ ID
    @Test
    void testSetId_ThrowsExceptionIfAlreadySet() {
        Client client = new Client();
        client.setId();
        assertThrows(IllegalStateException.class, () -> {
            client.setId();
        });
    }

}