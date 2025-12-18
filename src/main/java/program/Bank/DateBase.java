package program.Bank;

import program.Bank.Enums.AccountStatus;
import program.Bank.Enums.CardType;
import program.Bank.Enums.ClientStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.Enums.TransactionStatus;

import java.sql.*;
import java.util.UUID;

public class DateBase {
    private static final String URL = "jdbc:postgresql://localhost:5432/BankSystem";
    private static final String USER = "postgres"; // зазвичай стандартний
    private static final String PASSWORD = "password";
    private static final Logger log = LogManager.getLogger(DateBase.class);

    public DateBase() {
        log.debug("Created DateBase.");
    }

    public static Connection Connection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void Fetch(Client client){
        log.info("Loading client data by ID: {}", client.getId());

        String query = "SELECT * FROM client WHERE id = ?";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, client.getId());

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                client.setFull_name(rs.getString("full_name"));
                client.setDate_of_birth(rs.getDate("date_of_birth").toLocalDate());
                client.setSex(rs.getString("sex"));
                client.setNationality(rs.getString("nationality"));
                client.setMobile_phone(rs.getString("mobile_phone"));
                client.setIndividual_tax_number(rs.getString("individual_tax_number"));
                client.setPassport_number(rs.getString("passport_number"));
                client.setLegal_address(rs.getString("legal_address"));
                client.setPlace_of_birth(rs.getString("place_of_birth"));
                client.setRecord_number(rs.getString("record_number"));
                client.setPlace_of_work_or_study(rs.getString("place_of_work_or_study"));
                client.setStatus(ClientStatus.valueOf(rs.getString("status")));

                log.debug("Fetched client successfully.");
            } else {
                log.warn("Client with this ID not found in DB.");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void Fetch(Deposit deposit) {
        log.info("Fetching deposit data by ID: {}", deposit.getId());

        String query = "SELECT * FROM deposit WHERE id = ?";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, deposit.getId());

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                deposit.setClient_id((UUID) rs.getObject("client_id"));
                deposit.setOriginal_sum(rs.getBigDecimal("original_sum"));
                deposit.setProfit(rs.getBigDecimal("profit"));
                deposit.setOpen_date(rs.getDate("open_date").toLocalDate());
                deposit.setClose_date(rs.getDate("close_date").toLocalDate());
                deposit.setInterest_rate(rs.getBigDecimal("interest_rate"));
                deposit.setCurrency(rs.getString("currency"));
                String statusStr = rs.getString("status");
                deposit.setStatus(AccountStatus.valueOf(statusStr));

                log.debug("Deposit loaded successfully");
            }
            else {
                log.warn("Deposit with ID {} not found", deposit);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void Fetch(Card card) {
        log.info("Fetching card data by ID: {}", card.getId());
        String query = "SELECT * FROM card WHERE id = ?";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, card.getId());

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                card.setClient_id((UUID) rs.getObject("client_id"));
                card.setCard_number(rs.getString("card_number"));
                card.setBalance(rs.getBigDecimal("balance"));
                card.setCurrency(rs.getString("currency"));
                String typeStr = rs.getString("card_type");
                card.setCard_type(CardType.valueOf(typeStr));
                String statusStr = rs.getString("status");
                card.setStatus(AccountStatus.valueOf(statusStr));

                log.debug("Card data loaded successfully");
            }
            else {
                log.warn("Card with ID {} not found", card);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void Fetch(Loan loan){
        log.info("Fetching loan data by ID: {}", loan.getId());

        String query = "SELECT * FROM loan WHERE id = ?";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, loan.getId());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    loan.setClient_id((UUID) rs.getObject("client_id"));
                    loan.setOriginal_sum(rs.getBigDecimal("original_sum"));
                    loan.setCurrent_balance(rs.getBigDecimal("current_balance"));
                    loan.setCurrency(rs.getString("currency"));
                    loan.setInterest_rate(rs.getBigDecimal("interest_rate"));
                    loan.setMonthly_rate(rs.getBigDecimal("monthly_rate"));
                    loan.setOpen_date(rs.getDate("open_date").toLocalDate());
                    loan.setClose_date(rs.getDate("close_date").toLocalDate());
                    loan.setNext_payment_date(rs.getDate("next_payment_date").toLocalDate());
                    loan.setTerm_month(rs.getInt("term_month"));
                    loan.setPayment_day(rs.getInt("payment_day"));
                    loan.setMonthly_payment(rs.getBigDecimal("monthly_payment"));
                    loan.setStatus(AccountStatus.valueOf(rs.getString("status")));
                    loan.setOverdue_sum(rs.getBigDecimal("overdue_sum"));
                    loan.setChange(rs.getBigDecimal("change"));

                    log.debug("Loan loaded successfully");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void Fetch(Transaction transaction){
        log.info("Fetching transaction data by ID: {}", transaction.getId());

        String query = "SELECT * FROM transaction WHERE id = ?";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, transaction.getId());

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                transaction.setOpen_date(rs.getDate("open_date").toLocalDate());
                transaction.setOpen_time(rs.getTime("open_time").toLocalTime());
                transaction.setSum(rs.getBigDecimal("sum"));
                transaction.setCurrency(rs.getString("currency"));
                transaction.setOperation_info(rs.getString("operation_info"));
                transaction.setSign(rs.getString("sign")); // Присвоюємо першу літеру
                transaction.setAccount_id_from((UUID) rs.getObject("account_id_from"));
                transaction.setAccount_id_to((UUID) rs.getObject("account_id_to"));
                transaction.setStatus(TransactionStatus.valueOf(rs.getString("status")));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
