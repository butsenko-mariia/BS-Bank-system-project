package program.Bank;

public enum CardType {
    UNIVERSAL,
    PAYMENT,
    JUNIOR,
    ESUPPORT,
    NATIONAL_CASHBACK;
    @Override
    public String toString() {
        return this.name();
    }
}
