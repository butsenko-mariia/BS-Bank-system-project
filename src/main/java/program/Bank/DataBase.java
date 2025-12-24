package program.Bank;

import program.Bank.Enums.AccountStatus;
import program.Bank.Enums.CardType;
import program.Bank.Enums.ClientStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.Enums.TransactionStatus;
import java.sql.*;
import java.util.UUID;

public class DataBase {
    private static volatile DataBase instance;
    private static final String URL = "jdbc:postgresql://localhost:5432/BankSystem";
    private static final String USER = "postgres";
    private static final String PASSWORD = "new password";
    private static final Logger log = LogManager.getLogger(DataBase.class);

    private DataBase() {
    }

    public static DataBase getInstance() {
        if (instance == null) {
            synchronized (DataBase.class) {
                if (instance == null) {
                    instance = new DataBase();
                    log.debug("Created DateBase.");
                }
            }
        }
        return instance;
    }

    public Connection Connection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void Fetch(Client client){
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

        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error("Sql failed: " + e.getMessage());
        }
    }

    public void Fetch(Deposit deposit) {
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
                deposit.setTax_rate(rs.getBigDecimal("tax_rate"));
                deposit.setMilitary_rate(rs.getBigDecimal("military_rate"));

                log.debug("Deposit loaded successfully");
            }
            else {
                log.warn("Deposit with ID {} not found", deposit);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error("Sql failed: " + e.getMessage());
        }
    }

    public void Fetch(Card card) {
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
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error("Sql failed: " + e.getMessage());
        }
    }

    public void Fetch(Loan loan){
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
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error("Sql failed: " + e.getMessage());
        }
    }

    public void Fetch(Transaction transaction){
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
        catch (SQLException e) {
            log.error(e.getMessage());
            log.error("Sql failed: " + e.getMessage());
        }
    }

    public void Upload(Card card){
        log.info("Uploading card id data by ID: {}",card.getId());
        final String sql = "INSERT INTO card (id, client_id, card_number, card_type, balance, currency, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (   Connection connection = Connection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, card.getId());
            statement.setObject(2, card.getClient_id());
            statement.setString(3, card.getCard_number());
            statement.setString(4, card.getCard_type().toString());
            statement.setBigDecimal(5, card.getBalance());
            statement.setString(6, card.getCurrency());
            statement.setString(7, card.getStatus().toString());
            statement.executeUpdate();

            log.info("New card: {} was successfully created at base.", card.getCard_number());
        } catch (SQLException e) {
            log.error("Sql failed: " + e.getMessage());
        }
    }

    public void Upload(Client client) {
        log.info("Uploading client data by ID: {}", client.getId());
        final String sql = "INSERT INTO client (id, full_name, date_of_birth, sex, nationality, mobile_phone, individual_tax_number, passport_number, legal_address, place_of_birth, record_number, place_of_work_or_study, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, client.getId());
            statement.setString(2, client.getFull_name());
            statement.setDate(3, java.sql.Date.valueOf(client.getDate_of_birth()));
            statement.setString(4, client.getSex());
            statement.setString(5, client.getNationality());
            statement.setString(6, client.getMobile_phone());
            statement.setString(7, client.getIndividual_tax_number());
            statement.setString(8, client.getPassport_number());
            statement.setString(9, client.getLegal_address());
            statement.setString(10, client.getPlace_of_birth());
            statement.setString(11, client.getRecord_number());
            statement.setString(12, client.getPlace_of_work_or_study());
            statement.setString(13, client.getStatus().toString());
            statement.executeUpdate();

            log.info("New client: {} was successfully created at base.", client.getFull_name());
        } catch (SQLException e) {
            log.error("Sql failed: " + e.getMessage());
        }
    }

    public void Upload(Deposit deposit) {
        log.info("Uploading deposit data by ID: {}", deposit.getId());

        final String sql = "INSERT INTO deposit (id, client_id, original_sum, profit, open_date, close_date, interest_rate, currency, status, deposit_type, tax_rate, military_rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, deposit.getId());
            statement.setObject(2, deposit.getClient_id());
            statement.setBigDecimal(3, deposit.getOriginal_sum());
            statement.setBigDecimal(4, deposit.getProfit());
            statement.setDate(5, java.sql.Date.valueOf(deposit.getOpen_date()));
            statement.setDate(6, java.sql.Date.valueOf(deposit.getClose_date()));
            statement.setBigDecimal(7, deposit.getInterest_rate());
            statement.setString(8, deposit.getCurrency());
            statement.setString(9, deposit.getStatus().toString());
            String type = (deposit instanceof CapitalizationDeposit) ? "CapitalizationDeposit" : "StandardDeposit";
            statement.setString(10, type);
            statement.setBigDecimal(11, deposit.getTax_rate());
            statement.setBigDecimal(12, deposit.getMilitary_rate());

            statement.executeUpdate();

            log.info("New deposit: {} was successfully created at base.", deposit.getId());
        } catch (SQLException e) {
            log.error("Sql failed: " + e.getMessage());
        }
    }

    public void Upload(Loan loan) {
        log.info("Uploading loan data by ID: {}", loan.getId());
        final String sql = "INSERT INTO loan (id, client_id, original_sum, current_balance, open_date, close_date, next_payment_date, term_month, payment_day, monthly_payment, interest_rate, monthly_rate, currency, status, overdue_sum, change) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, loan.getId());
            statement.setObject(2, loan.getClient_id());
            statement.setBigDecimal(3, loan.getOriginal_sum());
            statement.setBigDecimal(4, loan.getCurrent_balance());
            statement.setDate(5, java.sql.Date.valueOf(loan.getOpen_date()));
            statement.setDate(6, java.sql.Date.valueOf(loan.getClose_date()));
            statement.setDate(7, java.sql.Date.valueOf(loan.getNext_payment_date()));
            statement.setLong(8, loan.getTerm_month());
            statement.setInt(9, loan.getPayment_day());
            statement.setBigDecimal(10, loan.getMonthly_payment());
            statement.setBigDecimal(11, loan.getInterest_rate());
            statement.setBigDecimal(12, loan.getMonthly_rate());
            statement.setString(13, loan.getCurrency());
            statement.setString(14, loan.getStatus().toString());
            statement.setBigDecimal(15, loan.getOverdue_sum());
            statement.setBigDecimal(16, loan.getChange());

            statement.executeUpdate();

            log.info("New loan: {} was successfully created at base.", loan.getId());
        } catch (SQLException e) {
            log.error("Sql failed: " + e.getMessage());
        }
    }

    public void Upload(Transaction transaction) {
        log.info("Uploading transaction data by ID: {}", transaction.getId());
        final String sql = "INSERT INTO transaction (id, open_date, open_time, sum, currency, operation_info, sign, account_id_from, account_id_to, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, transaction.getId());
            statement.setDate(2, java.sql.Date.valueOf(transaction.getOpen_date()));
            statement.setTime(3, java.sql.Time.valueOf(transaction.getOpen_time()));
            statement.setBigDecimal(4, transaction.getSum());
            statement.setString(5, transaction.getCurrency());
            statement.setString(6, transaction.getOperation_info());
            statement.setString(7, transaction.getSign());
            statement.setObject(8, transaction.getAccount_id_from());
            statement.setObject(9, transaction.getAccount_id_to());

            statement.setString(10, transaction.getStatus().toString());

            statement.executeUpdate();

            log.info("New transaction: {} was successfully created at base.", transaction.getId());
        } catch (SQLException e) {
            log.error("Sql failed: " + e.getMessage());
        }
    }

    public void Update(Client client) {
        log.info("Updating client data for ID: {}", client.getId());
        String sql = "UPDATE client SET full_name=?, date_of_birth=?, sex=?, nationality=?, mobile_phone=?, " +
                "individual_tax_number=?, passport_number=?, legal_address=?, place_of_birth=?, " +
                "record_number=?, place_of_work_or_study=?, status=? WHERE id=?";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.getFull_name());
            statement.setDate(2, java.sql.Date.valueOf(client.getDate_of_birth()));
            statement.setString(3, client.getSex());
            statement.setString(4, client.getNationality());
            statement.setString(5, client.getMobile_phone());
            statement.setString(6, client.getIndividual_tax_number());
            statement.setString(7, client.getPassport_number());
            statement.setString(8, client.getLegal_address());
            statement.setString(9, client.getPlace_of_birth());
            statement.setString(10, client.getRecord_number());
            statement.setString(11, client.getPlace_of_work_or_study());
            statement.setString(12, client.getStatus().toString());

            statement.setObject(13, client.getId());

            statement.executeUpdate();
            log.info("Client {} updated successfully.", client.getFull_name());

        } catch (SQLException e) {
            log.error("Failed to update client: {}", e.getMessage());
        }
    }

    public void Update(Card card) {
        log.info("Updating card data for ID: {}", card.getId());
        String sql = "UPDATE card SET client_id=?, card_number=?, card_type=?, balance=?, currency=?, status=? WHERE id=?";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, card.getClient_id());
            statement.setString(2, card.getCard_number());
            statement.setString(3, card.getCard_type().toString());
            statement.setBigDecimal(4, card.getBalance());
            statement.setString(5, card.getCurrency());
            statement.setString(6, card.getStatus().toString());

            statement.setObject(7, card.getId());

            statement.executeUpdate();
            log.info("Card {} updated successfully.", card.getCard_number());

        } catch (SQLException e) {
            log.error("Failed to update card: {}", e.getMessage());
        }
    }

    public void Update(Deposit deposit) {
        log.info("Updating deposit data for ID: {}", deposit.getId());
        String sql = "UPDATE deposit SET client_id=?, original_sum=?, profit=?, open_date=?, close_date=?, " +
                "interest_rate=?, currency=?, status=?, tax_rate=?, military_rate=? WHERE id=?";


        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, deposit.getClient_id());
            statement.setBigDecimal(2, deposit.getOriginal_sum());
            statement.setBigDecimal(3, deposit.getProfit());
            statement.setDate(4, java.sql.Date.valueOf(deposit.getOpen_date()));
            statement.setDate(5, java.sql.Date.valueOf(deposit.getClose_date()));
            statement.setBigDecimal(6, deposit.getInterest_rate());
            statement.setString(7, deposit.getCurrency());
            statement.setString(8, deposit.getStatus().toString());
            statement.setBigDecimal(9, deposit.getTax_rate());
            statement.setBigDecimal(10, deposit.getMilitary_rate());

            statement.setObject(11, deposit.getId());

            statement.executeUpdate();
            log.info("Deposit {} updated successfully.", deposit.getId());

        } catch (SQLException e) {
            log.error("Failed to update deposit: {}", e.getMessage());
        }
    }

    public void Update(Loan loan) {
        log.info("Updating loan data for ID: {}", loan.getId());
        String sql = "UPDATE loan SET client_id=?, original_sum=?, current_balance=?, open_date=?, close_date=?, " +
                "next_payment_date=?, term_month=?, payment_day=?, monthly_payment=?, interest_rate=?, " +
                "monthly_rate=?, currency=?, status=?, overdue_sum=?, change=? WHERE id=?";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, loan.getClient_id());
            statement.setBigDecimal(2, loan.getOriginal_sum());
            statement.setBigDecimal(3, loan.getCurrent_balance());
            statement.setDate(4, java.sql.Date.valueOf(loan.getOpen_date()));
            statement.setDate(5, java.sql.Date.valueOf(loan.getClose_date()));

            if (loan.getNext_payment_date() != null) {
                statement.setDate(6, java.sql.Date.valueOf(loan.getNext_payment_date()));
            }
            statement.setLong(7, loan.getTerm_month());
            statement.setInt(8, loan.getPayment_day());
            statement.setBigDecimal(9, loan.getMonthly_payment());
            statement.setBigDecimal(10, loan.getInterest_rate());
            statement.setBigDecimal(11, loan.getMonthly_rate());
            statement.setString(12, loan.getCurrency());
            statement.setString(13, loan.getStatus().toString());
            statement.setBigDecimal(14, loan.getOverdue_sum());
            statement.setBigDecimal(15, loan.getChange());

            statement.setObject(16, loan.getId());

            statement.executeUpdate();
            log.info("Loan {} updated successfully.", loan.getId());

        } catch (SQLException e) {
            log.error("Failed to update loan: {}", e.getMessage());
        }
    }

    public void Update(Transaction transaction) {
        log.info("Updating transaction data for ID: {}", transaction.getId());

        final String sql = "UPDATE transaction SET open_date=?, open_time=?, sum=?, currency=?, " +
                "operation_info=?, sign=?, account_id_from=?, account_id_to=?, status=? WHERE id=?";

        try (Connection connection = Connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(transaction.getOpen_date()));
            statement.setTime(2, java.sql.Time.valueOf(transaction.getOpen_time()));
            statement.setBigDecimal(3, transaction.getSum());
            statement.setString(4, transaction.getCurrency());
            statement.setString(5, transaction.getOperation_info());
            statement.setString(6, transaction.getSign());
            statement.setObject(7, transaction.getAccount_id_from());
            statement.setObject(8, transaction.getAccount_id_to());
            statement.setString(9, transaction.getStatus().toString());

            statement.setObject(10, transaction.getId());

            statement.executeUpdate();
            log.info("Transaction {} updated successfully.", transaction.getId());

        } catch (SQLException e) {
            log.error("Failed to update transaction: {}", e.getMessage());
        }
    }
}
