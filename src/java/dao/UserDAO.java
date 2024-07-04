/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.TraceLog;
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

/**
 *
 * @author Asus
 */
public class UserDAO extends DBConnect {

    private static UserDAO instance = null;

    private UserDAO() {

    }

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public int addUser(User user) {
        int n = setUser(user);
        user = getUser(user.getEmail());
        int role = 0;
        TraceLogDAO.getInstance().newLog(new TraceLog(user.getId(), 0, new Timestamp(System.currentTimeMillis())));
        switch (user.getRole()) {
            case 0:
                role = AdminDAO.getInstance().setAdmin(user.getId());
                break;
            case 1:
                role = LectureDAO.getInstance().setLecture(user.getId(), 1);
                break;
            case 2:
                role = StudentDAO.getInstance().setStudent(user.getId(), 1);
                break;
        }
        return (n > 0 && role > 0 ? 1 : 0);
    }

    public int addUserThrow(User user) throws SQLException {
        int n = setUserThrow(user);
        user = getUser(user.getEmail());
        int role = 0;
        TraceLogDAO.getInstance().newLog(new TraceLog(user.getId(), 0, new Timestamp(System.currentTimeMillis())));
        switch (user.getRole()) {
            case 0:
                role = AdminDAO.getInstance().setAdmin(user.getId());
                break;
            case 1:
                role = LectureDAO.getInstance().setLecture(user.getId(), 1);
                break;
            case 2:
                role = StudentDAO.getInstance().setStudent(user.getId(), 1);
                break;
        }
        return (n > 0 && role > 0 ? 1 : 0);
    }

    public int setUserNoClose(User user) throws SQLException {
        //set new user to database
        int n = 0;
        String sql = "INSERT INTO [dbo].[User]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[firstname]\n"
                + "           ,[lastname]\n"
                + "           ,[email]\n"
                + "           ,[time]\n"
                + "           ,[role]\n"
                + "           ,[confirmkey])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getUsername());
            pre.setString(2, Utility.encode(user.getPassword()));
            pre.setString(3, user.getFirstname());
            pre.setString(4, user.getLastname());
            pre.setString(5, user.getEmail());
            pre.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pre.setInt(7, user.getRole());
            pre.setString(8, Utility.generateRandCode());
            n = pre.executeUpdate();
        return n;
    }

    public int setUser(User user) {
        //set new user to database
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[User]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[firstname]\n"
                + "           ,[lastname]\n"
                + "           ,[email]\n"
                + "           ,[time]\n"
                + "           ,[role]\n"
                + "           ,[confirmkey])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getUsername());
            pre.setString(2, Utility.encode(user.getPassword()));
            pre.setString(3, user.getFirstname());
            pre.setString(4, user.getLastname());
            pre.setString(5, user.getEmail());
            pre.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pre.setInt(7, user.getRole());
            pre.setString(8, Utility.generateRandCode());

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

    public int setUserThrow(User user) throws SQLException {
        //set new user to database
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[User]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[firstname]\n"
                + "           ,[lastname]\n"
                + "           ,[email]\n"
                + "           ,[time]\n"
                + "           ,[role]\n"
                + "           ,[confirmkey])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getUsername());
            pre.setString(2, Utility.encode(user.getPassword()));
            pre.setString(3, user.getFirstname());
            pre.setString(4, user.getLastname());
            pre.setString(5, user.getEmail());
            pre.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pre.setInt(7, user.getRole());
            pre.setString(8, Utility.generateRandCode());

            n = pre.executeUpdate();
        } catch (SQLException ex) {
            closeConnection();
            throw ex;
        }
        return n;
    }


