package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Client;
import program.Bank.Enums.ClientStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientBuilderTest {
    /**
     * Перевіряє побудову клієнта через ланцюжок методів (Fluent Interface).
     * Ми створюємо об'єкт з багатьма полями і перевіряємо, чи всі вони збереглися.
     */
    @Test
    void testBuildFullClient() {
        Client client = ClientBuilder.create()
                .full_name("Ivanenko Ivan")
                .mobile_phone("+380991234567")
                .sex("M")
                .date_of_birth(LocalDate.of(1990, 1, 1))
                .status(ClientStatus.ACTIVE)
                .build();

        assertNotNull(client.getId(), "ID must be generated");
        assertEquals("Ivanenko Ivan", client.getFull_name());
        assertEquals("+380991234567", client.getMobile_phone());
        assertEquals("M", client.getSex());
        assertEquals(ClientStatus.ACTIVE, client.getStatus());
    }

    /**
     * Перевіряє, чи працює валідація всередині білдера.
     * Якщо ми передаємо некоректний телефон у білдер, він має викликати
     * ту ж помилку, що й звичайний сетер клієнта.
     */
    @Test
    void testBuilderValidationPropagation() {
        assertThrows(IllegalArgumentException.class, () -> {
            ClientBuilder.create()
                    .full_name("Ivan")
                    .mobile_phone("000") // Невірний формат
                    .build();
        }, "The builder should throw an error if an invalid phone number is passed");
    }
}