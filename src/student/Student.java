
package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Student {
    Connection connection = MyConnection.getConnection();
    PreparedStatement preparedStatement;
    
//    get table max row
    public int getMaxRow() {
        int id = 0;
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select max(ID) from student");
            while(resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }
}
