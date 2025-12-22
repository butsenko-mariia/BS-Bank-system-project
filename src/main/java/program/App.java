package program;

import program.Bank.Builders.MenuBuilder;
import program.Bank.Menu.Menu;

public class App {

   public static void main(String[] args) {
       MenuBuilder builder = new MenuBuilder();
       Menu mainMenu = builder.MainMenu();
       mainMenu.execute();
       }
}