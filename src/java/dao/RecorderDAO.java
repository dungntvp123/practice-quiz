/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Recorder;
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
public class RecorderDAO extends DBConnect {

    private static RecorderDAO instance = null;

    private RecorderDAO() {

    }

    public static synchronized RecorderDAO getInstance() {
        if (instance == null) {
            instance = new RecorderDAO();
        }
        return instance;
    }

    public Recorder newRecord(Recorder recoder) {
        startConnection();
        int id = 0;
        String sql = "INSERT INTO [dbo].[Change_Recorder]\n"
                + "           ([actor]\n"
                + "           ,[action]\n"
                + "           ,[action_area]\n"
                + "           ,[time])\n"
                + "OUTPUT inserted.id as id\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, recoder.getActor());
            pre.setString(2, recoder.getAction());
            pre.setInt(3, recoder.getActionArea());
            pre.setTimestamp(4, recoder.getDate());
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                recoder.setId(rs.getInt("id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return recoder;
    }

    public List<Recorder> getRecorder(String condition) {
        startConnection();
        List<Recorder> recoders = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[actor]\n"
                + "      ,[action]\n"
                + "      ,[action_area]\n"
                + "      ,[time]\n"
                + "  FROM [dbo].[Change_Recorder]" + (condition.equals("0") ? "" : "Where DATEDIFF(HOUR, time, GETDATE()) <= " + condition) ;
        
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Recorder recoder = new Recorder();
                recoder.setId(rs.getInt("id"));
                recoder.setActor(rs.getInt("actor"));
                recoder.setAction(rs.getString("action"));
                recoder.setActionArea(rs.getInt("action_area"));
                recoder.setDate(rs.getTimestamp("time"));
                recoders.add(recoder);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        
        return recoders;
    }
    
    public static void main(String[] args) {
        
    }
}
