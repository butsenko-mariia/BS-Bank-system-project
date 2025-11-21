package program.Bank.Builders;

import program.Bank.AccountStatus;
import program.Bank.Card;
import program.Bank.CardType;

import java.math.BigDecimal;
import java.util.UUID;

public class CardBuilder {
    private Card card =  new Card();

    public CardBuilder() {
        this.create();
    }
    private void create(){
        card.id();
        card.setBalance(BigDecimal.ZERO);
        card.setCurrency("GRN");
        card.setStatus(AccountStatus.OPEN);
    }
    public CardBuilder client_id(UUID client_id) {
        card.setClient_id(client_id);
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
    public Card build() {
        return card;
    }
    public void reset(){
        card = new Card();
        this.create();
    }
}
