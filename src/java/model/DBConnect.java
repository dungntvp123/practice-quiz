package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * DBConnect: Quản lý kết nối đến CSDL và các method dùng chung cho các DAO
 */

/**
 *
 * @author Asus
 */
public class DBConnect {
    public Connection conn;

//    public static void main(String[] args) {
//        DBConnect db = new DBConnect();
//
//    }
    public DBConnect() {

//        this("jdbc:sqlserver://localhost:1433;databaseName=SWP211", "sa", "123456");
    }
    
//    public DBConnect(String url, String userName, String pass) {
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            conn = DriverManager.getConnection(url, userName, pass);
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
    
    public void startConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=SWP211", "sa", "123456");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    public PreparedStatement prepareStatement(String sql, Object... args) throws SQLException {
        PreparedStatement pre = conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++){
            Object obj = args[i];
            pre.setObject(i+1,obj);
        }
        return pre;
    }
    
}
