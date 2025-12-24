package program.Bank.Services;

import program.Bank.Builders.ClientBuilder;
import program.Bank.Client;
import program.Bank.ConsoleUI;
import program.Bank.DataBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class ClientService {
    private static final Logger log = LogManager.getLogger(ClientService.class);
    private final DataBase dataBase;
    private final ConsoleUI ui =  new ConsoleUI();

    public ClientService(DataBase dataBase) {
        this.dataBase = dataBase;
    }
    public Client RegisterClient(String full_name, String date_of_birth, String sex, String nationality, String mobile_phone,
                                 String individual_tax_number, String passport_number, String legal_address,
                                 String place_of_birth, String record_number, String place_of_work_or_study){
        log.info("Registering new client: {}", full_name);
        LocalDate birthDate = LocalDate.parse(date_of_birth);
        Client newClient = ClientBuilder.create()
                .full_name(full_name)
                .date_of_birth(birthDate)
                .sex(sex)
                .nationality(nationality)
                .mobile_phone(mobile_phone)
                .individual_tax_number(individual_tax_number)
                .passport_number(passport_number)
                .legal_address(legal_address)
                .place_of_birth(place_of_birth)
                .record_number(record_number)
                .place_of_work_or_study(place_of_work_or_study)
                .build();

        dataBase.Upload(newClient);
        log.info("Client registered successfully. ID: {}", newClient.getId());
        return newClient;
    }

    public void FullInfo(Client client){

        log.debug("Printing full info for client: {}", client.getId());
        client.PrintClientFullInfo();
    }

    public Client SearchClient(String searchFilter, String searchValue){
        log.info("Searching client by {}.", searchFilter);
        String query = "";

        switch (searchFilter.toLowerCase()) {
            case "name":
                query = "SELECT id FROM client WHERE full_name = ?";
                break;
            case "passport":
                query = "SELECT id FROM client WHERE passport_number = ?";
                break;
            case "phone":
                query = "SELECT id FROM client WHERE mobile_phone = ?";
            default:
                return null;

        }

        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, searchValue);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UUID clientId = (UUID) rs.getObject("id");
                Client client = new Client(clientId);
                dataBase.Fetch(client);
                log.info("Client found: {}", clientId);
                return client;
            }else {
                log.info("Client not found by filter: {}", searchFilter);
            }

        } catch (Exception e) {
            String mes = "Database error: " + e.getMessage();
            log.error(mes);
            ui.print(mes);
        }
        return null;
    }

    public void ShortInfoShow(Client client){
        client.PrintInfo();
    }
}
