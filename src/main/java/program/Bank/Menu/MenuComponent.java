package program.Bank.Menu;

import program.Bank.Enums.Result;

public abstract class MenuComponent {
    protected String name;

    public MenuComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Result execute();
}
