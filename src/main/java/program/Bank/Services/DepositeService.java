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
import java.util.UUID;

public class DepositeService {
    private final Logger log = LogManager.getLogger(DepositeService.class);
    private final DataBase dataBase;
    private final ConsoleUI ui =  new ConsoleUI();

    public DepositeService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Deposit OpenStandardDeposit(UUID client_id, BigDecimal original_sum, LocalDate open_date, LocalDate close_date,
                                       BigDecimal interest_rate, String currency){
        Deposit standardDeposit = StandardDepositBuilder.create()
                .client_id(client_id)
                .original_sum(original_sum)
                .open_date(open_date)
                .close_date(close_date)
                .interest_rate(interest_rate)
                .currency(currency)
                .build();
        dataBase.Upload(standardDeposit);
        return standardDeposit;
    }

    public Deposit OpenCapitalizationDeposit(UUID client_id, BigDecimal original_sum, LocalDate open_date, LocalDate close_date,
                                             BigDecimal interest_rate, String currency) {
        Deposit capitalizationDeposit = CapitalizationDepositBuilder.create()
                .client_id(client_id)
                .original_sum(original_sum)
                .open_date(open_date)
                .close_date(close_date)
                .interest_rate(interest_rate)
                .currency(currency)
                .build();
        dataBase.Upload(capitalizationDeposit);
        return capitalizationDeposit;
    }


    public void PrintFullDetails(Deposit deposit){
        deposit.PrintFullInfo();
    }

    public void PrintShortInfo(Deposit deposit){
        deposit.PrintInfo();
    }

    public void ShowAllClientDeposits(Client client){
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
                String mes = "У даного клієнта немає відкритих депозитів.";
                log.warn(mes);
                ui.print(mes);

            }
        } catch (Exception e) {
            String mes = "Помилка при завантаженні депозитів: " + e.getMessage();
            log.error(mes);
            System.out.println(mes);
        }
    }

    public BigDecimal CloseDeposit(Deposit deposit){
        BigDecimal sum = deposit.Close(false);
        dataBase.Update(deposit);
        return sum;
    }

    public BigDecimal EarlyCloseDeposit(Deposit deposit){
        BigDecimal sum = deposit.Close(true);
        dataBase.Update(deposit);
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
            log.error("Failed to get deposit type: {}", e.getMessage());
        }
        return null;
    }

    public Deposit GetDeposit(UUID deposit_id){
        String type = getDepositType(deposit_id);
        Deposit deposit = type.equals("StandardDeposit") ? new StandardDeposit(deposit_id) : new CapitalizationDeposit(deposit_id);
        dataBase.Fetch(deposit);

        return deposit;
    }
}
