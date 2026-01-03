package program.Bank.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import program.Bank.DataBase;
import program.Bank.Deposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Перевіряє відкриття двох типів депозитів: Стандартного та з Капіталізацією.
 * Тест гарантує, що незалежно від типу депозиту, сервіс коректно звертається до бази даних
 * для збереження договору.
 */
@ExtendWith(MockitoExtension.class)
class DepositeServiceTest {

    @Mock
    private DataBase dataBase;

    @InjectMocks
    private DepositeService depositeService;

    @Test
    void shouldOpenStandardDeposit() {
        depositeService.OpenStandardDeposit(
                UUID.randomUUID(),
                new BigDecimal("1000.00"),
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                new BigDecimal("5.0"),
                "USD"
        );

        verify(dataBase, times(1)).Create(any(Deposit.class));
    }

    @Test
    void shouldOpenCapitalizationDeposit() {
        depositeService.OpenCapitalizationDeposit(
                UUID.randomUUID(),
                new BigDecimal("2000.00"),
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                new BigDecimal("4.5"),
                "EUR"
        );

        verify(dataBase, times(1)).Create(any(Deposit.class));
    }
}