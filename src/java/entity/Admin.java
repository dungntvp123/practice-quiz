/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import entity.interfaces.UserExtension;

import java.sql.Timestamp;

/**
 *
 * @author Asus
 */
public class Admin extends User implements UserExtension {
    private int id, userid;

    public Admin() {
    }

    public Admin(int id, int userid, String username, String password, String firstname, String lastname, String email, Timestamp createTime, int role, String confirmkey) {
        super(userid, username, password, firstname, lastname, email, createTime, role, confirmkey);
        this.id = id;
        this.userid = userid;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Admin{" + "id=" + id + ", userid=" + userid + '}';
    }


    @Override
    public int getIDValue() {
        return getId();
    }
}
