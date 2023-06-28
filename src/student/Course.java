/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class Course {
    Connection connection = MyConnection.getConnection();
    PreparedStatement preparedStatement;
    
//    get table max row
    public int getMaxRow() {
        int id = 0;
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select max(ID) from course");
            while(resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }
    
    public boolean getId(int id){
        try {
            preparedStatement = connection.prepareStatement("select * from student where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Home.jTextField11.setText(String.valueOf(rs.getInt(1)));
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "Student id does not exist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int countSemester(int id){
        int total = 0;
        try {
            preparedStatement = connection.prepareStatement("select count(*) as 'total' from course where student_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
                if (total == 8) {
                    JOptionPane.showMessageDialog(null, "This student has completed all the courses");
                    return -1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
         return total;
    }
    
//    check whether the student has already taken this semester or not
    public boolean isSemesterExist(int sid, int semesterNo){
        try {
            preparedStatement = connection.prepareStatement("select * from course where student_id = ? and semester=?");
            preparedStatement.setInt(1, sid);
            preparedStatement.setInt(2, semesterNo);
             ResultSet rs = preparedStatement.executeQuery();
             if(rs.next()){
                 return true;
             }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     public boolean isCourseExist(int sid, String courseNo, String course){
         String sql = "select * from course where student_id = ? and "+ courseNo+" = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, sid);
            preparedStatement.setString(2, course);
             ResultSet rs = preparedStatement.executeQuery();
             if(rs.next()){
                 return true;
             }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//     insert data into course table
     public void insert(int id, int sid,int semester,String course1,String course2,
            String course3,String course4,String course5){
            String sql = "insert into course values(?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, sid);
            preparedStatement.setInt(3, semester);
            preparedStatement.setString(4, course1);
            preparedStatement.setString(5, course2);
            preparedStatement.setString(6, course3);
            preparedStatement.setString(7, course4);
            preparedStatement.setString(8, course5);
            if(preparedStatement.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "New course add succesfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
//     get all the course values from database course table
     public void getCoursetValue(JTable table,String searchValue){
         String sql = "select * from course where concat(id,student_id,semester)like ? order by id asc";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+searchValue+"%");
            ResultSet rs = preparedStatement.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[8];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
            model.addRow(row);
        }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}

