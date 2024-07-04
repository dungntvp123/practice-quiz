package dao;

import entity.Test;
import entity.TestDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnect;

public class TestDAO extends DBConnect {

    private static TestDAO instance = null;

    private TestDAO() {

    }

    public static synchronized TestDAO getInstance() {
        if (instance == null) {
            instance = new TestDAO();
        }
        return instance;
    }

    public Test getTest(int testid) {
        Test test = new Test();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[duration]\n"
                + "      ,[starttime]\n"
                + "      ,[finishedtime]\n"
                + "      ,[numofques]\n"
                + "      ,[coefficient]\n"
                + "      ,[classid]\n"
                + "      ,[status]\n"
                + "      ,[allow_review]\n"
                + "  FROM [dbo].[Test] WHERE id = " + testid;
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                test.setId(testid);
                test.setName(rs.getString("name"));
                test.setDuration(rs.getLong("duration"));
                test.setStartTime(rs.getTimestamp("starttime"));
                test.setFinishedTime(rs.getTimestamp("finishedtime"));
                test.setNumOfQues(rs.getInt("numofques"));
                test.setCoefficient(rs.getDouble("coefficient"));
                test.setClassId(rs.getInt("classid"));
                test.setStatus(rs.getInt("status"));
                test.setAllowReview(rs.getBoolean("allow_review"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return test;
    }

    public Vector<Test> getAllTests() {
        Vector<Test> tests = new Vector<>();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[duration]\n"
                + "      ,[starttime]\n"
                + "      ,[finishedtime]\n"
                + "      ,[numofques]\n"
                + "      ,[coefficient]\n"
                + "      ,[classid]\n"
                + "      ,[status]\n"
                + "  FROM [dbo].[Test]";
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Test test = new Test();
                test.setId(rs.getInt("id"));
                test.setName(rs.getString("name"));
                test.setDuration(rs.getLong("duration"));
                test.setStartTime(rs.getTimestamp("starttime"));
                test.setFinishedTime(rs.getTimestamp("finishedtime"));
                test.setNumOfQues(rs.getInt("numofques"));
                test.setCoefficient(rs.getDouble("coefficient"));
                test.setClassId(rs.getInt("classid"));
                test.setStatus(rs.getInt("status"));
                tests.add(test);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return tests;
    }

    public Vector<Test> getTestOfClass(int classID) {
        Vector<Test> tests = new Vector<>();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[duration]\n"
                + "      ,[starttime]\n"
                + "      ,[finishedtime]\n"
                + "      ,[numofques]\n"
                + "      ,[coefficient]\n"
                + "      ,[classid]\n"
                + "      ,[status]\n"
                + "      ,[allow_review]\n"
                + "  FROM [SWP211].[dbo].[Test]\n"
                + "  WHERE classid = ?\n"
                + "  AND status = 1\n"
                + "  ORDER BY starttime DESC";
        try ( PreparedStatement statement = prepareStatement(sql, classID)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Test test = new Test(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getLong(3),
                        resultSet.getTimestamp(4),
                        resultSet.getTimestamp(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getBoolean(10)
                );
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return tests;
    }

    public int addTestDetail(TestDetail td) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Test_Detail]\n"
                + "           ([testid]\n"
                + "           ,[questionid]\n"
                + "           ,[coefficient])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        try ( PreparedStatement statement = prepareStatement(sql, td.getTestId(), td.getQuestionId(), td.getCoefficient())) {
            n = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

    public Vector<TestDetail> getTestDetails(int testID) {
        Vector<TestDetail> testDetails = new Vector<>();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[testid]\n"
                + "      ,[questionid]\n"
                + "      ,[coefficient]\n"
                + "  FROM [SWP211].[dbo].[Test_Detail] WHERE testid = ?";
        try ( PreparedStatement statement = prepareStatement(sql, testID)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                testDetails.add(new TestDetail(
                        resultSet.getInt("id"),
                        resultSet.getInt("testid"),
                        resultSet.getInt("questionid"),
                        resultSet.getInt("coefficient")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return testDetails;
    }

    public TestDetail getTestDetail(int testid, int questionid) {
        TestDetail testDetail = new TestDetail();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[testid]\n"
                + "      ,[questionid]\n"
                + "      ,[coefficient]\n"
                + "  FROM [dbo].[Test_Detail] WHERE testid = " + testid + " and questionid = " + questionid;
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                testDetail.setId(rs.getInt("id"));
                testDetail.setQuestionId(rs.getInt("questionid"));
                testDetail.setTestId(rs.getInt("testid"));
                testDetail.setCoefficient(rs.getDouble("coefficient"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return testDetail;
    }



    public Test addTestReturnID(Test test) {
        startConnection();
        String sql = "INSERT INTO [dbo].[Test]\n"
                + "           ([name]\n"
                + "      ,[duration]\n"
                + "      ,[starttime]\n"
                + "      ,[finishedtime]\n"
                + "      ,[numofques]\n"
                + "      ,[coefficient]\n"
                + "      ,[classid]\n"
                + "      ,[status]\n"
                + "      ,[allow_review])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try ( PreparedStatement pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pre.setString(1, test.getName());
            pre.setLong(2, test.getDuration());
            pre.setTimestamp(3, test.getStartTime());
            pre.setTimestamp(4, test.getFinishedTime());
            pre.setInt(5, test.getNumOfQues());
            pre.setDouble(6, test.getCoefficient());
            pre.setInt(7, test.getClassId());
            pre.setInt(8, test.getStatus());
            pre.setBoolean(9, test.getAllowReview());
            int n = pre.executeUpdate();
            if (n == 0) {
                throw new SQLException("No changes made.");
            }
            try ( ResultSet generatedKeys = pre.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    test.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("No ID obtained.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeConnection();
        }
        return test;
    }

    public int addTest(Test test) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Test]\n"
                + "           ([name]\n"
                + "      ,[duration]\n"
                + "      ,[starttime]\n"
                + "      ,[finishedtime]\n"
                + "      ,[numofques]\n"
                + "      ,[coefficient]\n"
                + "      ,[classid]\n"
                + "      ,[status]\n"
                + "      ,[allow_review])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try ( PreparedStatement pre = conn.prepareCall(sql)) {
            pre.setString(1, test.getName());
            pre.setLong(2, test.getDuration());
            pre.setTimestamp(3, test.getStartTime());
            pre.setTimestamp(4, test.getFinishedTime());
            pre.setInt(5, test.getNumOfQues());
            pre.setDouble(6, test.getCoefficient());
            pre.setInt(7, test.getClassId());
            pre.setInt(8, test.getStatus());
            pre.setBoolean(9, test.getAllowReview());
            n = pre.executeUpdate();
        } catch (SQLException ex) {

            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

    public int removeTest(int testId) {
        startConnection();
        String sql = "UPDATE Test\n"
                + "SET status = 0\n"
                + "WHERE id = ?";
        try ( PreparedStatement statement = prepareStatement(sql, testId)) {
            int n = statement.executeUpdate();
            return n;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public int setTestDetail(TestDetail testDetail) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Test_Detail]\n"
                + "           ([testid]\n"
                + "           ,[questionid]\n"
                + "           ,[coefficient])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";

        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, testDetail.getTestId());
            pre.setInt(2, testDetail.getQuestionId());
            pre.setDouble(3, testDetail.getCoefficient());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentTestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

}
