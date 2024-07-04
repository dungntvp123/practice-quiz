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
public class StudentTest {
    private int id; 
    private int studentId, testId;
    private Timestamp startTime, finishedTime;
    private double totalScore;

    public StudentTest() {
    }

    public StudentTest(int id, int studentId, int testId, Timestamp startTime, Timestamp finishedTime, double totalScore) {
        this.id = id;
        this.studentId = studentId;
        this.testId = testId;
        this.startTime = startTime;
        this.finishedTime = finishedTime;
        this.totalScore = totalScore;
    }

    public StudentTest(int studentId, int testId, Timestamp startTime, Timestamp finishedTime, double totalScore) {
        this.studentId = studentId;
        this.testId = testId;
        this.startTime = startTime;
        this.finishedTime = finishedTime;
        this.totalScore = totalScore;
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

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Timestamp finishedTime) {
        this.finishedTime = finishedTime;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }


    @Override
    public String toString() {
        return "StudentTest{" + "id=" + id + ", studentId=" + studentId + ", testId=" + testId + ", startTime=" + startTime + ", finishedTime=" + finishedTime + ", totalScore=" + totalScore + '}';
    }
    
    
}
