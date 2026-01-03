package program.Bank.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.*;
import program.Bank.Builders.CardBuilder;
import program.Bank.Enums.AccountStatus;
import program.Bank.Enums.CardType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import program.Bank.Enums.TransactionStatus;
public class CardService {
    private static final Logger log = LogManager.getLogger(CardService.class);
    private final DataBase dataBase;
    private final ConsoleUI ui = new ConsoleUI();
    private final TransactionService transactionService;
    public CardService(DataBase dataBase) {
        this.dataBase = dataBase;
        this.transactionService = new TransactionService(dataBase);
    }

    public Card CreateCard(String client_id, String card_number, String card_type, String currency) {
        log.info("Creating new card for client: {}. Type: {}, Currency: {}", client_id, card_type, currency);
        Card card = CardBuilder.create()
                .client_id(UUID.fromString(client_id))
                .card_number(card_number)
                .card_type(CardType.valueOf(card_type))
                .currency(currency)
                .build();
        dataBase.Create(card);
        log.info("Card created successfully. ID: {}", card.getId());
        return card;
    }

    public String PrintFullDetails(Card card) {
        log.debug("Printing full details for card: {}", card.getId());
        card.PrintFullInfo();
        return card.toString();
    }

    public String PrintShortInfo(Card card){
        return card.PrintInfo();
    }

    public List<Card> GetAllClientsCards(Client client) {
        log.info("Fetching all cards for client: {}", client.getId());
        String query = "SELECT id FROM card WHERE client_id = ?";
        boolean foundAny = false;

        List<Card> cards = new ArrayList<>();

        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, client.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                foundAny = true;
                UUID cardId = (UUID) rs.getObject("id");
                Card card = new Card(cardId);
                dataBase.Read(card);
                cards.add(card);
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
        return cards;
    }

