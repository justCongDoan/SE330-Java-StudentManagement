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
 * @author Admin
 */
public class Score {

    Connection connection = MyConnection.getConnection();
    PreparedStatement preparedStatement;

    public int getMaxRow() {
        int id = 0;
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select max(ID) from score");
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public boolean getDetails(int sid, int semesterNo) {
        try {
            preparedStatement = connection.prepareStatement("select * from course where student_id = ? and semester = ?");
            preparedStatement.setInt(1, sid);
            preparedStatement.setInt(2, semesterNo);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Home.jTextField14.setText(String.valueOf(rs.getInt(2)));
                Home.jTextField16.setText(String.valueOf(rs.getInt(3)));
                Home.jTextCourse1.setText(rs.getString(4));
                Home.jTextCourse2.setText(rs.getString(5));
                Home.jTextCourse3.setText(rs.getString(6));
                Home.jTextCourse4.setText(rs.getString(7));
                Home.jTextCourse5.setText(rs.getString(8));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Student id or semester number does not exist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isIdExist(int id) {
        try {
            preparedStatement = connection.prepareStatement("select * from score where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isSidSemesterNoExist(int sid, int semesterNo) {
        try {
            preparedStatement = connection.prepareStatement("select * from score where student_id = ? and semester = ?");
            preparedStatement.setInt(1, sid);
            preparedStatement.setInt(1, semesterNo);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insert(int id, int sid, int semester, String course1, String course2,
            String course3, String course4, String course5, double score1, double score2,
            double score3, double score4, double score5, double average) {
        String sql = "insert into score values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, sid);
            preparedStatement.setInt(3, semester);
            preparedStatement.setString(4, course1);
            preparedStatement.setDouble(5, score1);
            preparedStatement.setString(6, course2);
            preparedStatement.setDouble(7, score2);
            preparedStatement.setString(8, course3);
            preparedStatement.setDouble(9, score3);
            preparedStatement.setString(10, course4);
            preparedStatement.setDouble(11, score4);
            preparedStatement.setString(12, course5);
            preparedStatement.setDouble(13, score5);
            preparedStatement.setDouble(14, average);
            if (preparedStatement.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Score added succesfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getScoreValue(JTable table, String searchValue) {
        String sql = "select * from score where concat(id,student_id,semester)like ? order by id asc";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchValue + "%");
            ResultSet rs = preparedStatement.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[14];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getDouble(7);
                row[7] = rs.getString(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getDouble(11);
                row[11] = rs.getString(12);
                row[12] = rs.getDouble(13);
                row[13] = rs.getDouble(14);

                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(int id, double score1, double score2, double score3, double score4, double score5, double average) {

        String sql = "update score set score1=?, score2=?, score3=?, score4=?, score5=?, average=? where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, score1);
            preparedStatement.setDouble(2, score2);
            preparedStatement.setDouble(3, score3);
            preparedStatement.setDouble(4, score4);
            preparedStatement.setDouble(5, score5);
            preparedStatement.setDouble(6, average);
            preparedStatement.setInt(7, id);

            if (preparedStatement.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Score updated successfully!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
