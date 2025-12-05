package program.Bank;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    // --- ТЕСТИ ДЛЯ ІМЕНІ ---

    @Test
    void testFullName_Valid() {
        Client client = new Client();
        client.setFull_name("Шевченко Тарас");
        assertEquals("Шевченко Тарас", client.getFull_name());
    }

    @Test
    void testFullName_Invalid_Numbers() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            client.setFull_name("Taras123");
        });
        assertTrue(exception.getMessage().contains("cannot contain numbers"));
    }

    @Test
    void testFullName_Invalid_Empty() {
        Client client = new Client();
        assertThrows(IllegalArgumentException.class, () -> client.setFull_name(""));
    }

    // --- ТЕСТИ ДЛЯ ТЕЛЕФОНУ ---

    @Test
    void testMobilePhone_Valid() {
        Client client = new Client();
        client.setMobile_phone("+380991234567");
        assertEquals("+380991234567", client.getMobile_phone());
    }

    @Test
    void testMobilePhone_Invalid_Format() {
        Client client = new Client();
        assertThrows(IllegalArgumentException.class, () -> {
            client.setMobile_phone("0991234567");
        });
    }

    // --- ТЕСТИ ДЛЯ СТАТІ ---

    @Test
    void testSex_Valid_CaseInsensitive() {
        Client client = new Client();
        client.setSex("m");
        assertEquals("M", client.getSex());
    }

    @Test
    void testSex_Invalid() {
        Client client = new Client();
        assertThrows(IllegalArgumentException.class, () -> client.setSex("Human"));
    }

    // --- ТЕСТИ ДЛЯ ПАСПОРТА ---

    @Test
    void testPassport_Valid() {
        Client client = new Client();
        client.setPassport_number("AB123456");
        assertEquals("AB123456", client.getPassport_number());
    }

    @Test
    void testPassport_Invalid_Null() {
        Client client = new Client();
        assertThrows(IllegalArgumentException.class, () -> client.setPassport_number(null));
    }
}
