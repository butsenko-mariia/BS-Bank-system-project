package program.Bank.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import program.Bank.DataBase;
import program.Bank.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Перевіряє логіку видачі кредиту.
 * Тест симулює відкриття кредиту на певну суму та термін,
 * перевіряючи, що сформований об'єкт Loan передається в базу даних на збереження.
 */
@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private DataBase dataBase;

    @InjectMocks
    private LoanService loanService;

    @Test
    void shouldOpenLoan() {
        // Дія: Відкриваємо кредит
        loanService.OpenLoan(
                UUID.randomUUID(),
                new BigDecimal("5000.00"),
                LocalDate.now(),
                LocalDate.now().plusMonths(12),
                new BigDecimal("10.5"),
                "UAH"
        );

        verify(dataBase, times(1)).Create(any(Loan.class));
    }
}