    public User getUser(int id) {
        final String sql = "SELECT [id]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[firstname]\n"
                + "      ,[lastname]\n"
                + "      ,[email]\n"
                + "      ,[time]\n"
                + "      ,[role]\n"
                + "      ,[confirmkey]\n"
                + "  FROM [SWP211].[dbo].[User]\n"
                + "  WHERE id = ?";
        startConnection();
        try ( PreparedStatement statement = prepareStatement(sql, id)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
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
    
    public User getUserByUsername(String username) {
        final String sql = "SELECT [id]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[firstname]\n"
                + "      ,[lastname]\n"
                + "      ,[email]\n"
                + "      ,[time]\n"
                + "      ,[role]\n"
                + "      ,[confirmkey]\n"
                + "  FROM [SWP211].[dbo].[User]\n"
                + "  WHERE username = ?";
        startConnection();
        try ( PreparedStatement statement = prepareStatement(sql,username)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
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
    
    public User getUser(String... args) {
        User user = new User();
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[firstname]\n"
                + "      ,[lastname]\n"
                + "      ,[email]\n"
                + "      ,[time]\n"
                + "      ,[role]\n"
                + "      ,[confirmkey]\n"
                + "  FROM [dbo].[User] WHERE " + (args.length == 2
                        ? "[username] = '" + args[0] + "' and [password] = '" + Utility.encode(args[1]) + "'"
                        : (Utility.isNumber(args[0]) ? "id = " + args[0] : "[email] = '" + args[0] + "'"));

        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(Utility.decode(rs.getString("password")));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setCreateTime(Timestamp.valueOf(rs.getString("time")));
                user.setRole(rs.getInt("role"));
                user.setConfirmkey(rs.getString("confirmkey"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return user;
    }

    public User getUserNoClose(String... args) throws SQLException {
        User user = new User();
        String sql = "SELECT [id]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[firstname]\n"
                + "      ,[lastname]\n"
                + "      ,[email]\n"
                + "      ,[time]\n"
                + "      ,[role]\n"
                + "      ,[confirmkey]\n"
                + "  FROM [dbo].[User] WHERE " + (args.length == 2
                ? "[username] = '" + args[0] + "' and [password] = '" + Utility.encode(args[1]) + "'"
                : (Utility.isNumber(args[0]) ? "id = " + args[0] : "[email] = '" + args[0] + "'"));

        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(Utility.decode(rs.getString("password")));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setCreateTime(Timestamp.valueOf(rs.getString("time")));
                user.setRole(rs.getInt("role"));
                user.setConfirmkey(rs.getString("confirmkey"));
            }
        }

        return user;
    }

    public User getLectureUserFromId(int lectureID) {
        String sql = "SELECT u.* from [User] u join Lecture l on u.id = l.userid\n"
                + "WHERE l.id = ?";
//        System.out.println("ID = "+lectureID);
        startConnection();
        try ( PreparedStatement statement = prepareStatement(sql, lectureID)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
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

    public User getStudentUserFromId(int studentID) {
        String sql = "SELECT u.* from [User] u join Student l on u.id = l.userid\n"
                + "WHERE l.id = ?";
//        System.out.println("ID = "+lectureID);
        startConnection();
        try ( PreparedStatement statement = prepareStatement(sql, studentID)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
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

    public Vector<User> getAllUsers() {
        Vector<User> users = new Vector<>();
        String sql = "SELECT * from [User]";
//        System.out.println("ID = "+lectureID);
        startConnection();
        try ( PreparedStatement statement = prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return users;
    }

    public int changePasswordForLoggedUser(String username, String newPassword) {

        startConnection();
        int n = 0;
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET [password] = '" + Utility.encode(newPassword) + "' WHERE [User].username = '" + username + "'";
        try ( Statement state = conn.createStatement()) {
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return n;
    }

    public int changeUserProfile(String firstname, String lastname, String username) {
        startConnection();
        int n = 0;
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET [firstname] = '" + firstname + "',[lastname] ='" + lastname + "' WHERE [User].username = '" + username + "'";
        try ( Statement state = conn.createStatement()) {
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return n;
    }

    public User getUserByID(String IdtoChange) {

        String sql = "SELECT * from [User] where [User].id= ?";
//        System.out.println("ID = "+lectureID);
        startConnection();
        try ( PreparedStatement statement = prepareStatement(sql, IdtoChange)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
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

    public int updateUser(User user,int id) {
        startConnection();
        int n = 0;
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET [username] = ?\n"
                + "      ,[password] = ?\n"
                + "      ,[firstname] = ?\n"
                + "      ,[lastname] =  ?\n"
                + "      ,[email] = ?\n"
                + "      ,[role] = ?\n"
                + " WHERE [id] = ?";
        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, user.getUsername());
            pre.setString(2, Utility.encode(user.getPassword()));
            pre.setString(3, user.getFirstname());
            pre.setString(4, user.getLastname());
            pre.setString(5, user.getEmail());  
            pre.setInt(6, user.getRole());
            pre.setInt(7, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return n;
    }

}
