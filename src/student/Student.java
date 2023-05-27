
package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
//    insert date to student table
    public void insert(int id, String name,String date,String gender,String email,
            String phone,String father,String mother,String address1,String address2,String imagePath){
            String sql = "insert into student values(?,?,?,?,?,?,?,?,?,?,?)";
    
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, father);
            preparedStatement.setString(8, mother);
            preparedStatement.setString(9, address1);
            preparedStatement.setString(10, address2);
            preparedStatement.setString(11, imagePath);
            if(preparedStatement.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "New student add succesfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    public boolean isEmailExist(String email){
        try {
            
        } catch (Exception e) {
            preparedStatement = connection.prepareStatement("select * from student where email = ?");
            
        } 
        
    }
}
