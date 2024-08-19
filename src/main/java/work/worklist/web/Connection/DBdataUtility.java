package work.worklist.web.Connection;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBdataUtility {
    public static Connection getConnection(String url, String name, String password){
        try {
            return DriverManager.getConnection(url, name, password);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
