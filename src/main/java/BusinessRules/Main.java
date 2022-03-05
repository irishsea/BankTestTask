package BusinessRules;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws ParseException {
        DepositManager depositManager = new DepositManager();

        System.out.println("Создание клиентов... ");
        Client client1 = new Client(1, "чжун ли");
        Client client2 = new Client(2, "Сян Лин");
        Client client3 = new Client(3, "Сахароза");
        Client client4 = new Client(4, "Фишль");
        Client client5 = new Client(5, "Беннет");

//        System.out.println("Создание вкладов... ");
//        Deposit deposit1 = depositManager.addDeposit(client1, 100, 5, 6, 10, date, false);
//        Deposit deposit2 = depositManager.addDeposit(client3, 200, 5, 6, 10, date, false);
//        Deposit deposit3 = depositManager.addDeposit(client2, 12345, 5, 6, 10, date, false);
//        Deposit deposit5 = depositManager.addDeposit(client5, 50000, 10, 0.01, 365, date, false);

//        System.out.println(deposit1);
//        System.out.println(deposit2);
//        System.out.println(deposit3);

//        System.out.println("Вклады выбранного клиента: ");
//        List<Deposit> deposits = depositManager.getClientDeposits(client2);
//        for(Deposit deposit: deposits){
//            System.out.println(deposit);
//        }
//        System.out.println("Все вклады: ");
//        List<Deposit> deposits = depositManager.getAllDeposits();
//        for(Deposit deposit: deposits){
//            System.out.println(deposit);
//        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date startDate = formatter.parse("2022-03-02");
        Date closeDate = formatter.parse("2022-09-02");
        Date currentDate = formatter.parse("2022-07-02");
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlCloseDate = new java.sql.Date(closeDate.getTime());
        java.sql.Date sqlCurrentDate = new java.sql.Date(currentDate.getTime());
        Deposit deposit6 = depositManager.addDeposit(client5, 50000, 10, 0.01, 365, sqlStartDate, true);
        Deposit deposit5 = new Deposit(72, 50000, 10, 0.01, 365, sqlStartDate, true, client5);
//        System.out.println("Сумма к выплате: " + depositManager.removeDeposit(deposit5, sqlCloseDate));
        System.out.format("%.2f", depositManager.getEarnings(deposit5, sqlCurrentDate));

    }



}
