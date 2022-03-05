package BusinessRules.Interfaces;

import java.sql.Connection;

import static DB.DeclareDB.*;
import static DB.DeclareDB.PASS;
import static java.sql.DriverManager.getConnection;


public interface IStorageManager {
    static Connection openConnection(){
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
        } catch (Exception se) {
            se.printStackTrace();
        }
        return conn;
    };




}
