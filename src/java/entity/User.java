/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;

/**
 *
 * @author Asus
 */
public class User {
    private int id;
    private String username, password, firstname, lastname, email;
    private Timestamp createTime; 
    int role;
    private String confirmkey;

    public static final int STUDENT = 2;
    public static final int TEACHER = 1;
    public static final int ADMINISTRATOR = 0;
    public User() {
    }

    public User(int id, String username, String password, String firstname, String lastname, String email, Timestamp createTime, int role, String confirmkey) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.createTime = createTime;
        this.role = role;
        this.confirmkey = confirmkey;
    }

    public User(String username, String password, String firstname, String lastname, String email, Timestamp createTime, int role, String confirmkey) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.createTime = createTime;
        this.role = role;
        this.confirmkey = confirmkey;
    }

    public User(String username, String password, String firstname, String lastname, String email, int role, String confirmkey) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.confirmkey = confirmkey;
    }

    public User(String username, String password, String firstname, String lastname, String email, int role) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
    }
    
    

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getConfirmkey() {
        return confirmkey;
    }

    public void setConfirmkey(String confirmkey) {
        this.confirmkey = confirmkey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", createTime=" + createTime + ", role=" + role + ", confirmkey=" + confirmkey + '}';
    }

    
    
}
