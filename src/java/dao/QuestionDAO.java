package dao;

import entity.ExtendedQuestion;
import entity.Question;

import entity.Selection;
import utility.group.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnect;

public class QuestionDAO extends DBConnect {

    private static QuestionDAO instance = null;

    private QuestionDAO() {

    }

    public static synchronized QuestionDAO getInstance() {
        if (instance == null) {
            instance = new QuestionDAO();
        }
        return instance;
    }

    public Question getQuestion(int id) {
        startConnection();
        String sql = "SELECT * from Question WHERE id = ?";
        try ( PreparedStatement statement = prepareStatement(sql, id)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Question(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return null;
    }

    public int addQuestion(Question question) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Question]\n"
                + "           ([subjectid]\n"
                + "           ,[description]\n"
                + "           ,[chapter]\n"
                + "           ,[lectureid]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, question.getSubjectId());
            pre.setString(2, question.getDescription());
            pre.setInt(3, question.getChapter());
            pre.setInt(4, question.getLectureid());
            pre.setInt(5, question.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;

    }

    public ExtendedQuestion getQuestionFromID(int questionID) {
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[subjectid]\n"
                + "      ,[description]\n"
                + "      ,[chapter]\n"
                + "      ,[lectureid]\n"
                + "      ,[status]\n"
                + "  FROM [dbo].[Question]"
                + "WHERE [id] = ?";
        ExtendedQuestion question = null;
        try ( PreparedStatement pre = prepareStatement(sql, questionID)) {
            ResultSet resultSet = pre.executeQuery();
            if (resultSet.next()) {
                question = new ExtendedQuestion(
                        resultSet.getInt("lectureid"),
                        resultSet.getInt("subjectid"),
                        resultSet.getString("description"),
                        resultSet.getInt("chapter"),
                        resultSet.getInt("lectureid"),
                        resultSet.getInt("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return question;
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
                selections.add(0, selection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return selections;
    }

    public Vector<Map<String, Object>> getQuestionBank(int userID) {
        Vector<Map<String, Object>> bank = new Vector<>();
        startConnection();
        String sql = "with main as (SELECT q.id, s.name, CAST(q.description AS nvarchar(1000)) as description, q.chapter from Question q \n"
                + "join Subject s on q.subjectid = s.id\n"
                + "join Lecture l on q.lectureid = l.id\n"
                + "join [User] u on u.id = l.userid\n"
                + "WHERE u.id = ? AND q.status = 1)\n"
                + "SELECT *, 0 as isIN from main\n"
                + "WHERE id in (SELECT td.questionid from Test_Detail td join Test t on td.testid = t.id)\n"
                + "UNION\n"
                + "SELECT *, 1 as isIN from main\n"
                + "WHERE id not in (SELECT td.questionid from Test_Detail td join Test t on td.testid = t.id)";
        try ( PreparedStatement statement = prepareStatement(sql, userID)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> question = new HashMap<>();
                question.put("id", resultSet.getInt(1));
                question.put("subject", resultSet.getString(2));
                question.put("description", resultSet.getString(3));
                question.put("chapter", resultSet.getInt(4));
                question.put("isIn", resultSet.getInt(5));
                bank.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return bank;
    }

    public Vector<Map<String, Object>> getAllQuestion() {
        Vector<Map<String, Object>> bank = new Vector<>();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[subjectid]\n"
                + "      ,[description]\n"
                + "      ,[chapter]\n"
                + "      ,[lectureid]\n"
                + "      ,[status]\n"
                + "  FROM [SWP211].[dbo].[Question]";
        try ( Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, Object> question = new HashMap<>();
                question.put("id", resultSet.getInt(1));
                question.put("subject", SubjectDAO.getInstance().getSubject(resultSet.getInt(2)).getName());
                question.put("description", resultSet.getString(3));
                question.put("chapter", resultSet.getInt(4));
                question.put("lecture", LectureDAO.getInstance().getLectureUserFromId(resultSet.getInt(5)).getUsername());
                bank.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return bank;
    }

    public Vector<Map<String, Object>> getQuestionBankOfSubject(int subjectID) {
        Vector<Map<String, Object>> bank = new Vector<>();
        startConnection();
        String sql = "SELECT q.id, s.name, q.description, q.chapter from Question q \n"
                + "join Subject s on q.subjectid = s.id\n"
                + "join Lecture l on q.lectureid = l.id\n"
                + "join [User] u on u.id = l.userid\n"
                + "WHERE q.subjectid = ? AND q.status = 1 ORDER BY q.id ASC";
        try ( PreparedStatement statement = prepareStatement(sql, subjectID)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> question = new HashMap<>();
                question.put("id", resultSet.getInt(1));
                question.put("subject", resultSet.getString(2));
                question.put("description", resultSet.getString(3));
                question.put("chapter", resultSet.getInt(4));
                bank.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return bank;
    }

    public Vector<Pair<Integer, Double>> getQuestionsOfQuiz(int quizID) {
        Vector<Pair<Integer, Double>> questions = new Vector<>();
        startConnection();
        String sql = "SELECT td.questionid, td.coefficient from Test_Detail td WHERE testid = ? ORDER BY td.questionid ASC";
        try ( PreparedStatement statement = prepareStatement(sql, quizID)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(
                        new Pair<>(resultSet.getInt(1), resultSet.getDouble(2))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return questions;
    }

    public int getQuestionInsertId(Question question) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Question]\n"
                + "           ([subjectid]\n"
                + "           ,[description]\n"
                + "           ,[chapter]\n"
                + "           ,[lectureid]\n"
                + "           ,[status])\n"
                + "OUTPUT inserted.id\n"
                + "     VALUES\n"
                + "           (" + question.getSubjectId() + "\n"
                + "           ,'" + question.getDescription() + "'\n"
                + "           ," + question.getChapter() + "\n"
                + "           ," + question.getLectureid() + "\n"
                + "           ," + question.getStatus() + ")";
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                n = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

    public Vector<Question> getTestQuestion(int testid) {
        Vector<Question> questions = new Vector<>();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[subjectid]\n"
                + "      ,[description]\n"
                + "      ,[chapter]\n"
                + "  FROM [dbo].[Question] WHERE id IN \n"
                + "  (select [questionid] from Test_Detail where testid = " + testid + ")";
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Question question = new Question();
                question.setSubjectId(rs.getInt("subjectid"));
                question.setId(rs.getInt("id"));
                question.setDescription(rs.getString("description"));
                question.setChapter(rs.getInt("chapter"));
                questions.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return questions;
    }

    public List<Question> loadQuestion(int size, int subjectId, int chap) {
        List<Question> list = new ArrayList<>();
        ResultSet rs;
        startConnection();
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery("select * from Question where subjectid =" + subjectId
                    + (chap == 0 ? "" : "and chapter =" + chap));
            while (rs.next()) {
                int id = rs.getInt("id");
                int subjectid = rs.getInt("subjectid");
                String description = rs.getString("description");
                int chapter = rs.getInt("chapter");
                int lectureid = rs.getInt("lectureid");
                int status = rs.getInt("status");
                list.add(new Question(id, subjectid, description, chapter, lectureid, status));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        
        return list.subList(0, size);
    }

    public Vector<Question> getQuestion(int numOfQues, int subjectId, int lowerLimit, int upperLimit) {
        Vector<Question> questions = new Vector<>();
        startConnection();
        String sql = "SELECT TOP(" + numOfQues + ") [id]\n"
                + "[id]\n"
                + "      ,[subjectid]\n"
                + "      ,[description]\n"
                + "      ,[chapter]\n"
                + "      ,[lectureid]\n"
                + "      ,[status]\n"
                + "  FROM [dbo].[Question] WHERE  subjectid = " + subjectId + " and (chapter >= " + lowerLimit + " and chapter <= " + upperLimit + ")\n"
                + "  ORDER BY NEWID()";

        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Question question = new Question();
                question.setSubjectId(rs.getInt("subjectid"));
                question.setId(rs.getInt("id"));
                question.setDescription(rs.getString("description"));
                question.setChapter(rs.getInt("chapter"));
                question.setLectureid(rs.getInt("lectureid"));
                question.setStatus(rs.getInt("status"));
                questions.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return questions;
    }

    public int removeQuestion(int id) {
        startConnection();
        String sql = "UPDATE [dbo].[Question]\n"
                + "   SET \n"
                + "     [status] = 0\n"
                + " WHERE [id] = " + id;
        int n = 0;
        try ( Statement state = conn.createStatement();) {

            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return n;
    }

    public boolean isQuestionInUsed(int questionid) {
        startConnection();
        String sql = "SELECT td.id from Test_Detail td join Test t on td.testid = t.id\n"
                + "WHERE t.status = 1 AND td.questionid = ?";
        try ( PreparedStatement statement = prepareStatement(sql, questionid)) {
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            closeConnection();
        }
    }

    public List<Question> loadQuestion(int testid) {
        startConnection();
        List<Question> list = new ArrayList<>();
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery("SELECT [id]\n"
                    + "      ,[subjectid]\n"
                    + "      ,[description]\n"
                    + "      ,[chapter]\n"
                    + "      ,[lectureid]\n"
                    + "      ,[status]\n"
                    + "  FROM [dbo].[Question] WHERE id in (SELECT [questionid]\n"
                    + "  FROM [dbo].[Test_Detail] WHERE testid = " + testid + ")");
            while (rs.next()) {
                Question question = new Question();
                question.setChapter(rs.getInt("chapter"));
                question.setId(rs.getInt("id"));
                question.setSubjectId(rs.getInt("subjectid"));
                question.setDescription(rs.getString("description"));
                question.setLectureid(rs.getInt("lectureid"));
                question.setStatus(rs.getInt("status"));
                list.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return list;
    }
    
    public static void main(String[] args) {
        InformalDAO.getInstance().listAll(getInstance().loadQuestion(1));
    }
}
