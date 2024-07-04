/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Asus
 */
public class StudentClassDetail {
    private int id;
    private int studentId, classId;  
    private int status;

    public StudentClassDetail() {
    }

    public StudentClassDetail(int id, int studentId, int classId, int status) {
        this.id = id;
        this.studentId = studentId;
        this.classId = classId;
        this.status = status;
    }

    public StudentClassDetail(int studentId, int classId, int status) {
        this.studentId = studentId;
        this.classId = classId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StudentClassDetail{" + "id=" + id + ", studentId=" + studentId + ", classId=" + classId + ", status=" + status + '}';
    }
    
    
}
