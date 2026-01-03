package program.Bank.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import program.Bank.Client;
import program.Bank.DataBase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private DataBase dataBase;

    @InjectMocks
    private ClientService clientService;

    @Test
    void shouldRegisterClient() {
        // 1. Дія: Спробуємо зареєструвати клієнта
        clientService.RegisterClient(
                "Taras Test", "2000-01-01", "Male", "UA",
                "0991234567", "1111111111", "AA000000",
                "Kyiv", "Kyiv", "rec1", "Work"
        );

        // 2. Перевірка: Чи зберіг сервіс клієнта в базу?
        verify(dataBase, times(1)).Create(any(Client.class));
    }
}