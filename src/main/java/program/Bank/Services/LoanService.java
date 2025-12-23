package program.Bank.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.*;
import program.Bank.Builders.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoanService {
    private final Logger log = LogManager.getLogger(LoanService.class);
    private final DataBase dataBase;
    private final ConsoleUI ui =  new ConsoleUI();

    public LoanService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Loan OpenLoan(UUID client_id, BigDecimal original_sum, LocalDate open_date, LocalDate close_date,
                         BigDecimal interest_rate, String currency){

        Loan loan = LoanBuilder.create()
                .client_id(client_id)
                .interest_rate(interest_rate)
                .original_sum(original_sum)
                .open_date(open_date)
                .close_date(close_date)
                .currency(currency)
                .build();
        dataBase.Upload(loan);
        return loan;
    }

    public void PrintFullDetails(Loan loan){
        loan.PrintFullInfo();
    }

    public void PrintShortInfo(Loan loan){
        loan.PrintInfo();
    }

    public void ShowAllClientLoans(Client client){
        String query = "SELECT id  FROM loan WHERE client_id = ?";
        boolean foundAny = false;

        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, client.getId());
            ResultSet rs = stmt.executeQuery();
            ui.print("Your loans:");

            while (rs.next()) {
                foundAny = true;
                UUID loanId = (UUID) rs.getObject("id");
                Loan loan = new Loan(loanId);

                if (loan != null) {
                    dataBase.Fetch(loan);
                    loan.PrintInfo();
                    ui.print("------------------------------");
                }
            }

            if (!foundAny) {
                String mes = "У даного клієнта немає відкритих кредитів.";
                log.warn(mes);
                ui.print(mes);

            }
        } catch (Exception e) {
            String mes = "Помилка при завантаженні кредитів: " + e.getMessage();
            log.error(mes);
            System.out.println(mes);
        }
    }

    public BigDecimal CloseLoan(Loan loan){
        loan.Close();
        dataBase.Update(loan);
        if (loan.getChange().compareTo(BigDecimal.ZERO) > 0){
            return loan.getChange();
        }
        return null;
    }

    public BigDecimal FullEarlyRepayment(Loan loan, BigDecimal amount){
        loan.FullEarlyRepayment(amount);
        dataBase.Update(loan);
        if (loan.getChange().compareTo(BigDecimal.ZERO) > 0){
            return loan.getChange();
        }
        return null;
    }

    public void DisplaySchedule(Loan loan){
        loan.DisplaySchedule();
    }

    public void RegularPayment(Loan loan, BigDecimal payment) throws Exception {
        try {
            loan.RegularPayment(payment);
        }
        catch(Exception e){
            ui.print(e.getMessage());
        }
    }

    public Loan GetLoan(UUID loan_id){
        Loan loan = new Loan(loan_id);
        dataBase.Fetch(loan);

        return loan;
    }

    public List<Loan> getClientLoans(UUID clientId) {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT id FROM loan WHERE client_id = ?";

        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UUID loanId = (UUID) rs.getObject("id");
                Loan loan = new Loan(loanId);
                dataBase.Fetch(loan);
                loans.add(loan);
            }
        } catch (Exception e) {
            log.error("Помилка отримання списку депозитів: " + e.getMessage());
        }
        return loans;
    }
}
