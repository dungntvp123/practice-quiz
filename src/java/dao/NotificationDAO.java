package dao;

import entity.Notification;
import model.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class NotificationDAO extends DBConnect {
    private NotificationDAO(){}
    private static NotificationDAO instance = null;
    public static synchronized NotificationDAO getInstance(){
        if (instance == null){
            instance = new NotificationDAO();
        }
        return instance;
    }

    public Notification getNotification(int id){
        final String sql = "SELECT [id]\n" +
                "      ,[publisher]\n" +
                "      ,[class_id]\n" +
                "      ,[title]\n" +
                "      ,[content]\n" +
                "      ,[time_of_publish]\n" +
                "      ,[status]\n" +
                "  FROM [SWP211].[dbo].[Notification] WHERE id = ? AND status = 1 ORDER BY time_of_publish DESC";
        Notification notif = null;
        startConnection();
        try (PreparedStatement statement = prepareStatement(sql,id)){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                notif = parseResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return notif;
    }

    public Vector<Notification> getNotifications(){
        final String sql = "SELECT [id]\n" +
                "      ,[publisher]\n" +
                "      ,[class_id]\n" +
                "      ,[title]\n" +
                "      ,[content]\n" +
                "      ,[time_of_publish]\n" +
                "      ,[status]\n" +
                "  FROM [SWP211].[dbo].[Notification] ORDER BY time_of_publish DESC";
        Vector<Notification> vector = new Vector<>();
        startConnection();
        try (PreparedStatement statement = prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                vector.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return vector;
    }

    public Vector<Notification> getNotifications(int classID){
        final String sql = "SELECT [id]\n" +
                "      ,[publisher]\n" +
                "      ,[class_id]\n" +
                "      ,[title]\n" +
                "      ,[content]\n" +
                "      ,[time_of_publish]\n" +
                "      ,[status]\n" +
                "  FROM [SWP211].[dbo].[Notification] WHERE class_id = ? AND status = 1 ORDER BY time_of_publish DESC";
        Vector<Notification> vector = new Vector<>();
        startConnection();
        try (PreparedStatement statement = prepareStatement(sql,classID)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                vector.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return vector;
    }

    public Vector<Notification> getNotifications(int classID, int count){
        final String sql = "SELECT TOP (?) [id]\n" +
                "      ,[publisher]\n" +
                "      ,[class_id]\n" +
                "      ,[title]\n" +
                "      ,[content]\n" +
                "      ,[time_of_publish]\n" +
                "      ,[status]\n" +
                "  FROM [SWP211].[dbo].[Notification] WHERE class_id = ? AND status = 1 ORDER BY time_of_publish DESC";
        Vector<Notification> vector = new Vector<>();
        startConnection();
        try (PreparedStatement statement = prepareStatement(sql,count,classID)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                vector.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return vector;
    }

    public Vector<Notification> getNotificationsOfStudent(int userID){
        final String sql = "SELECT n.* from [Notification] n\n" +
                "WHERE class_id in (\n" +
                "SELECT scd.classid from Student_Class_Detail scd\n" +
                "join Student s on s.id = scd.studentid\n" +
                "join [User] u on u.id = s.userid\n" +
                "WHERE u.id = 1) AND n.status = 1 ORDER BY time_of_publish DESC";
        Vector<Notification> vector = new Vector<>();
        startConnection();
        try (PreparedStatement statement = prepareStatement(sql,userID)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                vector.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return vector;
    }
    public Vector<Notification> getNotificationsOfStudent(int userID, int count){
        final String sql = "SELECT TOP (?) n.* from [Notification] n\n" +
                "WHERE class_id in (\n" +
                "SELECT scd.classid from Student_Class_Detail scd\n" +
                "join Student s on s.id = scd.studentid\n" +
                "join [User] u on u.id = s.userid\n" +
                "WHERE u.id = ?) AND n.status = 1 ORDER BY time_of_publish DESC";
        Vector<Notification> vector = new Vector<>();
        startConnection();
        try (PreparedStatement statement = prepareStatement(sql,count,userID)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                vector.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return vector;
    }

    public void softRemoveNotification(int id){
        final String sql = "UPDATE [dbo].[Notification]\n" +
                "   SET [status] = 0\n" +
                " WHERE [id] = ?";
        startConnection();
        try (PreparedStatement statement = prepareStatement(sql,id)){
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
    }

    private Notification parseResultSet(ResultSet resultSet) throws SQLException{
        return new Notification(
                resultSet.getInt("id"),
                resultSet.getInt("publisher"),
                resultSet.getInt("class_id"),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getTimestamp("time_of_publish"),
                resultSet.getInt("status")
        );
    }

    public void addNotification(Notification n) {
        final String sql = "INSERT INTO [dbo].[Notification]\n" +
                "           ([publisher]\n" +
                "           ,[class_id]\n" +
                "           ,[title]\n" +
                "           ,[content]\n" +
                "           ,[time_of_publish]\n" +
                "           ,[status])\n" +
                "     VALUES\n" +
                "           (?,?,?,?,?,?)";
        startConnection();
        try (PreparedStatement statement = prepareStatement(sql,n.getPublisher(),n.getClassID(),n.getTitle(),n.getContent(),n.getTimeOfPublish(),n.getStatus())){
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
    }
}
