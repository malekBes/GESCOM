package Conn;

import Config.Commen_Proc;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase_connect {

    private static String url;

    //public static Connection main(String args[ ]) 
    public Connection Open() {
        //String host = "jdbc:mysql://41.228.22.2:3306/";
        //String host = "jdbc:mysql://192.168.1.121:3306/";
        //jalel+hanen 3308 root root 
        //lamia 3306 root ""
        //malek 3308 root "root"
        String host = "jdbc:mysql://localhost:3308/";
        String dbName = "sops_prod";//_Statique
        String username = "root";
        String password = "root";
/*
        String host = "jdbc:mysql://41.228.22.2:3306/";
        String dbName = "sops_prod";//_Statique
        String username = "sodis";
        String password = "sodis@2022*";
*/
        /*  
        String host = "jdbc:mysql://196.203.63.40:3306/";
        String dbName = "aspvtn_Cloud_db";//
        String username = "aspvtn_root";
        String password = "MySQLDB2021*";*/

 /*  String username = "sodis";
        String password = "sodis";*/
        url = host + dbName + "?user=" + username + "&password=" + password;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection(url);
            System.out.println("Succeed!");
            return conn;
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found!");
        } catch (SQLException ex) {
            System.out.println("SQL exception! : " + ex.getMessage());
        }
        return null;
    }

    public void db_Cloase(Connection con) throws SQLException {
        con.close();
        return;
    }

}
