/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Asus
 */
public class Class {
    private int id;
    private String name;
    private int subjectId, lectureId;
    private Date startClass, finishedClass;
    int status;

    public Class() {
    }

    public Class(int id, String name, int subjectId, int lectureId, Date startClass, Date finishedClass, int status) {
        this.id = id;
        this.name = name;
        this.subjectId = subjectId;
        this.lectureId = lectureId;
        this.startClass = startClass;
        this.finishedClass = finishedClass;
        this.status = status;
    }

    public Class(String name, int subjectId, int lectureId, Date startClass, Date finishedClass, int status) {
        this.name = name;
        this.subjectId = subjectId;
        this.lectureId = lectureId;
        this.startClass = startClass;
        this.finishedClass = finishedClass;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public Date getStartClass() {
        return startClass;
    }

    public void setStartClass(Date startClass) {
        this.startClass = startClass;
    }

    public Date getFinishedClass() {
        return finishedClass;
    }

    public void setFinishedClass(Date finishedClass) {
        this.finishedClass = finishedClass;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Class{" + "id=" + id + ", name=" + name + ", subjectId=" + subjectId + ", expertId=" + lectureId + ", startClass=" + startClass + ", finishedClass=" + finishedClass + ", status=" + status + '}';
    }
    
    
}
