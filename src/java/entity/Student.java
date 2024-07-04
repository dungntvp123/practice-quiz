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
public class Student extends User implements UserExtension {
    private int id, userid, status;

    public Student() {
    }

    public Student(int id, int status, int userid, String username, String password, String firstname, String lastname, String email, Timestamp createTime, int role, String confirmkey) {
        super(userid, username, password, firstname, lastname, email, createTime, role, confirmkey);
        this.id = id;
        this.userid = userid;
        this.status = status;
    }
    public Student(int status, int userid, String username, String password, String firstname, String lastname, String email, Timestamp createTime, int role, String confirmkey) {
        super(userid, username, password, firstname, lastname, email, createTime, role, confirmkey);
        this.userid = userid;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", userid=" + userid + ", status=" + status + '}';
    }


    @Override
    public int getIDValue() {
        return getId();
    }
}
