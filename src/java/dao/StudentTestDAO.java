/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.StudentTest;
import entity.StudentTestDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.DBConnect;

/**
 * @author ACER NITRO
 */
public class StudentTestDAO extends DBConnect {
    
    private static StudentTestDAO instance = null;
    
    private StudentTestDAO() {
        
    }
    
    public static synchronized StudentTestDAO getInstance() {
        if (instance == null) {
            instance = new StudentTestDAO();
        }
        return instance;
    }
    
    public StudentTest getStudentTest(int studentid, int testid) {
        StudentTest studentTest = new StudentTest();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[Studentid]\n"
                + "      ,[Testid]\n"
                + "      ,[starttime]\n"
                + "      ,[finishedtime]\n"
                + "      ,[totalscore]\n"
                + "  FROM [dbo].[Student_Test] WHERE Testid = " + testid + "and Studentid = " + studentid;
        ResultSet rs;
        try (Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                studentTest.setId(rs.getInt("id"));
                studentTest.setStudentId(rs.getInt("Studentid"));
                studentTest.setTestId(rs.getInt("Testid"));
                studentTest.setStartTime(rs.getTimestamp("starttime"));
                studentTest.setFinishedTime(rs.getTimestamp("finishedtime"));
                studentTest.setTotalScore(rs.getDouble("totalscore"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return studentTest;
    }

    public StudentTest getStudentTest(int id) {
        StudentTest studentTest = new StudentTest();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[Studentid]\n"
                + "      ,[Testid]\n"
                + "      ,[starttime]\n"
                + "      ,[finishedtime]\n"
                + "      ,[totalscore]\n"
                + "  FROM [dbo].[Student_Test] WHERE id = " + id;
        ResultSet rs;
        try (Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                studentTest.setId(rs.getInt("id"));
                studentTest.setStudentId(rs.getInt("Studentid"));
                studentTest.setTestId(rs.getInt("Testid"));
                studentTest.setStartTime(rs.getTimestamp("starttime"));
                studentTest.setFinishedTime(rs.getTimestamp("finishedtime"));
                studentTest.setTotalScore(rs.getDouble("totalscore"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return studentTest;
    }

    public Vector<StudentTest> getStudentTestsFromTest(int testID) {
        startConnection();
        Vector<StudentTest> studentTests = new Vector<>();
        String sql = "SELECT * from Student_Test st\n" +
                "WHERE st.Testid = ?";
        try (PreparedStatement statement = prepareStatement(sql, testID)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StudentTest studentTest = new StudentTest();
                studentTest.setId(resultSet.getInt("id"));
                studentTest.setStudentId(resultSet.getInt("Studentid"));
                studentTest.setTestId(resultSet.getInt("Testid"));
                studentTest.setStartTime(resultSet.getTimestamp("starttime"));
                studentTest.setFinishedTime(resultSet.getTimestamp("finishedtime"));
                studentTest.setTotalScore(resultSet.getDouble("totalscore"));
                studentTests.add(studentTest);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return studentTests;
    }

    public StudentTestDetail getStudentTestDetail(int STid, int TDid) {
        startConnection();
        StudentTestDetail ctd = new StudentTestDetail();
        String sql = "SELECT [id]\n"
                + "      ,[STid]\n"
                + "      ,[TDid]\n"
                + "      ,[selected]\n"
                + "      ,[score]\n"
                + "  FROM [dbo].[Student_Test_Detail] WHERE STid = " + STid + " and TDid = " + TDid;

        ResultSet rs;
        try (Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                ctd.setId(rs.getInt("id"));
                ctd.setSTid(rs.getInt("STid"));
                ctd.setTDid(rs.getInt("TDid"));
                ctd.setSelected(rs.getString("selected"));
                ctd.setScore(rs.getDouble("score"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return ctd;
    }

    public Vector<StudentTestDetail> getStudentTestDetailsOfTest(int STid) {
        startConnection();
        Vector<StudentTestDetail> stdList = new Vector<>();
        String sql = "SELECT TOP (1000) [id]\n" +
                "      ,[STid]\n" +
                "      ,[TDid]\n" +
                "      ,[selected]\n" +
                "      ,[score]\n" +
                "  FROM [SWP211].[dbo].[Student_Test_Detail]\n" +
                "  WHERE STid = ?";
        try (PreparedStatement statement = prepareStatement(sql,STid)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                StudentTestDetail std = new StudentTestDetail();
                std.setId(resultSet.getInt("id"));
                std.setSTid(resultSet.getInt("STid"));
                std.setTDid(resultSet.getInt("TDid"));
                std.setSelected(resultSet.getString("selected"));
                std.setScore(resultSet.getDouble("score"));
                stdList.add(std);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return stdList;
    }

    public int setStudentTest(StudentTest st) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Student_Test]\n"
                + "           ([Studentid]\n"
                + "           ,[Testid]\n"
                + "           ,[starttime]\n"
                + "           ,[finishedtime]\n"
                + "           ,[totalscore])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, st.getStudentId());
            pre.setInt(2, st.getTestId());
            pre.setTimestamp(3, st.getStartTime());
            pre.setTimestamp(4, st.getFinishedTime());
            pre.setDouble(5, st.getTotalScore());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentTestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

    public int setStudentTestDetail(StudentTestDetail std) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Student_Test_Detail]\n"
                + "           ([STid]\n"
                + "           ,[TDid]\n"
                + "           ,[selected]\n"
                + "           ,[score])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, std.getSTid());
            pre.setInt(2, std.getTDid());
            pre.setString(3, std.getSelected());
            pre.setDouble(4, std.getScore());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentTestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

    public int updateStudentTest(StudentTest st) {
        int n = 0;
        startConnection();
        String sql = "UPDATE [dbo].[Student_Test]\n"
                + "   SET [Studentid] = ?\n"
                + "      ,[Testid] = ?\n"
                + "      ,[starttime] = ?\n"
                + "      ,[finishedtime] = ?\n"
                + "      ,[totalscore] = ?\n"
                + " WHERE Studentid = " + st.getStudentId() + " and Testid = " + st.getTestId();
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, st.getStudentId());
            pre.setInt(2, st.getTestId());
            pre.setTimestamp(3, st.getStartTime());
            pre.setTimestamp(4, st.getFinishedTime());
            pre.setDouble(5, st.getTotalScore());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentTestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }
}
