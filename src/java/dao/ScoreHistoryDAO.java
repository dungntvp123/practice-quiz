package dao;

import entity.extended.ScoreHistory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.DBConnect;

public class ScoreHistoryDAO extends DBConnect{
    
    private static ScoreHistoryDAO instance = null;
    
    private ScoreHistoryDAO() {
        
    }
    
    public static synchronized ScoreHistoryDAO getInstance() {
        if (instance == null) {
            instance = new ScoreHistoryDAO();
        }
        return instance;
    }
    
    public Vector<ScoreHistory> getScoreHistoryOfStudent(int user_id){
        startConnection();
        Vector<ScoreHistory> scoreHistories = new Vector<>();
        final String SQL = "SELECT st.id as [ID],t.name as [Test Name], c.name as [Class], s.name as [Subject], \n" +
                "st.starttime as [Start], st.finishedtime as [Finished], st.totalscore as [Score], t.finishedtime as [TFT], \n" +
                "t.duration as [Duration]\n" +
                "from Student_Test st \n" +
                "join Test t on t.id = st.Testid \n" +
                "join Class c on c.id = t.classid \n" +
                "join Subject s on s.id = c.subjectid\n" +
                "join Student stu on stu.id = st.studentid\n" +
                "WHERE stu.userid = ?";
        try (PreparedStatement statement = prepareStatement(SQL,user_id)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ScoreHistory history = new ScoreHistory(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5),
                        resultSet.getTimestamp(6),
                        resultSet.getDouble(7)
                );
                scoreHistories.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return scoreHistories;
    }
    
    public static void main(String[] args) {
        InformalDAO.getInstance().listAll(getInstance().getScoreHistoryOfStudent(3));
    }
}