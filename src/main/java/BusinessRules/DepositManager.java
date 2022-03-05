package BusinessRules;

import BusinessRules.Interfaces.IDepositManager;
import BusinessRules.Interfaces.IStorageManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DepositManager implements IDepositManager {
    Connection conn = IStorageManager.openConnection();

    @Override
    public Deposit addDeposit(Client client, double amount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization) {

        java.sql.Date sqlDate = new java.sql.Date(startDate.getTime());
        Deposit gotDeposit = null;
        try {
            String sqlInsert = "INSERT INTO DEPOSIT(AMOUNT, PERCENT," +
                    "PRETERMPERCENT, TERMDATE," +
                    "STARTDATE, WITHPERCENTCAPITALIZATION," +
                    "CLIENTID)" +
                    " VALUES" +
                    "(?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement st = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            st.setDouble(1, amount);
            st.setDouble(2, percent);
            st.setDouble(3, pretermPercent);
            st.setInt(4, termDays);
            st.setDate(5, sqlDate);
            st.setBoolean(6, withPercentCapitalization);
            st.setInt(7, client.getId());

            st.execute();

            ResultSet keysSet = st.getGeneratedKeys();
            keysSet.next();
            int depositId = keysSet.getInt("id");

            String sqlSelect = "SELECT * FROM DEPOSIT WHERE ID = ?";
            PreparedStatement prSt = conn.prepareStatement(sqlSelect);
            prSt.setInt(1, depositId);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                gotDeposit = convertResultSetToDeposit(resultSet, depositId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return gotDeposit;
    }

    /**
     * метод возвращает список вкладов по ID клиента
     *
     * @param client
     * @return
     */

    @Override
    public List<Deposit> getClientDeposits(Client client) {
        List<Deposit> deposits = new ArrayList<>();
        try {
            String sqlSelect = "SELECT * FROM DEPOSIT WHERE CLIENTID = ?";
            PreparedStatement st = conn.prepareStatement(sqlSelect);
            st.setDouble(1, client.getId());
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                deposits.add(convertResultSetToDeposit(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deposits;
    }

    @Override
    public List<Deposit> getAllDeposits() {
        List<Deposit> deposits = new ArrayList<>();
        try {
            String sqlSelect = "SELECT * FROM DEPOSIT";
            PreparedStatement st = conn.prepareStatement(sqlSelect);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                deposits.add(convertResultSetToDeposit(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deposits;
    }

    @Override
    public double getEarnings(Deposit deposit, Date currentDate) {
        int currentDays = (int) TimeUnit.DAYS.convert(currentDate.getTime(), TimeUnit.MILLISECONDS);
        return deposit.isWithPercentCapitalization() ?
                deposit.getEarningsWithCap(deposit.getPercent(), currentDays) :
                deposit.getEarningsWithoutCap(deposit.getPercent(), currentDays);
    }


    /**
     * Метод удаляет запись о вкладе и возвращает сумму к выплате в кассе.
     * Если вклад закрывается досрочно, то сумма к выплате рассчитывается
     * исходя из процента при досрочном изъятии.
     */

    @Override
    public double removeDeposit(Deposit deposit, Date closeDate) {
        String sqlDelete = "DELETE FROM DEPOSIT WHERE ID = ?";
        String sqlSelect = "SELECT * FROM DEPOSIT WHERE ID = ?";
        PreparedStatement st = null;
        Deposit gotDeposit = null;
        try {
            st = conn.prepareStatement(sqlSelect);
            st.setInt(1, deposit.getId());
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                gotDeposit = convertResultSetToDeposit(resultSet); //проверка, действительно ли сущестует такой вклад в базе
            }
            double sum = 0;
            if (gotDeposit.getId() == deposit.getId()) {
                st = conn.prepareStatement(sqlDelete);
                st.setInt(1, gotDeposit.getId());
                int rowCount = st.executeUpdate();
                System.out.println(
                        "Запись была удалена из базы. Количество изменений: " + rowCount);
                int closeDays = (int) TimeUnit.DAYS.convert(closeDate.getTime(), TimeUnit.MILLISECONDS);
                double percent = isDepositClosingEarly(deposit, closeDate) ? deposit.getPretermPercent() : deposit.getPercent();
                return deposit.isWithPercentCapitalization() ?
                        deposit.getEarningsWithCap(percent, closeDays) :
                        deposit.getEarningsWithoutCap(percent, closeDays);
            } else {
                System.out.println("Поиск по базе не дал результатов");
                return -1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private Deposit convertResultSetToDeposit(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        double gotAmount = resultSet.getDouble("AMOUNT");
        double gotPercent = resultSet.getDouble("PERCENT");
        double gotPretermPercent = resultSet.getDouble("PRETERMPERCENT");
        int gotTermDays = resultSet.getInt("TERMDATE");
        Date gotStartDate = resultSet.getDate("STARTDATE");
        boolean gotWithPercentCapitalization = resultSet.getBoolean("WITHPERCENTCAPITALIZATION");
        return new Deposit(id, gotAmount, gotPercent, gotPretermPercent,
                gotTermDays, gotStartDate, gotWithPercentCapitalization);

    }

    private Deposit convertResultSetToDeposit(ResultSet resultSet, int depositId) throws SQLException {
        double gotAmount = resultSet.getDouble("AMOUNT");
        double gotPercent = resultSet.getDouble("PERCENT");
        double gotPretermPercent = resultSet.getDouble("PRETERMPERCENT");
        int gotTermDays = resultSet.getInt("TERMDATE");
        Date gotStartDate = resultSet.getDate("STARTDATE");
        boolean gotWithPercentCapitalization = resultSet.getBoolean("WITHPERCENTCAPITALIZATION");
        return new Deposit(depositId, gotAmount, gotPercent, gotPretermPercent,
                gotTermDays, gotStartDate, gotWithPercentCapitalization);
    }

    private boolean isDepositClosingEarly(Deposit deposit, Date closeDate) {
        long closeDays = TimeUnit.DAYS.convert(closeDate.getTime(), TimeUnit.MILLISECONDS);
        long startDays = TimeUnit.DAYS.convert(deposit.getStartDate().getTime(), TimeUnit.MILLISECONDS);
        return closeDays - startDays < deposit.getTermDays();
    }



}
