package entity;

public class ExtendedQuestion extends Question{
    public int getTeacherID() {
        return lectureID;
    }

    public void setTeacherID(int lectureID) {
        this.lectureID = lectureID;
    }

    private int lectureID;

    public ExtendedQuestion(int lectureID, int subjectId, String description, int chapter, int teacherid, int status) {
        super(subjectId, description, chapter, teacherid, status);
        this.lectureID = lectureID;
    }

    
}
