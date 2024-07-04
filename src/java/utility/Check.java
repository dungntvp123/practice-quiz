package utility;

import entity.StudentClassDetail;

import java.util.Vector;

public class Check {
    public static boolean Check(Vector<StudentClassDetail> list, int id){
        for (StudentClassDetail std : list) {
            if (std.getStudentId() == id) {
                return true;
            }
        }
        return false;
    }
}
