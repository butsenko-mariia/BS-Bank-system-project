package program.Bank.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import program.Bank.DataBase;
import program.Bank.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private DataBase dataBase;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldCreateTransaction() {
         transactionService.createTransaction(
                UUID.randomUUID(),       // Від кого
                UUID.randomUUID(),       // Кому
                new BigDecimal("100.00"), // Сума
                "UAH",                   // Валюта
                "Test Payment"           // Опис
        );

         verify(dataBase, times(1)).Upload(any(Transaction.class));
    }
}