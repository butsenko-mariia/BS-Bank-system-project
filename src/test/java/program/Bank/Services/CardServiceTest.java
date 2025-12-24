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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Перевіряє процес відкриття нової картки.
 * Головна мета: впевнитися, що при виклику CreateCard створюється об'єкт Card
 * із вказаними параметрами (валюта, тип) і відправляється на збереження в базу.
 */
@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private DataBase dataBase;

    @InjectMocks
    private CardService cardService;

    @Test
    void shouldCreateCardWithRealData() {
        UUID clientId = UUID.randomUUID();
        String cardNumber = "4149499988887777";
        CardType cardType = CardType.UNIVERSAL;
        String currency = "UAH";

        cardService.CreateCard(
                clientId,
                cardNumber,
                cardType,
                currency
        );

        verify(dataBase, times(1)).Upload(any(Card.class));
    }
}