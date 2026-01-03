package program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import program.Bank.Builders.MenuBuilder;
import program.Bank.Menu.Menu;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
@SpringBootApplication
public class App {

   public static void main(String[] args) {
       SpringApplication.run(App.class, args);

   }
}