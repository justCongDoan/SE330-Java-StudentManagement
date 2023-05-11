
package db;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;

public class MyConnection {
    private static final String username = "root";
    private static final String password = "my_password";   // edit this line to YOUR password
    private static final String dataConnection = "jdbc:mysql://localhost:3306/student_management";
    private static Connection connection = null;
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dataConnection, username, password);
        } catch (Exception ex) {
             System.out.println(ex.getMessage());
        }
        return connection;
    }
}
