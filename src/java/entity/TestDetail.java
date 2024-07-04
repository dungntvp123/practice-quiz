/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author ACER NITRO
 */
public class TestDetail {
    private int id;
    private int testId, questionId;
    private double coefficient;

    public TestDetail() {
    }

    public TestDetail(int id, int testId, int questionId, double coefficient) {
        this.id = id;
        this.testId = testId;
        this.questionId = questionId;
        this.coefficient = coefficient;
    }

    public TestDetail(int testId, int questionId, double coefficient) {
        this.testId = testId;
        this.questionId = questionId;
        this.coefficient = coefficient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public String toString() {
        return "TestDetail{" + "id=" + id + ", testId=" + testId + ", questionId=" + questionId + ", coefficient=" + coefficient + '}';
    }
}
