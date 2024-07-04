/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Question;
import entity.Selection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnect;

/**
 *
 * @author ACER NITRO
 */
public class SelectionDAO extends DBConnect {
    
    private static SelectionDAO instance = null;
    
    private SelectionDAO() {
        
    }
    
    public static synchronized SelectionDAO getInstance() {
        if (instance == null) {
            instance = new SelectionDAO();
        }
        return instance;
    }

    public int addSelection(Selection selection) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Selection]\n"
                + "           ([questionid]\n"
                + "           ,[coefficient]\n"
                + "           ,[charid]\n"
                + "           ,[description])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, selection.getQuestionId());
            pre.setDouble(2, selection.getCoefficent());
            pre.setString(3, selection.getCharId());
            pre.setString(4, selection.getDescription());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

    public Vector<Selection> getSelection(int questionid) {
        Vector<Selection> selections = new Vector<>();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[questionid]\n"
                + "      ,[coefficient]\n"
                + "      ,[charid]\n"
                + "      ,[description]\n"
                + "  FROM [dbo].[Selection] WHERE [questionid] = " + questionid;

        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Selection selection = new Selection();
                selection.setId(rs.getInt("id"));
                selection.setQuestionId(questionid);
                selection.setCoefficent(rs.getDouble("coefficient"));
                selection.setCharId(rs.getString("charid"));
                selection.setDescription(rs.getString("description"));
                selections.add(selection);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return selections;
    }
    
    public Vector<Selection> getSelectionsOfQuestion(int questionID) {
        Vector<Selection> selections = new Vector<>();
        startConnection();
        String sql = "SELECT TOP (7) [id]\n"
                + "      ,[questionid]\n"
                + "      ,[coefficient]\n"
                + "      ,[charid]\n"
                + "      ,[description]\n"
                + "  FROM [SWP211].[dbo].[Selection]\n"
                + "WHERE [questionid] = ?"
                + "  ORDER BY [charid] DESC";
        try ( PreparedStatement pre = prepareStatement(sql, questionID)) {
            ResultSet resultSet = pre.executeQuery();
            while (resultSet.next()) {
                Selection selection = new Selection(
                        resultSet.getInt("id"),
                        resultSet.getInt("questionid"),
                        resultSet.getDouble("coefficient"),
                        resultSet.getString("description"),
                        resultSet.getString("charid")
                );
                selections.add(selection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return selections;
    }

    public Map<Question, List<Selection>> loadSelection(List<Question> ques) {
        Map<Question, List<Selection>> test = new LinkedHashMap<>();
        
        for (Question question : ques) {
            startConnection();
            List<Selection> list = new ArrayList<>();
            String sql = "select * from selection where questionid = ? order by charid asc";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, question.getId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int quesid = rs.getInt("questionid");
                    double coff = rs.getDouble("coefficient");
                    String description = rs.getString("description");
                    String charId = rs.getString("charId");
                    list.add(new Selection(id, quesid, coff, description, charId));
                }
            } catch (SQLException e) {
                System.out.println("Error at load selection");
                System.out.println(e.getMessage());
            } finally {
                closeConnection();
            }
            
            test.put(question, list);
        }
        return test;
    }
    
    public Vector<Selection> getCorrectAnswer(int questionid) {
        Vector<Selection> selections = new Vector<>();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[questionid]\n"
                + "      ,[coefficient]\n"
                + "      ,[charid]\n"
                + "      ,[description]\n"
                + "  FROM [dbo].[Selection] WHERE [coefficient] > 0 and [questionid] = " + questionid;

        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Selection selection = new Selection();
                selection.setId(rs.getInt("id"));
                selection.setQuestionId(questionid);
                selection.setCoefficent(rs.getDouble("coefficient"));
                selection.setCharId(rs.getString("charid"));
                selection.setDescription(rs.getString("description"));
                selections.add(selection);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return selections;
    }
    
    public static void main(String[] args) {
        InformalDAO.getInstance().listAll(QuestionDAO.getInstance().loadQuestion(1));
        System.out.println();
        for (Map.Entry<Question, List<Selection>> entry : getInstance().loadSelection(QuestionDAO.getInstance().loadQuestion(1)).entrySet()) {
            System.out.println(entry.getKey());
        }
    }
    
}
