
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
            preparedStatement = connection.prepareStatement("select * from student where email = ?");
             preparedStatement.setString(1, email);
             ResultSet rs = preparedStatement.executeQuery();
             if(rs.next()){
                 return true;
             }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
     public boolean isPhoneExist(String phone){
        try {
            preparedStatement = connection.prepareStatement("select * from student where phone = ?");
            preparedStatement.setString(1, phone);
             ResultSet rs = preparedStatement.executeQuery();
             if(rs.next()){
                 return true;
             }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
      public boolean isIdExist(int id){
        try {
            preparedStatement = connection.prepareStatement("select * from student where id = ?");
            preparedStatement.setInt(1, id);
             ResultSet rs = preparedStatement.executeQuery();
             if(rs.next()){
                 return true;
             }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
     public void getStudentValue(JTable table,String searchValue){
         String sql = "select * from student where concat(id,name,email,phone)like ? order by id asc";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+searchValue+"%");
            ResultSet rs = preparedStatement.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[11];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
            model.addRow(row);
        }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
//     update student value
     public void update(int id, String name,String date,String gender,String email,
            String phone,String father,String mother,String address1,String address2,String imagePath){
         
         String sql = "update student set name=?, date_of_birth=?, gender=?, email=?, phone=?,"
                 + "father_name=?, mother_name=?, address1=?, address2=?, image_path=? where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, father);
            preparedStatement.setString(7, mother);
            preparedStatement.setString(8, address1);
            preparedStatement.setString(9, address2);
            preparedStatement.setString(10, imagePath);
            preparedStatement.setInt(11, id);
            if(preparedStatement.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Student data updated successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
//     student data delete 
     public void delete(int id){
        int yesNo =  JOptionPane.showConfirmDialog(null, "Course and score records will also be deleted","Student delete",JOptionPane.OK_OPTION);
        if(yesNo == JOptionPane.OK_OPTION){
            try {
                preparedStatement = connection.prepareStatement("delete from student where id = ?");
                preparedStatement.setInt(1, id);
                if(preparedStatement.executeUpdate()>0){
                    JOptionPane.showMessageDialog(null, "Student deleted successfully");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
     }
}
