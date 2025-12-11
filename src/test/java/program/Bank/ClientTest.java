package program.Bank;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    /**
     * Перевіряє, що система приймає коректний формат номера телефону.
     * Формат: +380XXXXXXXXX.
     */
    @Test
    void testValidMobilePhone() {
        Client client = new Client();
        assertDoesNotThrow(() -> client.setMobile_phone("+380501234567"));
    }

    /**
     * Перевіряє, що система відхиляє некоректні номери телефонів.
     * Наприклад: без коду країни, з літерами або занадто короткі.
     */
    @Test
    void testInvalidMobilePhone() {
        Client client = new Client();
        assertThrows(IllegalArgumentException.class, () -> {
            client.setMobile_phone("0501234567");
        }, "Phone without country code should be rejected");

        assertThrows(IllegalArgumentException.class, () -> {
            client.setMobile_phone("Hello");
        }, "Phone with letters should be rejected");
    }

    /**
     * Перевіряє валідацію імені клієнта.
     * Ім'я не повинно містити цифр, лише літери.
     */
    @Test
    void testFullNameValidation() {
        Client client = new Client();

        assertThrows(IllegalArgumentException.class, () -> {
            client.setFull_name("Ivan 123");
        }, "Names with numbers should be banned");
    }

}
