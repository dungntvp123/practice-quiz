/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Subject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnect;

/**
 *
 * @author Asus
 */
public class SubjectDAO extends DBConnect {

    private static SubjectDAO instance = null;

    private SubjectDAO() {

    }

    public static synchronized SubjectDAO getInstance() {
        if (instance == null) {
            instance = new SubjectDAO();
        }
        return instance;
    }

    public int addSubject(Subject subject) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Subject]\n"
                + "           ([name]\n"
                + "           ,[numofchapter])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?)";
        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, subject.getName());
            pre.setInt(2, subject.getNumOfChapter());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

    public Subject getSubject(int id) {
        final String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[numofchapter]\n"
                + "  FROM [SWP211].[dbo].[Subject]\n"
                + "  WHERE id = ?";
        startConnection();
        try ( PreparedStatement statement = prepareStatement(sql, id)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Subject(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return null;
    }

    public Vector<Subject> getSubject(String name) {
        startConnection();
        Vector<Subject> subjects = new Vector<>();
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[numofchapter]\n"
                + "  FROM [dbo].[Subject]"
                + "WHERE " + (name.isEmpty() ? "1=1" : "name ='" + name + "'");
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setId(rs.getInt("id"));
                sub.setNumOfChapter(rs.getInt("numofchapter"));
                sub.setName(rs.getString("name"));
                subjects.add(sub);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return subjects;
    }

    public Vector<Subject> getAllSubject() {
        startConnection();
        Vector<Subject> subjects = new Vector<>();
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[numofchapter]\n"
                + "  FROM [dbo].[Subject]";
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setId(rs.getInt("id"));
                sub.setNumOfChapter(rs.getInt("numofchapter"));
                sub.setName(rs.getString("name"));
                subjects.add(sub);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return subjects;
    }

    public int updateSubject(Subject subject, int id) {
        int n = 0;
        startConnection();
        String sql = "UPDATE [dbo].[Subject]\n"
                + "   SET [name] = ?\n"
                + "      ,[numofchapter] = ?\n"
                + " WHERE [id] = ?";
        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, subject.getName());
            pre.setInt(2, subject.getNumOfChapter());
            pre.setInt(3, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

}
