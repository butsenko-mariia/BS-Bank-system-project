package program.Bank.Builders;

import program.Bank.DateBase;
import program.Bank.Enums.AccountStatus;
import program.Bank.Card;
import program.Bank.Enums.CardType;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;

public class CardBuilder {
    private Card card;

    public CardBuilder() {
        card =  new Card();
    }
    public CardBuilder(UUID id) {
        card =  new Card(id);
    }
    public static CardBuilder create(){
        return  new CardBuilder();
    }
    public CardBuilder client_id(UUID client_id) {
        card.setClient_id(client_id);
        return this;
    }
    public CardBuilder card_number(String card_number) {
        card.setCard_number(card_number);
        return this;
    }
    public CardBuilder card_type(CardType card_type) {
        card.setCard_type(card_type);
        return this;
    }
    public CardBuilder balance(BigDecimal balance) {
        card.setBalance(balance);
        return this;
    }
    public CardBuilder currency(String currency) {
        card.setCurrency(currency);
        return this;
    }
    public CardBuilder status(AccountStatus status) {
        card.setStatus(status);
        return this;
    }
    public CardBuilder fetch() {
        DateBase.Fetch(card);
        return this;
    }
    public CardBuilder upload() {
        DateBase.Upload(card);
        return this;
    }
    public Card build() {
        if (card == null || card.getId() == null || card.getClient_id() == null ||card.getCard_number() == null){
            throw  new IllegalStateException("Some fields are null.");
        }
        return card;
    }
    public void reset(){
        card = new Card();
    }
}
