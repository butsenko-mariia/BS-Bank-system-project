package program.Bank.Builders;

import org.junit.jupiter.api.Test;
import program.Bank.Client;
import program.Bank.Enums.ClientStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientBuilderTest {

    @Test
    void testBuild_Valid() {
        String fullName = "John Doe";
        String passport = "AB123456";
        String taxNumber = "1234567890";

        Client client = ClientBuilder.create()
                .full_name(fullName)
                .passport_number(passport)
                .individual_tax_number(taxNumber)
                .date_of_birth(LocalDate.of(1990, 1, 1))
                .mobile_phone("+380501112233")
                .status(ClientStatus.ACTIVE)
                .build();

        assertNotNull(client);
        assertNotNull(client.getId());
        assertEquals(fullName, client.getFull_name());
        assertEquals(passport, client.getPassport_number());
        assertEquals(taxNumber, client.getIndividual_tax_number());
    }

    @Test
    void testBuild_MissingTaxNumber_ShouldThrowException() {
        ClientBuilder builder = ClientBuilder.create()
                .full_name("Jane Doe")
                .passport_number("XY987654");

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void testBuild_MissingFullName_ShouldThrowException() {
        ClientBuilder builder = ClientBuilder.create()
                .passport_number("XY987654")
                .individual_tax_number("12345");

        assertThrows(IllegalStateException.class, builder::build);
    }
}