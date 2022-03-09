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
        Client client2 = new Client(2, "сян лин");
        Client client3 = new Client(3, "сахароза");
        Client client4 = new Client(4, "фишль");
        Client client5 = new Client(5, "беннет");

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//        Date date1 = formatter.parse("2022-01-01");
//        Date date2 = formatter.parse("2022-01-01");
//        Date date3 = formatter.parse("2022-01-01");
//        Date date4 = formatter.parse("2022-01-01");
//
//        java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
//        java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());
//        java.sql.Date sqlDate3 = new java.sql.Date(date3.getTime());
//        java.sql.Date sqlDate4 = new java.sql.Date(date4.getTime());
//
//        System.out.println("Создание вкладов... ");
//        Deposit deposit1 = depositManager.addDeposit(client1, 100, 5, 0.01, 365, sqlDate1, false);
//        Deposit deposit2 = depositManager.addDeposit(client3, 200, 7, 0.02, 90, sqlDate2, false);
//        Deposit deposit3 = depositManager.addDeposit(client2, 12345, 9, 0.01, 730, sqlDate3, true);
//        Deposit deposit4 = depositManager.addDeposit(client5, 50000, 10, 0.02, 365, sqlDate4, false);

        System.out.println("Вклады выбранного клиента: ");
        List<Deposit> deposits = depositManager.getClientDeposits(client2);
        for(Deposit deposit: deposits){
            System.out.println(deposit);
        }

        System.out.println("Все вклады: ");
        List<Deposit> allDeposits = depositManager.getAllDeposits();
        for(Deposit deposit: allDeposits ){
            System.out.println(deposit);
        }

//        Date startDate = formatter.parse("2022-03-02");
//        Date closeDate = formatter.parse("2022-09-02");
//        Date currentDate = formatter.parse("2022-07-02");
//        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
//        java.sql.Date sqlCloseDate = new java.sql.Date(closeDate.getTime());
//        java.sql.Date sqlCurrentDate = new java.sql.Date(currentDate.getTime());
//        Deposit deposit6 = depositManager.addDeposit(client5, 50000, 10, 0.01, 365, sqlStartDate, true);


//        Deposit deposit5 = new Deposit(72, 50000, 10, 0.01, 365, sqlStartDate, true, client5);
////        System.out.println("Сумма к выплате: " + depositManager.removeDeposit(deposit5, sqlCloseDate));
//        System.out.format("%.2f", depositManager.getEarnings(deposit5, sqlCurrentDate));

    }



}