    public Card GetCardByNumber(String cardNumber) {
        log.debug("Searching for card by number: {}", cardNumber);
        String query = "SELECT id FROM card WHERE card_number = ?";
        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cardNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                Card card = new Card(id);
                dataBase.Read(card);
                return card;
            }
        } catch (Exception e) {
            log.error("Error searching for card {}: {}", cardNumber, e.getMessage());
        }
        log.warn("Card number {} not found in database.", cardNumber);
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

    public boolean TopUpCard(Card card, BigDecimal amount) {
        if (!IfActiveCard(card)) {
            log.warn("Attempt to top up inactive or null card: {}", (card != null ? card.getCard_number() : "null"));
            return false;
        }
        log.info("Topping up card {}. Amount: {}", card.getCard_number(), amount);

        card.TopUp(amount);
        dataBase.Update(card);

        log.info("Card {} topped up successfully. New balance: {}", card.getCard_number(), card.getBalance());
        return true;
    }

    public boolean WithDraw(Card card, BigDecimal amount) {
        if (!IfActiveCard(card)) {
            log.warn("Attempt to withdraw from inactive card: {}", card.getCard_number());
            return false;
        }

        if (card.getBalance().compareTo(amount) < 0) {
            log.warn("Amount to withdraw more than card conclude: {}", card.getBalance());
            return false;
        }

        log.info("Withdrawing from card {}. Amount: {}", card.getCard_number(), amount);

        card.Withdraw(amount);
        dataBase.Update(card);

        log.info("Withdrawal from card {} successful. New balance: {}", card.getCard_number(), card.getBalance());
        return true;
    }

    public boolean TopUpCard(String cardNumber, BigDecimal amount) {
        Card card = GetCardByNumber(cardNumber);

        return TopUpCard(card, amount);
    }

    public boolean WithDraw(String cardNumber, BigDecimal amount) {
        Card card = GetCardByNumber(cardNumber);

        return WithDraw(card, amount);
    }

    public boolean BlockCard(Card card) {
        if (!IfActiveCard(card)) {
            log.warn("Attempt to block inactive or null card.");
            return false;
        }
        log.info("Blocking card: {}", card.getCard_number());
        card.Block();
        dataBase.Update(card);
        log.info("Card {} blocked successfully.", card.getCard_number());
        return true;
    }

    public boolean BlockCard(String cardNumber) {
        Card card = GetCardByNumber(cardNumber);

        return BlockCard(card);
    }

    public boolean CloseCard(String cardNumber) {
        Card card = GetCardByNumber(cardNumber);
        return CloseCard(card);
    }

    public boolean CloseCard(Card card) {
        if (!IfActiveCard(card)) {
            String mes = "Card not found.";
            log.warn(mes);
            ui.print(mes);
            return false;
        }
        log.info("Closing card: {}", card.getCard_number());
        card.Close();
        dataBase.Update(card);
        log.info("Card {} closed. Returned balance: {}", card.getCard_number());
        return true;
    }

    public boolean Transfer(Card senderCard, String receiverCardNumber, BigDecimal amount) {
        log.info("Initiating transfer from {} to {}. Amount: {}", senderCard.getCard_number(), receiverCardNumber, amount);
        boolean result = true;

        if (senderCard.getCard_number().equals(receiverCardNumber)) {
            String mes = "Error: Unable to make a transfer to the same card.";
            log.error(mes);
            ui.print(mes);
            result = false;
        }

        boolean ifWithDrawed = true;
        boolean ifToppedUp = true;

        if (result) {
            ifWithDrawed = WithDraw(senderCard, amount);
        }
        if (!ifWithDrawed) {
            String mes = "Error: Unable to withdraw.";
            ui.print(mes);
            log.error(mes);
            result = false;
        }
        if (result) {
            ifToppedUp = TopUpCard(receiverCardNumber, amount);
        }
        if (!ifToppedUp) {
            String mes = "Error: Unable to topp up card.";
            ui.print(mes);
            log.error(mes);
            result = false;
            TopUpCard(senderCard, amount);
        }

        if (result) {
            try {
                dataBase.Update(senderCard);

                Card receiverCard = GetCardByNumber(receiverCardNumber);
                dataBase.Update(receiverCard);

                String mes = "Transfer successful! Sent: " + amount + " " + senderCard.getCurrency();

                transactionService.createTransaction(
                        senderCard.getId(),
                        receiverCard.getId(),
                        amount,
                        senderCard.getCurrency(),
                        "Transfer from " + senderCard.getCard_number() + " to " + receiverCardNumber + " : "
                        + amount + " " + senderCard.getCurrency(),
                        TransactionStatus.COMPLETED
                );

                log.info("Transfer successful.");
                ui.print(mes);
                return true;
            } catch (Exception e) {
                log.error("Transaction failed: " + e.getMessage());
                ui.print("System error during transfer.");
                TopUpCard(senderCard, amount);
                WithDraw(receiverCardNumber, amount);
                return false;
            }
        }
        else{
            Card receiverCard = GetCardByNumber(receiverCardNumber);

            String mes = "Transfer failed!";

            transactionService.createTransaction(
                    senderCard.getId(),
                    receiverCard.getId(),
                    amount,
                    senderCard.getCurrency(),
                    "Transfer from " + senderCard.getCard_number() + " to " + receiverCardNumber + " : "
                            + amount + " " + senderCard.getCurrency() + " FAILED",
                    TransactionStatus.CANCELLED
            );
        }

        return result;
    }


    public List<Card> getClientCards(UUID clientId) {
        List<Card> cards = new ArrayList<>();

        log.debug("Getting list of cards for client: {}", clientId);

        String query = "SELECT id FROM card WHERE client_id = ?";

        try (Connection conn = dataBase.Connection(); //
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UUID cardId = (UUID) rs.getObject("id");
                Card card = new Card(cardId);
                dataBase.Read(card);
                cards.add(card);
            }
        } catch (Exception e) {
            log.error("Error retrieving card list for client {}: {}", clientId, e.getMessage());
        }
        return cards;
    }

    public boolean Transfer(String receiverCardNumber, BigDecimal amount, String transactionInfo) {
        log.info("Initiating external transfer to {}. Amount: {}. Info: {}", receiverCardNumber, amount, transactionInfo);
        boolean ifToppedUp = TopUpCard(receiverCardNumber, amount);

        if (ifToppedUp) {
            try {
                dataBase.Update(GetCardByNumber(receiverCardNumber));
                Card receiver = GetCardByNumber(receiverCardNumber);
                dataBase.Update(receiver);
                String mes = "Transfer successful! Sent: " + amount + " " + receiver.getCurrency();

                transactionService.createTransaction(
                        null,
                        receiver.getId(),
                        amount,
                        receiver.getCurrency(),
                        transactionInfo,
                        TransactionStatus.COMPLETED
                );

                log.info("External transfer successful. Sent: {} {}", amount, receiver.getCurrency());
                ui.print(mes);
                return true;
            } catch (Exception e) {
                log.error("Transaction error during external transfer: {}", e.getMessage());

                Card receiver = GetCardByNumber(receiverCardNumber);

                transactionService.createTransaction(
                        null,
                        receiver.getId(),
                        amount,
                        receiver.getCurrency(),
                        transactionInfo + " FAILED",
                        TransactionStatus.CANCELLED
                );

                ui.print("A technical error occurred during the transfer.");
                WithDraw(receiverCardNumber, amount);
                return false;
            }
        }
        return ifToppedUp;
    }

    public void DeleteCard(Card card) {
        log.debug("Deleting card {}", card.getId());
        dataBase.Delete(card);
    }
}