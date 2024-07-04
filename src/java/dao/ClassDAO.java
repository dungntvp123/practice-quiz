package dao;

import entity.*;

import java.sql.Date;

import entity.Class;
import utility.group.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnect;

public class ClassDAO extends DBConnect {
    
    private static ClassDAO instance = null;
    
    private ClassDAO() {
        
    }
    
    public static synchronized ClassDAO getInstance() {
        if (instance == null) {
            instance = new ClassDAO();
        }
        return instance;
    }
    public int addClass(entity.Class cls) {
        //add new class to database
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Class]\n"
                + "           ([name]\n"
                + "           ,[subjectid]\n"
                + "           ,[lectureid]\n"
                + "           ,[startclass]\n"
                + "           ,[finishedclass]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, cls.getName());
            pre.setInt(2, cls.getSubjectId());
            pre.setInt(3, cls.getLectureId());
            pre.setDate(4, cls.getStartClass());
            pre.setDate(5, cls.getFinishedClass());
            pre.setInt(6, cls.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }
    public Class addClassReturnID(Class cls) {
        startConnection();
        String sql = "INSERT INTO [dbo].[Class]\n"
                + "           ([name]\n"
                + "           ,[subjectid]\n"
                + "           ,[lectureid]\n"
                + "           ,[startclass]\n"
                + "           ,[finishedclass]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try ( PreparedStatement pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pre.setString(1, cls.getName());
            pre.setInt(2, cls.getSubjectId());
            pre.setInt(3, cls.getLectureId());
            pre.setDate(4, cls.getStartClass());
            pre.setDate(5, cls.getFinishedClass());
            pre.setInt(6, cls.getStatus());
            int n = pre.executeUpdate();
            if (n == 0) {
                throw new SQLException("No changes made.");
            }
            try ( ResultSet generatedKeys = pre.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cls.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("No ID obtained.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeConnection();
        }
        return cls;
    }

    public Vector<entity.Class> getClass(User user) {
        Vector<entity.Class> classes = new Vector<>();

        if (user.getRole() == 0) {
            return null;
        }
        startConnection();
        String sql = "";
        if (user.getRole() == 1) {
            sql = "SELECT [id]\n"
                    + "      ,[name]\n"
                    + "      ,[subjectid]\n"
                    + "      ,[expertid]\n"
                    + "      ,[startclass]\n"
                    + "      ,[finishedclass]\n"
                    + "      ,[status]\n"
                    + "  FROM [dbo].[Class] WHERE [expertid] = \n"
                    + "  (select [id] from Lecture where [userid] = " + user.getId() + ")";
        }

        if (user.getRole() == 2) {
            sql = "SELECT [id]\n"
                    + "      ,[name]\n"
                    + "      ,[subjectid]\n"
                    + "      ,[expertid]\n"
                    + "      ,[startclass]\n"
                    + "      ,[finishedclass]\n"
                    + "      ,[status]\n"
                    + "  FROM [dbo].[Class] WHERE [id] = \n"
                    + "  (select [classid] from Student_Class_Detail where studentid = \n"
                    + "  (select [id] from Student where userid = " + user.getId() + ")\n"
                    + "  )";
        }

        ResultSet rs;
        try ( Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            rs = state.executeQuery(sql);
            while (rs.next()) {
                entity.Class cls = new entity.Class();
                cls.setId(rs.getInt("id"));
                cls.setName(rs.getString("name"));
                cls.setSubjectId(rs.getInt("subjectid"));
                cls.setLectureId(rs.getInt("lectureid"));
                cls.setStartClass(Date.valueOf(rs.getString("startclass")));
                cls.setStartClass(Date.valueOf(rs.getString("finishedclass")));
                cls.setStatus(rs.getInt("status"));
                classes.add(cls);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return classes;
    }

    public Vector<Class> getClassOfSubject(int subjectID){
        Vector<Class> classes = new Vector<>();
        String sql = "SELECT * from Class WHERE subjectid = ? AND status = 1";
        startConnection();
        try ( PreparedStatement statement = prepareStatement(sql, subjectID)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entity.Class c = new entity.Class(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getInt(7)
                );
                classes.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return classes;
    }

    public Vector<Pair<entity.Class, String>> getClassOfStudent(User user) {
        Vector<Pair<entity.Class, String>> vector = new Vector<>();
        Student student = StudentDAO.getInstance().getStudent(user.getId());
        startConnection();
        String sql = "SELECT c.*, u.username from Class c \n"
                + "join Student_Class_Detail scd on c.id = scd.Classid\n"
                + "join Lecture l on l.id = c.lectureid\n"
                + "join [User] u on u.id = l.userid\n"
                + "WHERE scd.studentid = ?\n"
                + "AND c.status = 1";
        try ( PreparedStatement statement = prepareStatement(sql, student.getId())) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entity.Class c = new entity.Class(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getInt(7)
                );
                Pair<entity.Class, String> pair = new Pair<>(c, resultSet.getString(8));
                vector.add(pair);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return vector;
    }

    public Vector<Pair<entity.Class, String>> getClassOfStudent(User user, int subjectID) {
        Vector<Pair<entity.Class, String>> vector = new Vector<>();
        Student student = StudentDAO.getInstance().getStudent(user.getId());
        startConnection();
        String sql = "SELECT c.*, u.username from Class c \n"
                + "join Student_Class_Detail scd on c.id = scd.Classid\n"
                + "join Lecture l on l.id = c.lectureid\n"
                + "join [User] u on u.id = l.userid\n"
                + "WHERE scd.studentid = ?\n"
                + "AND c.status = 1 AND c.subjectid = ?";
        try ( PreparedStatement statement = prepareStatement(sql, student.getId(),subjectID)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entity.Class c = new entity.Class(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getInt(7)
                );
                Pair<entity.Class, String> pair = new Pair<>(c, resultSet.getString(8));
                vector.add(pair);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return vector;
    }

    public Vector<entity.Class> getClassOfLecture(User user) {
        Vector<entity.Class> vector = new Vector<>();
        Lecture lecture = LectureDAO.getInstance().getLecture(user.getId());
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[subjectid]\n"
                + "      ,[lectureid]\n"
                + "      ,[startclass]\n"
                + "      ,[finishedclass]\n"
                + "      ,[status]\n"
                + "  FROM [SWP211].[dbo].[Class]\n"
                + "   WHERE lectureid = ? AND status = 1";
        try ( PreparedStatement statement = prepareStatement(sql, lecture.getId())) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entity.Class c = new entity.Class(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getInt(7)
                );
                vector.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return vector;
    }

    public Vector<entity.Class> getClassOfLecture(User user, int subjectID) {
        Vector<entity.Class> vector = new Vector<>();
        Lecture lecture = LectureDAO.getInstance().getLecture(user.getId());
        startConnection();
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[subjectid]\n"
                + "      ,[lectureid]\n"
                + "      ,[startclass]\n"
                + "      ,[finishedclass]\n"
                + "      ,[status]\n"
                + "  FROM [SWP211].[dbo].[Class]\n"
                + "   WHERE lectureid = ? AND subjectid = ? AND status = 1";
        try ( PreparedStatement statement = prepareStatement(sql, lecture.getId(),subjectID)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entity.Class c = new entity.Class(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getInt(7)
                );
                vector.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return vector;
    }

    public entity.Class getClassFromId(int id) {
        startConnection();
        String sql = "SELECT TOP (1) [id]\n"
                + "      ,[name]\n"
                + "      ,[subjectid]\n"
                + "      ,[lectureid]\n"
                + "      ,[startclass]\n"
                + "      ,[finishedclass]\n"
                + "      ,[status]\n"
                + "  FROM [SWP211].[dbo].[Class]\n"
                + "  WHERE id = ?"
                + "  AND status = 1";
        try ( PreparedStatement statement = prepareStatement(sql, id)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new entity.Class(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getInt(7)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    public boolean isStudentUserInClass(int userId, int classId) {
        Student student = StudentDAO.getInstance().getStudent(userId);
        
        startConnection();
        String sql = "SELECT [id]\n"
                + "  FROM [SWP211].[dbo].[Student_Class_Detail]\n"
                + "  WHERE studentid = ? and classId = ?";
        try ( PreparedStatement statement = prepareStatement(sql, student.getId(), classId)) {
            try ( ResultSet rs = statement.executeQuery()) {
                System.out.println(student.getId());
                System.out.println(classId);
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public int setStudentClassDetail(StudentClassDetail scd) {
        int n = 0;
        startConnection();
        String sql = "INSERT INTO [dbo].[Student_Class_Detail]\n"
                + "           ([studentid]\n"
                + "           ,[Classid]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        
        try(PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, scd.getStudentId());
            pre.setInt(2, scd.getClassId());
            pre.setInt(3, scd.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentTestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return n;
    }

    public Vector<Class> getAllClass(){
        final String sql = "SELECT [id]\n" +
                "      ,[name]\n" +
                "      ,[subjectid]\n" +
                "      ,[lectureid]\n" +
                "      ,[startclass]\n" +
                "      ,[finishedclass]\n" +
                "      ,[status]\n" +
                "  FROM [dbo].[Class]";
        Vector<Class> classes = new Vector<>();
        startConnection();
        try(PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                classes.add(new entity.Class(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getInt(7)
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentTestDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return classes;
    }
    public Vector<StudentClassDetail> getAllStudentByClassID(String id) {
        Vector<StudentClassDetail> studentsInClass = new Vector<>();

        String sql = "SELECT * FROM Student_Class_Detail WHERE Classid = ?";
        startConnection();
        try (PreparedStatement statement = prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StudentClassDetail student = new StudentClassDetail(
                        resultSet.getInt("id"),
                        resultSet.getInt("studentid"),
                        resultSet.getInt("Classid"),
                        resultSet.getInt("status")
                );
                studentsInClass.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return studentsInClass;
    }

public Class getClassDetailByID(String id) {
    String sql = "select*from class as a join Subject as b on a.subjectid=b.id where a.id= ?";
//        System.out.println("ID = "+lectureID);
    startConnection();
    try (PreparedStatement statement = prepareStatement(sql, id)) {
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Class ClassDetail = new Class(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getDate(5),
                    resultSet.getDate(6),
                    resultSet.getInt(7)
            );
            return ClassDetail;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    return null;
}
    public int updateClassByClassID(int classID, String name, int subjectID, int lectureID, Date startClass, Date finishedClass, int status) {
        startConnection();
        int rowsAffected = 0;
        String sql = "UPDATE [Class]\n"
                + "   SET [name] = '" + name + "',"
                + "[subjectid] = " + subjectID + ","
                + "[lectureid] = " + lectureID + ","
                + "[startclass] = '" + startClass.toString() + "',"
                + "[finishedclass] = '" + finishedClass.toString() + "',"
                + "[status] = " + status
                + " WHERE [Class].[id] = " + classID;
        try (Statement statement = conn.createStatement()) {
            rowsAffected = statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return rowsAffected;
    }

    public int updateStudentStatus(int classID, int studentID, int status) {
        startConnection();
        int rowsAffected = 0;
        String sql = "UPDATE [Student_Class_Detail]\n"
                + "   SET [status] = " + status
                + " WHERE [Classid] = " + classID + " AND [studentid] = " + studentID;
        try (Statement statement = conn.createStatement()) {
            rowsAffected = statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(InformalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return rowsAffected;
    }
    public int RemoveAllStudentInClassByClassID(int id){
        startConnection();
        int rowsAffected = 0;


        String sql= "Delete from  [Student_Class_Detail]  WHERE [Classid] = " + id ;
        try (Statement statement = conn.createStatement()) {
            rowsAffected = statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return rowsAffected;
    }


    public static void main(String[] args) {
//        int m = getInstance().updateStudentStatus(3,1,1);
//
//        System.out.println(m);

        int m = ClassDAO.getInstance().RemoveAllStudentInClassByClassID(2);
        System.out.println(m);
    }

}
