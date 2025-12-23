package program;

import program.Bank.Builders.MenuBuilder;
import program.Bank.Menu.Menu;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class App {

   public static void main(String[] args) {
       try {
           System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
       } catch (Exception e) {
           e.printStackTrace();
       }
       MenuBuilder builder = new MenuBuilder();
       Menu mainMenu = builder.MainMenu();
       mainMenu.execute();
       }
}