package program.Bank.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import program.Bank.DataBase;
import program.Bank.Transaction;
import program.Bank.Enums.TransactionStatus;

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
                UUID.randomUUID(),
                UUID.randomUUID(),
                new BigDecimal("100.00"),
                "UAH",
                "Test Payment",
                TransactionStatus.COMPLETED
        );

        verify(dataBase, times(1)).Upload(any(Transaction.class));
    }
}