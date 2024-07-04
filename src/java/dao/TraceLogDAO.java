/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.TraceLog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.DBConnect;

/**
 *
 * @author Asus
 */
public class TraceLogDAO extends DBConnect {

    private static TraceLogDAO instance = null;

    private TraceLogDAO() {
    }

    public static TraceLogDAO getInstance() {
        if (instance == null) {
            instance = new TraceLogDAO();
        }
        return instance;
    }

    public TraceLog newLog(TraceLog log) {
        startConnection();
        String sql = "INSERT INTO [dbo].[Trace_Log]\n"
                + "           ([actor]\n"
                + "           ,[status]\n"
                + "           ,[last_log])\n"
                + "OUTPUT inserted.id as id\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, log.getActor());
            pre.setInt(2, log.getStatus());
            pre.setTimestamp(3, log.getLastLog());
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                log.setId(rs.getInt("id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }

        return log;
    }
    
    public TraceLog getTraceLog(int id) {
        startConnection();
        TraceLog log = new TraceLog();
        String sql = "SELECT [id]\n"
                + "      ,[actor]\n"
                + "      ,[status]\n"
                + "      ,[last_log]\n"
                + "  FROM [SWP211].[dbo].[Trace_Log] WHERE actor = " + id;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                log.setId(rs.getInt("id"));
                log.setActor(rs.getInt("actor"));
                log.setStatus(rs.getInt("status"));
                log.setLastLog(rs.getTimestamp("last_log"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return log;
    }

    public List<TraceLog> getTraceLogs(String condition) {
        startConnection();
        List<TraceLog> logs = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[actor]\n"
                + "      ,[status]\n"
                + "      ,[last_log]\n"
                + "  FROM [SWP211].[dbo].[Trace_Log]" + (condition.equals("-1") ? "" : "Where [status] = " + condition);
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                TraceLog log = new TraceLog();
                log.setId(rs.getInt("id"));
                log.setActor(rs.getInt("actor"));
                log.setStatus(rs.getInt("status"));
                log.setLastLog(rs.getTimestamp("last_log"));
                logs.add(log);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return logs;
    }

    public TraceLog updateStatus(TraceLog log) {
        startConnection();
        int n = 0;
        String sql = "UPDATE [dbo].[Trace_Log]\n"
                + "   SET [actor] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[last_log] = ?\n"
                + " WHERE id = " + log.getId();
        
        try (PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, log.getActor());
            pre.setInt(2, log.getStatus());
            pre.setTimestamp(3, log.getLastLog());
            n = pre.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        
        return log;
    }

    public static void main(String[] args) {
        
    }
}
