package program;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import program.Bank.*;
import program.Bank.Services.CardService;
import program.Bank.Services.ClientService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import program.Bank.Services.DepositeService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MainController {

    private final ClientService clientService = new ClientService(DataBase.getInstance());
    private final CardService cardService = new CardService(DataBase.getInstance());
    private final DepositeService depositService = new DepositeService(DataBase.getInstance());
    private final Logger log = LogManager.getLogger(MainController.class);

    @PostMapping("/auth/login")
    public Object login(@RequestBody Map<String, String> request) {

        String searchType = request.get("searchType");
        String searchValue = request.get("searchValue");
        String password = request.get("password");

        log.info("Спроба входу через веб: {} = {}", searchType, searchValue);

        Client client = clientService.SearchClient(searchType, searchValue);

        if (client == null) {
            return Map.of("status", "error", "message", "Клієнта не знайдено");
        }
        return Map.of(
                "status", "success",
                "pib", client.getFull_name(),       // Переконайтеся, що у вас є геттер getFullName() або getPib()
                "phone", client.getMobile_phone(),  // Переконайтеся, що є getMobilePhone()
                "passport", client.getPassport_number(),
                "clientId", client.getId().toString() // Корисно передати ID для майбутніх запитів
        );
    }

    @PostMapping("/auth/register")
    public Object createClient(@RequestBody Map<String, String> form) {
        log.info("Реєстрація клієнта: {}", form.get("pib"));

        try {
            Client client = clientService.RegisterClient(
                    form.get("pib"),           // full_name
                    form.get("birthdate"),     // date_of_birth
                    form.get("gender"),        // sex
                    form.get("citizenship"),   // nationality
                    form.get("phone"),         // mobile_phone
                    form.get("ipn"),           // individual_tax_number
                    form.get("passport"),      // passport_number
                    form.get("legalAddress"),  // legal_address
                    form.get("birthplace"),    // place_of_birth
                    form.get("recordNumber"),  // record_number
                    form.get("workplace")      // place_of_work_or_study
            );

            // client.setPin(form.get("pin"));

            return Map.of("status", "success", "message", "Клієнт створений");

        } catch (Exception e) {
            log.error("Помилка реєстрації", e);
            return Map.of("status", "error", "message", e.getMessage());
        }
    }

    @GetMapping("/cards")
    public Object getCards(@RequestParam String id) { // id клієнта
        UUID clientUuid = UUID.fromString(id);
        Client tempClient = new Client(clientUuid);

        return  cardService.GetAllClientsCards(tempClient);
    }

    @GetMapping("/cards/info")
    public Object getCardShortInfo(@RequestParam String id) { // id клієнта
        UUID Uuid = UUID.fromString(id);
        Card card = new Card(Uuid);

        return  cardService.PrintShortInfo(card);
    }

    @PostMapping("/cards/create")
    public Object createCard(@RequestBody Map<String, String> request) {
        Card card = cardService.CreateCard(
                request.get("clientId"),
                request.get("cardNumber") ,
                request.get("cardType"),
                request.get("currency")
        );

        return card;
    }

    @PostMapping("/cards/topup")
    public Object topUpCard(@RequestBody Map<String, String> request) {
        Card card = cardService.GetCardByNumber(request.get("cardNumber"));

        BigDecimal amount = new BigDecimal(request.get("amount"));
        boolean result = cardService.TopUpCard(card, amount);

        if (result) {
            return Map.of("status", "success");
        }
        else{
            return Map.of("status", "error");
        }
    }

    @PostMapping("/cards/withdraw")
    public Object withdrawCard(@RequestBody Map<String, String> request) {
        Card card = cardService.GetCardByNumber(request.get("cardNumber"));

        BigDecimal amount = new BigDecimal(request.get("amount"));
        boolean result = cardService.WithDraw(card, amount);

        if (result) {
            return Map.of("status", "success");
        }
        else{
            return Map.of("status", "error");
        }
    }

    @PostMapping("/cards/transfer")
    public Object transferCard(@RequestBody Map<String, String> request) {
        Card card = cardService.GetCardByNumber(request.get("cardNumber"));

        BigDecimal amount = new BigDecimal(request.get("amount"));
        boolean result = cardService.Transfer(card, request.get("receiverCardNumber"), amount);

        if (result) {
            return Map.of("status", "success");
        }
        else{
            return Map.of("status", "error");
        }
    }

    @PostMapping("/cards/details")
    public Object showDetailsCard(@RequestBody Map<String, String> request) {
        String cardNumber = request.get("cardNumber");
        Card card = cardService.GetCardByNumber(cardNumber);


        String info = cardService.PrintFullDetails(card);
        return info;
    }

    @PostMapping("/cards/block")
    public Object blockCard(@RequestBody Map<String, String> request) {
        String cardNumber = request.get("cardNumber");
        boolean result = cardService.BlockCard(cardNumber);

        if (result) {
            return Map.of("status", "success");
        } else {
            return Map.of("status", "error");
        }
    }

    @PostMapping("/cards/close")
    public Object closeCard(@RequestBody Map<String, String> request) {
        String cardNumber = request.get("cardNumber");
        boolean result = cardService.CloseCard(cardNumber);

        if (result) {
            return Map.of("status", "success");
        } else {
            return Map.of("status", "error");
        }
    }

    @PostMapping("/cards/delete")
    public Object deleteCard(@RequestBody Map<String, String> request) {
        String cardNumber = request.get("cardNumber");
        Card card = cardService.GetCardByNumber(cardNumber);

        if (card != null) {
            cardService.DeleteCard(card);
            return Map.of("message", "card was deleted");
        } else {
            return Map.of("status", "error", "message", "Card not found");
        }
    }

    @PostMapping("/deposits/close")
    public Object closeDeposit(@RequestBody String uuid) {
        UUID Uuid = UUID.fromString(uuid);
        String type = depositService.getDepositType(Uuid);
        Deposit deposit = type.equals("StandardDeposit") ? new StandardDeposit(Uuid) : new CapitalizationDeposit(Uuid);

        BigDecimal amount = depositService.CloseDeposit(deposit);
        return amount;
    }

    @PostMapping("/deposits/earlyclose")
    public Object earlyCloseDeposit(@RequestBody String uuid) {
        UUID Uuid = UUID.fromString(uuid);
        String type = depositService.getDepositType(Uuid);
        Deposit deposit = type.equals("StandardDeposit") ? new StandardDeposit(Uuid) : new CapitalizationDeposit(Uuid);

        BigDecimal amount = depositService.EarlyCloseDeposit(deposit);
        return amount;
    }

    @PostMapping("/deposits/details")
    public Object showDetailsDeposit(@RequestBody String uuid) {
        try {
            // 1. Очищення UUID
            String cleanUuid = uuid.replaceAll("\"", "").trim();
            UUID id = UUID.fromString(cleanUuid);

            // 2. Отримання типу
            String type = depositService.getDepositType(id);
            if (type == null) {
                return "Помилка: Невідомий тип депозиту.";
            }

            // 3. Створення об'єкту
            Deposit deposit = type.equals("StandardDeposit") ? new StandardDeposit(id) : new CapitalizationDeposit(id);

            // --- !!! ДОДАТИ ЦЕЙ РЯДОК !!! ---
            // Завантажуємо дані з бази даних в об'єкт
            DataBase.getInstance().Read(deposit);
            // -------------------------------

            // 4. Тепер дати заповнені, можна виводити деталі
            return depositService.PrintFullDetails(deposit);

        } catch (IllegalArgumentException e) {
            log.error("Невірний формат UUID: {}", uuid);
            return "Помилка: Невірний ідентифікатор.";
        } catch (Exception e) {
            log.error("Помилка при отриманні деталей", e); // Це покаже деталі в консолі
            return "Сталася помилка на сервері: " + e.getMessage();
        }
    }

    @PostMapping("/deposits/delete")
    public Object deleteDeposit(@RequestBody String uuid) {
        UUID Uuid = UUID.fromString(uuid);
        String type = depositService.getDepositType(Uuid);
        Deposit deposit = type.equals("StandardDeposit") ? new StandardDeposit(Uuid) : new CapitalizationDeposit(Uuid);
        depositService.DeleteDeposit(deposit);


        return Map.of("message","deposit was deleted");
    }

    // --- ОНОВЛЕНИЙ МЕТОД ОТРИМАННЯ ДЕПОЗИТІВ ---
    @GetMapping("/deposits")
    public Object getDeposits(@RequestParam String id) {
        try {
            UUID clientUuid = UUID.fromString(id);

            Client realClient = null;
            realClient = new Client(clientUuid);
            DataBase.getInstance().Read(realClient);

            if (realClient == null) {
                log.error("Клієнта з ID {} не знайдено при запиті депозитів", id);
                return List.of();
            }

            return depositService.getClientDeposits(realClient.getId());

        } catch (Exception e) {
            log.error("Помилка отримання депозитів", e);
            return List.of();
        }
    }

    // --- ОНОВЛЕНИЙ МЕТОД СТВОРЕННЯ ---
    @PostMapping("/deposits/create")
    public Object createDeposits(@RequestBody Map<String, String> request) {
        log.info("Отримано запит на депозит: {}", request);

        try {
            Deposit deposit = null;
            String type = request.get("type");

            // Перевіряємо тип і викликаємо відповідний метод
            if ("StandardDeposit".equals(type)) {
                deposit = depositService.OpenStandardDeposit(
                        request.get("clientId"),
                        request.get("originalSum"),
                        request.get("openDate"),
                        request.get("closeDate"),
                        request.get("interestRate"),
                        request.get("currency")
                );
            } else if ("CapitalizationDeposit".equals(type)) {
                deposit = depositService.OpenCapitalizationDeposit(
                        request.get("clientId"),
                        request.get("originalSum"),
                        request.get("openDate"),
                        request.get("closeDate"),
                        request.get("interestRate"),
                        request.get("currency")
                );
            }

            if (deposit == null) {
                log.error("Не вдалося створити депозит (сервіс повернув null)");
                return Map.of("status", "error", "message", "Не вдалося створити депозит");
            }

            log.info("Депозит успішно створено: {}", deposit.getId());
            return deposit;

        } catch (Exception e) {
            log.error("Критична помилка при створенні депозиту", e);
            // Повертаємо об'єкт з помилкою, щоб JS це зрозумів
            return Map.of("status", "error", "message", e.getMessage());
        }
    }
}