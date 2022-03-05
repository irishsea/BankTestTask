//package DB;
//
//import BusinessRules.Interfaces.IStorageManager;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class CreateTables implements IStorageManager {
//    @Override
//    public void createTable(Connection conn) {
//        conn = IStorageManager.openConnection();
//        PreparedStatement stmt = null;
//        try {
//            System.out.println("Creating table in selected database...");
//
//            String sqlCreateTableClient =
//                    "CREATE TABLE IF NOT EXISTS client " +
//                            "(id INTEGER not NULL, " +
//                            " firstName VARCHAR(50), " +
//                            " lastName VARCHAR(50), " +
//                            " age INTEGER, " +
//                            " phone VARCHAR (50), " +
//                            " PRIMARY KEY (id))";
//            //stmt.execute(sqlCreateTableClient);
//            stmt = conn.prepareStatement(sqlCreateTableClient);
//            System.out.println(conn.getAutoCommit());
//            String sqlCreateTableDeposit =
//                    "CREATE TABLE IF NOT EXISTS deposit " +
//                            "(id INTEGER not NULL, " +
//                            " amount NUMERIC(20,2), " +
//                            " percent NUMERIC(20,2), " +
//                            " pretermPercent NUMERIC(20,2), " +
//                            " termDate INTEGER, " +
//                            " startDate DATE, " +
//                            " withPercentCapitalization BOOLEAN, " +
//                            " phone VARCHAR (50), " +
//                            " clientID INTEGER, " +
//                            " FOREIGN KEY (clientID) REFERENCES client (id), " +
//                            " PRIMARY KEY (id))";
//            stmt.executeUpdate(sqlCreateTableDeposit);
//            System.out.println("Tables successfully created...");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } finally {
//            //finally block used to close resources
//            try {
//                if (stmt != null) stmt.close();
//            } catch (SQLException ignored) {
//            } // nothing we can do
//        }
//    }
//}
