package program.Bank;

import org.junit.jupiter.api.Test;
import program.Bank.Enums.AccountStatus;

import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    /**
     * Перевіряє, чи коректно збільшується баланс картки при поповненні (TopUp).
     * Очікуємо, що баланс стане рівним сумі поповнення (якщо був 0).
     */
    @Test
    void testTopUpBalance() {
        Card card = new Card();
        card.setBalance(BigDecimal.ZERO);

         card.TopUp(new BigDecimal("100.50"));

         assertEquals(0, new BigDecimal("100.50").compareTo(card.getBalance()), "The balance must be replenished correctly");
    }

    /**
     * Перевіряє успішне зняття коштів, коли на рахунку достатньо грошей.
     * Очікуємо, що баланс зменшиться рівно на суму зняття.
     */
    @Test
    void testWithdrawSuccess() {
        Card card = new Card();
        card.setBalance(new BigDecimal("500.00"));

         card.Withdraw(new BigDecimal("200.00"));

         assertEquals(0, new BigDecimal("300.00").compareTo(card.getBalance()), "There should be 300 left after withdrawal");
    }

    /**
     * Перевіряє захист від відходу в мінус.
     * Очікуємо помилку ArithmeticException, якщо спробувати зняти більше грошей, ніж є.
     */
    @Test
    void testWithdrawInsufficientFunds() {
        Card card = new Card();
        card.setBalance(new BigDecimal("100.00"));

        assertThrows(ArithmeticException.class, () -> {
            card.Withdraw(new BigDecimal("200.00"));
        }, "An error should occur when trying to withdraw more than is in the account");
    }

    /**
     * Перевіряє статус картки при операціях.
     * Очікуємо помилку IllegalStateException, якщо спробувати поповнити заблоковану картку.
     */
    @Test
    void testOperationsOnBlockedCard() {
        Card card = new Card();
        card.setStatus(AccountStatus.BLOCKED);

        assertThrows(IllegalStateException.class, () -> {
            card.TopUp(new BigDecimal("100"));
        }, "You cannot top up a blocked card");
    }
}
