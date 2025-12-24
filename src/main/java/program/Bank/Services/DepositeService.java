package program.Bank.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.*;
import program.Bank.Builders.CapitalizationDepositBuilder;
import program.Bank.Builders.StandardDepositBuilder;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DepositeService {
    private static final Logger log = LogManager.getLogger(DepositeService.class);    private final DataBase dataBase;
    private final ConsoleUI ui =  new ConsoleUI();

    public DepositeService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Deposit OpenStandardDeposit(UUID client_id, BigDecimal original_sum, LocalDate open_date, LocalDate close_date,
                                       BigDecimal interest_rate, String currency){
        log.info("Initiating Standard Deposit opening for client: {}. Sum: {} {}", client_id, original_sum, currency);
        Deposit standardDeposit = StandardDepositBuilder.create()
                .client_id(client_id)
                .original_sum(original_sum)
                .open_date(open_date)
                .close_date(close_date)
                .interest_rate(interest_rate)
                .currency(currency)
                .build();
        dataBase.Upload(standardDeposit);

        log.info("Standard Deposit opened successfully. ID: {}", standardDeposit.getId());
        return standardDeposit;
    }

    public Deposit OpenCapitalizationDeposit(UUID client_id, BigDecimal original_sum, LocalDate open_date, LocalDate close_date,
                                             BigDecimal interest_rate, String currency) {
        log.info("Initiating Capitalization Deposit opening for client: {}. Sum: {} {}", client_id, original_sum, currency);
        Deposit capitalizationDeposit = CapitalizationDepositBuilder.create()
                .client_id(client_id)
                .original_sum(original_sum)
                .open_date(open_date)
                .close_date(close_date)
                .interest_rate(interest_rate)
                .currency(currency)
                .build();
        dataBase.Upload(capitalizationDeposit);

        log.info("Capitalization Deposit opened successfully. ID: {}", capitalizationDeposit.getId());
        return capitalizationDeposit;
    }


    public void PrintFullDetails(Deposit deposit){
        log.debug("Request to print full details for deposit: {}", deposit.getId());
        deposit.PrintFullInfo();
    }

    public void PrintShortInfo(Deposit deposit){
        deposit.PrintInfo();
    }

    public void ShowAllClientDeposits(Client client){
        log.info("Fetching all deposits for client: {}", client.getId());
        String query = "SELECT id  FROM deposit WHERE client_id = ?";
        boolean foundAny = false;

        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, client.getId());
            ResultSet rs = stmt.executeQuery();
            ui.print("Your deposits:");

            while (rs.next()) {
                foundAny = true;
                UUID depositId = (UUID) rs.getObject("id");
                String type = getDepositType(depositId);
                Deposit deposit = type.equals("StandardDeposit") ? new StandardDeposit(depositId)
                        : new CapitalizationDeposit(depositId);

                if (deposit != null) {
                    dataBase.Fetch(deposit);
                    deposit.PrintInfo();
                    ui.print("------------------------------");
                }
            }

            if (!foundAny) {
                String mes = "This customer has no open deposits.";
                log.info(mes);
                ui.print(mes);

            }
        } catch (Exception e) {
            String mes = "Error when loading deposits: " + e.getMessage();
            log.error(mes);
            ui.print(mes);
        }
    }

    public BigDecimal CloseDeposit(Deposit deposit){
        log.info("Closing deposit: {}. Type: Standard close.", deposit.getId());
        BigDecimal sum = deposit.Close(false);
        dataBase.Update(deposit);
        log.info("Deposit {} closed. Returned sum: {}", deposit.getId(), sum);
        return sum;
    }

    public BigDecimal EarlyCloseDeposit(Deposit deposit){
        log.info("Closing deposit: {}. Type: Early close.", deposit.getId());
        BigDecimal sum = deposit.Close(true);
        dataBase.Update(deposit);
        log.info("Deposit {} closed early. Returned sum: {}", deposit.getId(), sum);
        return sum;
    }

    public String getDepositType(UUID depositId){
        String query = "SELECT deposit_type  FROM deposit WHERE id = ?";

        try (Connection connection = dataBase.Connection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            String type;

            statement.setObject(1, depositId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                type = rs.getString("deposit_type");
                return type;
            }

        } catch (SQLException e) {
            log.error("Failed to get deposit type for ID {}: {}", depositId, e.getMessage());        }
        return null;
    }

    public Deposit GetDeposit(UUID deposit_id){
        log.debug("Retrieving deposit: {}", deposit_id);
        String type = getDepositType(deposit_id);
        Deposit deposit = type.equals("StandardDeposit") ? new StandardDeposit(deposit_id) : new CapitalizationDeposit(deposit_id);
        log.warn("Deposit type not found for ID: {}", deposit_id);
        dataBase.Fetch(deposit);

        return deposit;
    }

    public List<Deposit> getClientDeposits(UUID clientId) {
        log.debug("Getting list of deposits for client: {}", clientId);
        List<Deposit> deposits = new ArrayList<>();
        String query = "SELECT id FROM deposit WHERE client_id = ?";

        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UUID depositId = (UUID) rs.getObject("id");
                String type = getDepositType(depositId);
                Deposit deposit = type.equals("StandardDeposit") ? new StandardDeposit(depositId) : new CapitalizationDeposit(depositId);
                dataBase.Fetch(deposit);
                deposits.add(deposit);
            }
        } catch (Exception e) {
            log.error("Error retrieving deposit list: " + e.getMessage());
        }
        return deposits;
    }
}
