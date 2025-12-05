package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Client;
import program.Bank.ClientStatus;
import static org.junit.jupiter.api.Assertions.*;

class ClientBuilderTest {

    // 1. ПЕРЕВІРКА "ЛАНЦЮЖКА" (FLUENT INTERFACE)
    @Test
    void testBuild_CreatesClientWithCorrectFields() {
        Client client = ClientBuilder.create()
                .full_name("Петренко Тарас")
                .mobile_phone("+380991112233")
                .sex("M")
                .passport_number("AB123456")
                .build();

        // Assert
        assertNotNull(client.getId(), "ID має генеруватися автоматично");
        assertEquals("Петренко Тарас", client.getFull_name());
        assertEquals("+380991112233", client.getMobile_phone());
        assertEquals("M", client.getSex());
        assertEquals("AB123456", client.getPassport_number());
    }

    // 2. ПЕРЕВІРКА ДЕФОЛТНИХ ЗНАЧЕНЬ
    @Test
    void testCreate_SetsDefaultValues() {
         Client client = ClientBuilder.create().build();

        assertNotNull(client.getId(), "ID має бути згенеровано за замовчуванням");
        assertEquals(ClientStatus.ACTIVE, client.getStatus(), "Статус за замовчуванням має бути ACTIVE");
    }

    // 3. ПЕРЕВІРКА РОБОТИ МЕТОДУ RESET
    @Test
    void testReset_CreatesNewInstance() {
        ClientBuilder builder = ClientBuilder.create();

         Client client1 = builder
                .full_name("Клієнт Перший")
                .build();

         builder.reset();

         Client client2 = builder
                .full_name("Клієнт Другий")
                .build();

        // Перевірки
        assertNotEquals(client1.getId(), client2.getId(), "Клієнти повинні мати різні ID");
        assertNotEquals(client1.getFull_name(), client2.getFull_name());
        assertNotSame(client1, client2, "Це мають бути два різні об'єкти в пам'яті");
    }

    // 4. ПЕРЕВІРКА ВАЛІДАЦІЇ (ЧИ БУДІВЕЛЬНИК КИДАЄ ПОМИЛКИ КЛАСУ CLIENT)
    @Test
    void testBuilder_PropagatesValidationErrors() {
         assertThrows(IllegalArgumentException.class, () -> {
            ClientBuilder.create()
                    .mobile_phone("000")
                    .build();
        });
    }
    @Test
    void testBuilder_EmptyBuild() {
        // Що станеться, якщо нічого не встановити?
        assertDoesNotThrow(() -> {
            Client client = ClientBuilder.create().build();
            assertNotNull(client.getId());
        });
    }
}