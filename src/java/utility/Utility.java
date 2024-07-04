/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import dao.AdminDAO;
import dao.LectureDAO;
import dao.StudentDAO;
import entity.Selection;
import entity.StudentTestDetail;
import entity.TestDetail;
import entity.extended.QuestionSelections;

import java.security.SecureRandom;

import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 *
 * @author Asus
 */
public class Utility {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String generateRandCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String encode(String password) {
        String code = "";
        for (Character c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                code += c.toString().toLowerCase() + ((c < 'a') ? "" : "%");
            } else {
                code += c;
            }
        }
        return code;
    }

    public static String decode(String code) {
        String password = "";
        code += '.';
        for (int i = 0; i < code.length() - 1; ++i) {
            String reg = code.substring(i, i + 2);
            if (Pattern.compile("[a-z]%").matcher(reg).find()) {
                password += reg.charAt(0);
                ++i;
            } else {
                password += reg.toUpperCase().charAt(0);
            }
        }
        return password;
    }

    public static boolean isNumber(String candidate) {
        try {
            int n = Integer.parseInt(candidate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String timeToString(Long time, int unit) {
        return (unit == 0 ? "" : (time / (int) Math.pow(60, unit)) + (unit == 2 ? "h " : "m") + timeToString(time % ((int) Math.pow(60, unit)), unit - 1));
    }

    public static String timeToString2(Long time, int unit) {
        return (unit == 0 ? "" + String.format("%02d", time) : (time / (int) Math.pow(60, unit)) + ":" + timeToString2(time % ((int) Math.pow(60, unit)), unit - 1));
    }

    public static long timeToInt(String time) {
        long t = 0;
        int unit = 2;
        for (String i : time.split(":")) {
            t += Integer.parseInt(i) * Math.pow(60, unit);
            unit--;
        }
        return t;
    }

    public static double calculateCoefficent(String selected, List<Selection> selections) {
        if (selections.size() == 1) {
            if (selections.get(0).getCharId().equals(selected)) {
                return selections.get(0).getCoefficent();
            } else {
                return 0;
            }
        } else {
            //TODO Temporary patch
            if (selections.size() < selected.length()) {
                return 0;
            }
            double coef = 0;
            for (Selection selection : selections) {
                if (selected.contains(selection.getCharId())) {
                    coef += selection.getCoefficent();
                }
            }
            return (double) Math.round(coef * 1000) / 1000;
        }
    }

    protected static String monthInWord(Integer month) {
        String MIW = "";
        switch (month) {
            case 1:
                MIW = "Jan";
                break;
            case 2:
                MIW = "Feb";
                break;
            case 3:
                MIW = "Mar";
                break;
            case 4:
                MIW = "Apr";
                break;
            case 5:
                MIW = "May";
                break;
            case 6:
                MIW = "Jun";
                break;
            case 7:
                MIW = "Jul";
                break;
            case 8:
                MIW = "Aug";
                break;
            case 9:
                MIW = "Sep";
                break;
            case 10:
                MIW = "Nov";
                break;
            case 11:
                MIW = "Oct";
                break;
            case 12:
                MIW = "Dec";
                break;
        }
        return MIW;
    }

    public static String changDateFormat(Timestamp date) {
        String[] timeUnit = date.toString().split("-|\\s");
        return monthInWord(Integer.parseInt(timeUnit[1])) + " " + timeUnit[2] + ", " + timeUnit[0] + " " + timeUnit[3].substring(0, 8);
    }

    public static double calculateScore(Vector<TestDetail> questions) {
        double score = 0;
        for (TestDetail question : questions) {
            score += question.getCoefficient();
        }
        return score;
    }
    
    public static String numberRepresentTable(int representNumber) {
        String ret = null;
        switch (representNumber) {
            case 1:
                ret = "Subject";
                break;
            case 2:
                ret = "Question";
                break;
            case 3:
                ret = "Class";
                break;
            case 4:
                ret = "Test";
                break;
            case 5:
                ret = "Student Test";
                break;
            case 6:
                ret = "User";
                break;
            default:
                throw new AssertionError();
        }
        return ret;
    } 

    public static StudentTestDetail getTestDetailOfQuestion(List<StudentTestDetail> list, TestDetail testDetail) {
        for (StudentTestDetail studentTestDetail : list) {
            if (studentTestDetail.getTDid() == testDetail.getId()) {
                return studentTestDetail;
            }
        }
        return null;
    }
    
    public static String dateAgo(Timestamp time) {
        long diff = System.currentTimeMillis()- time.getTime();
        if (diff < 1000 * 60) return "few seconds ago"; 
        if (diff < 1000 * 60 * 60) return "" + diff / (1000 * 60) + "min ago";
        if (diff < 1000 * 60 * 60 * 24) return "" + diff / (1000 * 60 * 60) + "hour ago";
        return "" + diff / (1000 * 60 * 60 * 24) + "week ago";
    }

    public static boolean statusCheck(int id, int role) {
        if(role == 0) return true;
        int status = 0;
        if(role == 1){
            status = LectureDAO.getInstance().getLecture(id).getStatus();
        }
        if(role == 2){
            status = StudentDAO.getInstance().getStudent(id).getStatus();
        }
        return  status == 1;
    }
    
    public static int getUserStatus(int id, int role) {
        int status = 1;
        if(role == 1){
            status = LectureDAO.getInstance().getLecture(id).getStatus();
        }
        if(role == 2){
            status = StudentDAO.getInstance().getStudent(id).getStatus();
        }
        return  status;
    }
    
    public static int setUserPrevRole(int id,int role){
        int n = 0;
        if(role == 1){
            n = LectureDAO.getInstance().updateLecture(id, 0);
        }
        if(role == 2){
            n = StudentDAO.getInstance().updateStudent(id, 0);
        }
        return n;
    }
    
    public static void main(String[] args) {

        double a = 5.555556;
        System.out.println(Math.ceil(0.09009 * 100) / 100);

    }

    public static String reformat(QuestionSelections question) {
        StringBuilder s = new StringBuilder(question.getDescription() + "\n");
        StringBuilder corrects = new StringBuilder();
        for (Selection sel : question.getSelections()){
            s.append(sel.getCharId()).append(". ").append(sel.getDescription()).append("\n");
            if (sel.getCoefficent() > 0){
                corrects.append(sel.getCharId());
            }
        }
        s.append("Corrects: ").append(corrects);
        return s.toString();
    }
}
