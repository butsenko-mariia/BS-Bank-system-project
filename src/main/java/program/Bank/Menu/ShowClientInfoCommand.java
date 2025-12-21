package program.Bank.Menu;

import program.Bank.Client;

public class ShowClientInfoCommand implements Command {
    private final Client client;

    public ShowClientInfoCommand(Client client) {
        this.client = client;
    }

    @Override
    public Result execute() {
        System.out.println("\n--- ДАНІ КЛІЄНТА ---");
        System.out.println("ID: #" + client.getId());
        System.out.println("ПІБ: " + client.getFull_name());
        System.out.println("Дата народження: " + client.getDate_of_birth());
        System.out.println("Стать: " + client.getSex());
        System.out.println("Телефон: " + client.getMobile_phone());
        System.out.println("ІПН: " + client.getIndividual_tax_number());
        System.out.println("Паспорт: " + client.getPassport_number());
        System.out.println("Адреса: " + client.getLegal_address());
        System.out.println("Статус: " + client.getStatus());
        System.out.println("--------------------");
        return Result.CONTINUE;
    }

    @Override
    public String name() {
        return "info"; // Пункт 6
    }
}