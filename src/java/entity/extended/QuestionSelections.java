package entity.extended;

import dao.QuestionDAO;
import entity.Question;
import entity.Selection;

import java.util.Vector;

public class QuestionSelections extends Question {
    private Vector<Selection> selections;
    public QuestionSelections(Question question){
        super(question.getId(), question.getSubjectId(),
                question.getDescription(), question.getChapter(),
                question.getLectureid(), question.getStatus());

        this.selections = QuestionDAO.getInstance().getSelectionsOfQuestion(this.getId());
    }


    public Vector<Selection> getSelections() {
        return selections;
    }

    public void setSelections(Vector<Selection> selections) {
        this.selections = selections;
    }
}
