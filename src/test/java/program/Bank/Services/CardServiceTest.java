package program.Bank.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import program.Bank.Card;
import program.Bank.DataBase;
import program.Bank.Enums.CardType;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private DataBase dataBase;

    @InjectMocks
    private CardService cardService;

    @Test
    void shouldCreateCard() {
        // Дія: Створюємо картку
        cardService.CreateCard(UUID.randomUUID(), "1111222233334444", CardType.UNIVERSAL, "UAH");

        // Перевірка: Чи пішов запит у базу?
        verify(dataBase, times(1)).Upload(any(Card.class));
    }
}