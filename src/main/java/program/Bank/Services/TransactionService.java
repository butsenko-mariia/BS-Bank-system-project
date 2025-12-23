package program.Bank.Services;

import program.Bank.Builders.TransactionBuilder;
import program.Bank.DataBase;
import program.Bank.Transaction;
import program.Bank.Enums.TransactionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionService {
    private final Logger log = LogManager.getLogger(TransactionService.class);
    private final DataBase dataBase;

    public TransactionService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * СТВОРЕННЯ ЗАПИСУ ПРО ТРАНЗАКЦІЮ
     * Викликається після успішного переказу в CardService.
     * Автоматично знаходить ID отримувача за номером картки.
     */
    public void createTransaction(UUID fromCardId, String toCardNumber, BigDecimal amount, String operationInfo) {
        // 1. Знаходимо ID картки отримувача по номеру
        UUID toCardId = getCardIdByNumber(toCardNumber);

        // Якщо картку не знайдено (хоча CardService вже мав це перевірити)
        if (toCardId == null) {
            log.warn("Transaction log failed: Receiver card {} not found.", toCardNumber);
            return;
        }

        UUID transactionId = UUID.randomUUID();

        // 2. Будуємо об'єкт транзакції
        Transaction transaction = TransactionBuilder.create()
                .id(transactionId)
                .account_id_from(fromCardId)
                .account_id_to(toCardId)
                .sum(amount)
                .currency("UAH") // За замовчуванням UAH, можна передавати параметром
                .operation_info(operationInfo)
                .open_date(LocalDate.now())
                .open_time(LocalTime.now())
                .status(TransactionStatus.COMPLETED) // Статус COMPLETED, бо гроші вже переказані
                .build();

        // 3. Зберігаємо в базу даних
        dataBase.Upload(transaction);

        log.info("Transaction recorded: " + transactionId);
    }

    /**
     * ОТРИМАННЯ ІСТОРІЇ (для меню History)
     * Повертає список транзакцій для конкретної картки з правильними знаками +/-.
     */
    public List<Transaction> getTransactionHistory(UUID accountId) {
        List<Transaction> history = new ArrayList<>();

        // SQL: Шукаємо транзакції, де accountId є або відправником, або отримувачем
        String query = "SELECT * FROM transaction " +
                "WHERE account_id_from = ? OR account_id_to = ? " +
                "ORDER BY open_date DESC, open_time DESC";

        try (Connection connection = dataBase.Connection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, accountId);
            statement.setObject(2, accountId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                // Відновлюємо об'єкт через Builder
                Transaction t = TransactionBuilder.create()
                        .id((UUID) rs.getObject("id"))
                        .account_id_from((UUID) rs.getObject("account_id_from"))
                        .account_id_to((UUID) rs.getObject("account_id_to"))
                        .sum(rs.getBigDecimal("sum"))
                        .currency(rs.getString("currency"))
                        .operation_info(rs.getString("operation_info"))
                        .open_date(rs.getDate("open_date").toLocalDate())
                        .open_time(rs.getTime("open_time").toLocalTime())
                        .status(TransactionStatus.valueOf(rs.getString("status")))
                        .build();

                // Логіка знаку: якщо ми відправили -> "-", якщо отримали -> "+"
                if (accountId.equals(t.getAccount_id_from())) {
                    t.setSign("-");
                } else {
                    t.setSign("+");
                }

                history.add(t);
            }
        } catch (SQLException e) {
            log.error("Error loading history: " + e.getMessage());
        }

        return history;
    }

    /**
     * ДОПОМІЖНИЙ МЕТОД: Пошук ID картки за номером.
     * Потрібен, бо користувач вводить номер "4149...", а в базі транзакціям треба UUID.
     */
    private UUID getCardIdByNumber(String cardNumber) {
        String query = "SELECT id FROM card WHERE card_number = ?";
        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cardNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return (UUID) rs.getObject("id");
            }
        } catch (SQLException e) {
            log.error("SQL Error in getCardIdByNumber: " + e.getMessage());
        }
        return null;
    }

    /**
     * Пошук однієї транзакції за ID (стандартний метод)
     */
    public Transaction getTransactionById(UUID id) {
        Transaction transaction = new Transaction(id);
        dataBase.Fetch(transaction);
        if (transaction.getOpen_date() == null) return null;
        return transaction;
    }
}