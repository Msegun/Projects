package Zoo;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Created by msegun on 28.05.17.
 */
public class dbConnection {

    private static final String SQLITECONN = "jdbc:sqlite:zoo.sqlite";

    public static Connection getConnection() throws SQLException{

        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQLITECONN);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

}
