package program.Bank.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.Builders.CardBuilder;
import program.Bank.Card;
import program.Bank.Client;
import program.Bank.ConsoleUI;
import program.Bank.DataBase;
import program.Bank.Enums.AccountStatus;
import program.Bank.Enums.CardType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import program.Bank.Enums.TransactionStatus;
public class CardService {
    private final Logger log = LogManager.getLogger(CardService.class);
    private final DataBase dataBase;
    private final ConsoleUI ui = new ConsoleUI();
    private final TransactionService transactionService;
    public CardService(DataBase dataBase) {
        this.dataBase = dataBase;
        this.transactionService = new TransactionService(dataBase);
    }

    public Card CreateCard(UUID client_id, String card_number, CardType card_type, String currency) {
        Card card = CardBuilder.create()
                .client_id(client_id)
                .card_number(card_number)
                .card_type(card_type)
                .currency(currency)
                .build();
        dataBase.Upload(card);
        return card;
    }

    public void PrintFullDetails(Card card) {
        card.PrintFullInfo();
    }

    public void PrintShortInfo(Card card){
        card.PrintInfo();
    }

    public void PrintAllClientsCards(Client client) {
        String query = "SELECT id FROM card WHERE client_id = ?";
        boolean foundAny = false;

        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, client.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                foundAny = true;
                UUID cardId = (UUID) rs.getObject("id");
                Card card = new Card(cardId);
                dataBase.Fetch(card);
                ui.print("------------------------------");
                card.PrintInfo();
            }

            if (!foundAny) {
                String mes = "This customer does not have any open cards.";
                log.warn(mes);
                ui.print(mes);

            } else {
                ui.print("------------------------------");
            }

        } catch (Exception e) {
            String mes = "Error loading cards: " + e.getMessage();
            log.error(mes);
            ui.print(mes);
        }
    }

    public Card GetCardByNumber(String cardNumber) {
        String query = "SELECT id FROM card WHERE card_number = ?";
        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cardNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                Card card = new Card(id);
                dataBase.Fetch(card);
                return card;
            }
        } catch (Exception e) {
            log.error("Card search error: " + e.getMessage());
        }
        return null;
    }

    public boolean IfActiveCard(Card card){
        if (card == null) {
            String mes = "Card with number " + card.getCard_number() + " not found.";
            log.warn(mes);
            ui.print(mes);
            return false;
        }

        if(card.getStatus() != AccountStatus.ACTIVE){
            String mes = "The card with number " + card.getCard_number() + " is inactive.";
            log.warn(mes);
            ui.print(mes);
            return false;
        }

        return true;
    }

    public void TopUpCard(Card card, BigDecimal amount) {
        if (!IfActiveCard(card)) {
            return;
        }

        card.TopUp(amount);
        dataBase.Update(card);
    }

    public void TopUpCard(String cardNumber, BigDecimal amount) {
        Card card = GetCardByNumber(cardNumber);

        TopUpCard(card, amount);
    }

    public void WithDraw(String cardNumber, BigDecimal amount) {
        Card card = GetCardByNumber(cardNumber);

        WithDraw(card, amount);
    }

    public void WithDraw(Card card, BigDecimal amount) {
        if (!IfActiveCard(card)) {
            return;
        }

        card.Withdraw(amount);
        dataBase.Update(card);
    }

    public void BlockCard(Card card) {
        if (!IfActiveCard(card)) {
            return;
        }

        card.Block();
        dataBase.Update(card);
    }

    public void BlockCard(String cardNumber) {
        Card card = GetCardByNumber(cardNumber);

        BlockCard(card);
    }

    public BigDecimal CloseCard(String cardNumber) {
        Card card = GetCardByNumber(cardNumber);

        return CloseCard(card);
    }

    public BigDecimal CloseCard(Card card) {
        if (card == null) {
            String mes = "Card not found.";
            log.warn(mes);
            ui.print(mes);
            return null;
        }

        BigDecimal change = card.Close();
        dataBase.Update(card);

        return change;
    }

    public boolean Transfer(Card senderCard, String receiverCardNumber, BigDecimal amount) {
        if (senderCard.getCard_number().equals(receiverCardNumber)) {
            String mes = "Error: Unable to make a transfer to the same card.";
            log.warn(mes);
            ui.print(mes);
            return false;
        }

        WithDraw(senderCard, amount);
        TopUpCard(receiverCardNumber, amount);

        try {
            dataBase.Update(senderCard);
            dataBase.Update(GetCardByNumber(receiverCardNumber));

            String transactionInfo = "Transfer from " +  senderCard.getCard_number() + " to " + GetCardByNumber(receiverCardNumber)
                    +": " + amount;
            Card receiver = GetCardByNumber(receiverCardNumber);
            dataBase.Update(receiver);
            String mes = "Transfer successful! Sent: " + amount + " " + receiver.getCurrency();

            transactionService.createTransaction(
                    senderCard.getId(),
                    receiver.getId(),
                    amount,
                    senderCard.getCurrency(),
                    "Transfer to " + receiverCardNumber
            );

            log.warn(mes);
            ui.print(mes);
            return true;
        }
        catch (Exception e) {
            log.error("Transaction error: " + e.getMessage());

            Card receiver = GetCardByNumber(receiverCardNumber);
            UUID receiverId = (receiver != null) ? receiver.getId() : null;

            transactionService.createTransaction(
                    senderCard.getId(),
                    receiverId,
                    amount,
                    senderCard.getCurrency(),
                    "Failed transfer to " + receiverCardNumber,
                    TransactionStatus.CANCELLED // Статус "Відмінено"
            );
            ui.print("A technical error occurred during the transfer.");
            return false;
        }
    }

    public java.util.List<Card> getClientCards(UUID clientId) {
        java.util.List<Card> cards = new java.util.ArrayList<>();
        String query = "SELECT id FROM card WHERE client_id = ?";

        try (Connection conn = dataBase.Connection(); //
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UUID cardId = (UUID) rs.getObject("id");
                Card card = new Card(cardId);
                dataBase.Fetch(card); // Завантажуємо дані картки
                cards.add(card);
            }
        } catch (Exception e) {
            log.error("Error receiving list of cards: " + e.getMessage());
        }
        return cards;
    }

    public boolean Transfer(String receiverCardNumber, BigDecimal amount, String transactionInfo) {
        TopUpCard(receiverCardNumber, amount);

        try {
            dataBase.Update(GetCardByNumber(receiverCardNumber));
            Card receiver = GetCardByNumber(receiverCardNumber);
            dataBase.Update(receiver);
            String mes = "Transfer successful! Sent: " + amount + " " + receiver.getCurrency();

            transactionService.createTransaction(
                    null, // Відправника немає (готівка)
                    receiver.getId(),
                    amount,
                    receiver.getCurrency(),
                    transactionInfo
            );

            log.warn(mes);
            ui.print(mes);
            return true;
        }
        catch (Exception e) {
            log.error("Transaction error: " + e.getMessage());

            Card receiver = GetCardByNumber(receiverCardNumber);
            UUID receiverId = (receiver != null) ? receiver.getId() : null;
            String currency = (receiver != null) ? receiver.getCurrency() : "UAH";

            transactionService.createTransaction(
                    null,
                    receiverId,
                    amount,
                    currency,
                    transactionInfo + " (Failed)",
                    TransactionStatus.CANCELLED
            );

            ui.print("A technical error occurred during the transfer.");
            return false;
        }
    }

}