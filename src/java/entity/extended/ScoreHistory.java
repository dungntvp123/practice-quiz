package entity.extended;

import java.sql.Date;
import java.sql.Timestamp;

public class ScoreHistory {
    private int ID;
    private String testName;
    private String className;
    private String Subject;
    private java.sql.Timestamp startDate;
    private java.sql.Timestamp finishDate;
    private double score;
    private boolean canReview;

    protected ScoreHistory(int ID, String testName, String className, String subject,
                        java.sql.Timestamp startDate, java.sql.Timestamp finishDate, double score, boolean canReview) {
        this.ID = ID;
        this.testName = testName;
        this.className = className;
        Subject = subject;
        this.startDate = (Timestamp) startDate.clone();
        this.finishDate = (Timestamp) finishDate.clone();
        this.score = score;
        this.canReview = canReview;
    }

    public ScoreHistory(int ID, String testName, String className, String subject,
                        java.sql.Timestamp startDate, java.sql.Timestamp finishDate, double score){
        this(ID,testName,className,subject,startDate,finishDate,score,false);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Timestamp finishDate) {
        this.finishDate = finishDate;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }

    @Override
    public String toString() {
        return "ScoreHistory{" + "ID=" + ID + ", testName=" + testName + ", className=" + className + ", Subject=" + Subject + ", startDate=" + startDate + ", finishDate=" + finishDate + ", score=" + score + ", canReview=" + canReview + '}';
    }
    
    
}
