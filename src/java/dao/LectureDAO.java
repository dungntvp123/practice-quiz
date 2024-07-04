package dao;

import entity.Lecture;
import entity.Student;
import entity.User;

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

public class LectureDAO extends DBConnect{
    
    private static LectureDAO instance = null;
    
    private LectureDAO() {
        
    }
    
    public static synchronized LectureDAO getInstance() {
        if (instance == null) {
            instance = new LectureDAO();
        }
        return instance;
    }
    public User getLectureUserFromId(int lectureID){
        
        startConnection();
        String sql = "SELECT u.* from [User] u join Lecture l on u.id = l.userid\n" +
                "WHERE l.id = ?";
//        System.out.println("ID = "+lectureID);
        try (PreparedStatement statement = prepareStatement(sql,lectureID)){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                User user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getTimestamp(7),
                        resultSet.getInt(8),
                        resultSet.getString(9)
                );
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }
    
    public int setLecture(int userid, int status) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Lecture]\n"
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
    
    public Lecture getLecture(int userid) {
        Lecture lecture = new Lecture();
        String sql = "SELECT [Lecture].id as 'lid', [Lecture].userid, username, [password], firstname, lastname, email, [time], [role], [status]\n"
                + "from [User] inner join [Lecture] on [User].id = [Lecture].userid and [User].id = " + userid;
        startConnection();
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                lecture.setId(rs.getInt("lid"));
                lecture.setUserid(rs.getInt("userid"));
                lecture.setUsername(rs.getString("username"));
                lecture.setPassword(Utility.decode(rs.getString("password")));
                lecture.setFirstname(rs.getString("firstname"));
                lecture.setLastname(rs.getString("lastname"));
                lecture.setEmail(rs.getString("email"));
                lecture.setCreateTime(Timestamp.valueOf(rs.getString("time")));
                lecture.setRole(rs.getInt("role"));
                lecture.setStatus(rs.getInt("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return lecture;
    }
    public Vector<Lecture> getAllLectures() {
        Vector<Lecture> Lecture = new Vector<>();
        startConnection();
        String sql = "SELECT Lecture.[id] as [sid]\n" +
                "                     ,[firstname]\n" +
                "                      ,[lastname]\n" +
                "                  FROM [dbo].[User] inner join  [dbo].Lecture on [User].id = [Lecture].userid\n";
        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Lecture lecture = new Lecture();
                lecture.setId(rs.getInt("sid"));
                lecture.setFirstname(rs.getString("firstname"));
                lecture.setLastname(rs.getString("lastname"));
                Lecture.add(lecture);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return Lecture;
    }
    public int updateLecture(int userid, int status) {
        int n = 0;
        startConnection();
        String sql = "UPDATE [dbo].[Lecture]\n"
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