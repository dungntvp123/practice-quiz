/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnect;
import utility.Utility;



/**
 *
 * @author Asus
 */
public class InformalDAO extends DBConnect {

    private static InformalDAO instance = null;
    
    private InformalDAO() {
        
    }
    
    public static synchronized InformalDAO getInstance() {
        if (instance == null) {
            instance = new InformalDAO();
        }
        return instance;
    }
    public int changePassword(boolean confirm, String email, String newPassword, String confirmkey) {
        if (!confirm) {
            return 0;
        }
        startConnection();
        int n = 0;
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET [password] = '" + Utility.encode(newPassword) + "',[confirmkey]='" + confirmkey + "' WHERE [User].email = '" + email + "'";
        try ( Statement state = conn.createStatement()) {
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return n;
    }
    
    public boolean isStudentUserInClass(int userId, int classId){
        Student student = StudentDAO.getInstance().getStudent(userId);
        if (student == null){
            System.out.println("Null");
            return false;
        }
        startConnection();
        String sql = "SELECT [id]\n" +
                "  FROM [SWP211].[dbo].[Student_Class_Detail]\n" +
                "  WHERE studentid = ? and classId = ?";
        try (PreparedStatement statement = prepareStatement(sql,student.getId(),classId)){
            try (ResultSet rs = statement.executeQuery()){
                System.out.println(student.getId());
                System.out.println(classId);
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        System.out.println("Here");
        return false;
    }
    
    public void listAll(Vector list) {
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
        }
    }

    public void listAll(List list) {
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
        }
    }

}
