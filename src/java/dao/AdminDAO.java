/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import entity.Admin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnect;
import utility.Utility;




/**
 *
 * @author Asus
 */
public class AdminDAO extends DBConnect {
    
    private static AdminDAO instance = null;
    
    private AdminDAO() {
        
    }
    
    public static synchronized AdminDAO getInstance() {
        if (instance == null) {
            instance = new AdminDAO();
        }
        return instance;
    }

    public int setAdmin(int userid) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[ADMIN]\n"
                + "           ([userid])\n"
                + "     VALUES\n"
                + "           (?)";
        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, userid);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return n;
    }
    
    public Admin getAdmin(int userid) {
        Admin admin = new Admin();
        startConnection();

        String sql = "SELECT [ADMIN].id as 'aid', [ADMIN].userid, username, [password], firstname, lastname, email, [time], [role]\n"
                + "from [User] inner join [ADMIN] on [User].id = [ADMIN].userid and [User].id = " + userid;

        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            if (rs.next()) {
                admin.setId(rs.getInt("aid"));
                admin.setUserid(rs.getInt("userid"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(Utility.decode(rs.getString("password")));
                admin.setFirstname(rs.getString("firstname"));
                admin.setLastname(rs.getString("lastname"));
                admin.setEmail(rs.getString("email"));
                admin.setCreateTime(Timestamp.valueOf(rs.getString("time")));
                admin.setRole(rs.getInt("role"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return admin;
    }
    
    
    public static void main(String[] args) {
        AdminDAO dao = new AdminDAO();
        System.out.println(dao.getAdmin(1));
    }
}
