/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnect;
import utility.Utility;

/**
 *
 * @author Asus
 */
public class StudentDAO extends DBConnect {

    private static StudentDAO instance = null;

    private StudentDAO() {

    }

    public static synchronized StudentDAO getInstance() {
        if (instance == null) {
            instance = new StudentDAO();
        }
        return instance;
    }

    public int setStudent(int userid, int status) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Student]\n"
                + "           ([userid]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?)";
        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, userid);
            pre.setInt(2, status);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return n;
    }

    public Student getStudent(int userid) {
        Student student = new Student();
        startConnection();
        String sql = "SELECT Student.id as 'sid', Student.userid, username, [password], firstname, lastname, email, [time], [role], [status]\n"
                + "from [User] inner join Student on [User].id = Student.userid and [User].id = " + userid;

        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                student.setId(rs.getInt("sid"));
                student.setUserid(rs.getInt("userid"));
                student.setUsername(rs.getString("username"));
                student.setPassword(Utility.decode(rs.getString("password")));
                student.setFirstname(rs.getString("firstname"));
                student.setLastname(rs.getString("lastname"));
                student.setEmail(rs.getString("email"));
                student.setCreateTime(Timestamp.valueOf(rs.getString("time")));
                student.setRole(rs.getInt("role"));
                student.setStatus(rs.getInt("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return student;
    }

    public Vector<Student> getStudents(int classid) {
        Vector<Student> students = new Vector<>();
        startConnection();
        String sql = "SELECT Student.[id] as [sid]\n"
                + "      ,[firstname]\n"
                + "      ,[lastname]\n"
                + "  FROM [dbo].[User] inner join  [dbo].[Student] on [User].id = [Student].userid\n"
                + "  WHERE [User].id in \n"
                + "  (SELECT [userid] FROM [dbo].[Student] WHERE id in \n"
                + "  (SELECT [studentid] FROM [dbo].[Student_Class_Detail] WHERE Classid = " + classid + "))";
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("sid"));
                student.setFirstname(rs.getString("firstname"));
                student.setLastname(rs.getString("lastname"));
                students.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return students;
    }

    public Vector<Student> getAllStudents() {
        Vector<Student> students = new Vector<>();
        startConnection();
        String sql = "SELECT Student.[id] as [sid]\n"
                + "                     ,[firstname]\n"
                + "                      ,[lastname]\n"
                + "                  FROM [dbo].[User] inner join  [dbo].[Student] on [User].id = [Student].userid";
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("sid"));
                student.setFirstname(rs.getString("firstname"));
                student.setLastname(rs.getString("lastname"));
                students.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return students;
    }

    public int updateStudent(int userid, int status) {
        int n = 0;
        startConnection();
        String sql = "UPDATE [dbo].[Student]\n"
                + "   SET [status] = ?\n"
                + " WHERE [userid] = ?";
        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, status);
            pre.setInt(2, userid);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }
}
