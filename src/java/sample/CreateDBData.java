/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sample;

import dao.ClassDAO;
import dao.QuestionDAO;
import dao.RecorderDAO;
import dao.SelectionDAO;
import dao.StudentTestDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import dao.UserDAO;
import entity.Subject;
import entity.User;
import java.sql.Date;
import entity.Class;
import entity.Question;
import entity.Selection;
import entity.StudentClassDetail;
import entity.StudentTest;
import entity.StudentTestDetail;
import entity.Test;
import entity.TestDetail;
import java.sql.Timestamp;

/**
 *
 * @author Asus
 */
public class CreateDBData {

//    public static void main(String[] args) {
//
//        int n = 0;
//// insert User
//        n = UserDAO.getInstance().addUser(new User("khoinm", "abc", "Khoi", "Ngo", "khoinmhe176768@fpt.edu.vn", 0));
//        n = UserDAO.getInstance().addUser(new User("loveu1", "abc", "A", "Nguyen", "tuannmhe179004@fpt.edu.vn", 1));
//        n = UserDAO.getInstance().addUser(new User("anhcoczang1", "abc", "Dung", "Nguyen", "dungnthe176732@fpt.edu.vn", 2));
//        n = UserDAO.getInstance().addUser(new User("quanvippro", "abc", "Quan", "Tran", "quantmhe179013@fpt.edu.vn", 2));
//        n = UserDAO.getInstance().addUser(new User("saranghaeyo", "abc", "Duc", "Tran", "ductmhe173033@fpt.edu.vn", 1));
//// insert Subject
//        n = SubjectDAO.getInstance().addSubject(new Subject("SWP391", 12));
//        n = SubjectDAO.getInstance().addSubject(new Subject("SWT301", 12));
//        n = SubjectDAO.getInstance().addSubject(new Subject("SWR302", 12));
//        n = SubjectDAO.getInstance().addSubject(new Subject("PRN211", 12));
//        n = SubjectDAO.getInstance().addSubject(new Subject("ITE302c", 12));
//// insert Class
//        n = ClassDAO.getInstance().addClass(new Class("SWP391-SE1702.NET", 1, 1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + (long) 30 * 24 * 3600 * 1000), 1));
//        n = ClassDAO.getInstance().addClass(new Class("SWT301-SE1702.NET", 2, 1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + (long) 30 * 24 * 3600 * 1000), 1));
//        n = ClassDAO.getInstance().addClass(new Class("SWR302-SE1702.NET", 3, 2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + (long) 30 * 24 * 3600 * 1000), 1));
//        n = ClassDAO.getInstance().addClass(new Class("PRN211-SE1702.NET", 4, 2, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + (long) 30 * 24 * 3600 * 1000), 1));
//        n = ClassDAO.getInstance().addClass(new Class("ITE302c-SE1702.NET", 5, 1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + (long) 30 * 24 * 3600 * 1000), 1));
////insert Question
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Which of the following is not phase of the Fundamental Test Process?", 1, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Independent Verification & Validation is", 1, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Functional system testing is:", 1, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Validation involves which of the following  i. Helps to check the Quality of the Built Product  ii. Helps to check that we have built the right product.  iii. Helps in developing the product  niv. Monitoring tool wastage and obsoleteness.", 2, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Exhaustive Testing is", 2, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "If an expected result is not specified then:", 3, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "A Test Plan Outline contains which of the following:\ni. Test Items\nii. Test Scripts\niii. Test Deliverables\niv. Responsibilities", 3, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Verification involves which of the following:\ni. Helps to check that we have built product right.\nii. Helps to check that we have built the right product.\niii. Helps in developing the product\niv. Monitoring tool wastage and obsoleteness.", 3, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Which of the following is not a part of the Test Implementation and Execution Phase", 4, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Which of the following is not a major task of Exit criteria?", 4, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Testing where in we subject the target of the test, to varying workloads to measure a performance behaviors and ability of the target and of the test to continue to function different workloads.", 4, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "The Switch is switched off once the temperature falls below 18 and then it is turned on when the temperature is more than 21. When the temperature is more than 21. Identify the Equivalance values which belong to the same class.", 5, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "What is an equivalence partition (also known as an equivalence class)?", 5, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Which of the following is not a part of the Test Implementation and Execution Phase", 5, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Link Testing is also called as :", 6, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Who are the persons involved in a Formal Review :-\ni. Manager  ii. Moderator\niii. Scribe / Recorder\niv. Assistant Manager", 6, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Which of the following statements regarding static testing is false:", 7, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "Designing the test environment set-up and identifying any required infrastructure and tools are a part of which phase", 7, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "A Type of functional Testing, which investigates the functions relating to detection of threats, such as virus from malicious outsiders.", 7, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "A Person who documents all the issues, problems and open points that were identified during a formal review.", 7, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "The Test Cases Derived from use cases", 8, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(2, "One of the fields on a form contains a text box which accepts alpha numeric values. Identify the Valid Equivalence class", 8, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(3, "Walkthrough of requirements documents belong to which kind of peer review?", 1, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(3, "The distinguish users based on whether they worked in a large commercial bank, a small commercial bank, a savings and loan institution, or a credit union belong to which base to classifying users?", 1, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(3, "You need to be asking customers questions, listening to what they say, and watching what they do. That activities belong to which of the following process?", 2, 1, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(3, "You need to structure the customer input and derived requirements as written requirement statements and diagrams. That activities belong to which of the following process?", 3, 2, 1));
//        n = QuestionDAO.getInstance().addQuestion(new Question(3, "You need to process information to understand it, classify it in various categories, and relate the customer needs to possible software requirements. That activities belong to which of the following process?", 4, 1, 1));
//// insert Selection
//        n = SelectionDAO.getInstance().addSelection(new Selection(1, 0, "Test Planning and Control", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(1, 0, "Test implementation and Execution", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(1, 1, "Requirement Analysis", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(1, 0, "Evaluating Exit criteria and reporting", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(2, 0, "Done by the Developer", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(2, 0, "Done by the Test Engineers", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(2, 0, "Done By Management", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(2, 1, "Done by an Entity Outside the Project's sphere of influence", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(3, 0, "testing that the system functions with other systems", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(3, 0, "testing that the components that comprise the system", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(3, 1, "testing the end to end functionality of the system as a whole", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(3, 0, "testing the system performs functions within specified response times", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(4, 0, "Options i, ii, iii, iv are true", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(4, 1, "ii is true and i iii iv are false", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(4, 0, "iii iii are true and iv is false", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(4, 0, "iii is true and iii iv are false.", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(5, 0, "Is impractical but possible", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(5, 0, "Is practically possible", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(5, 1, "Is impractical and impossible", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(5, 0, "Is always possible", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(6, 0, "We cannot run the test", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(6, 0, "t may be difficult to repeat the test", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(6, 1, "It may be difficult to determine if the test has passed", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(6, 0, "We cannot automate the user inputs", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(7, 0, "ii, ii are true and iv is false", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(7, 1, "i,iii,iv are true and ii is false", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(7, 0, "ii, ii are true and i and iv are false", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(7, 0, "iii are false and iii, iv are true", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(8, 0, "Options i,ii,iii, iv are true.", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(8, 1, "i is true and ii iii iv are false", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(8, 0, "i,ii,iii are true and iv is false", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(8, 0, "ii is true and iii, iv are false.", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(9, 0, "Creating test suites from the test cases", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(9, 0, "Executing test cases either manually or by using test execution tools", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(9, 0, "Comparing actual results", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(9, 1, "Designing the Tests", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(10, 0, "Checking test logs against the exit criteria specified in test planning.", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(10, 1, "Checking test logs against the exit criteria specified in test planning.", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(10, 0, "Assessing if more tests are needed.", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(10, 0, "Writing a test summary report for stakeholders.", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(11, 1, "Load Testing", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(11, 0, "Integration Testing", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(11, 0, "System Testing", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(11, 0, "Usability Testing", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(12, 0, "12,16,22", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(12, 0, "24,27,17", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(12, 1, "22,23,24", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(12, 0, "22,23,24", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(13, 0, "A set of test cases for testing classes of objects", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(13, 1, "An input or output range of values such that only one value in the range becomes a test case", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(13, 0, "An input or output range of values such that each value in the range becomes a test case", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(13, 0, "An input or output range of values such that every tenth value in the range becomes a test case.", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(14, 0, "Creating test suites from the test cases", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(14, 0, "Executing test cases either manually or by using test execution tools", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(14, 0, "Comparing actual results", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(14, 1, "Designing the Tests", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(15, 1, "Component Integration testing", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(15, 0, "Component System Testing", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(15, 0, "Component Sub System Testing", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(15, 0, "Maintenance testing", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(16, 0, "i,ii,iii,iv are true", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(16, 1, "i,ii,iii are true and iv is false.", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(16, 0, "ii,iii,iv are true and i is false.", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(16, 0, "i,iv are true and ii, iii are false.", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(17, 1, "Static testing requires the running of tests through the code", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(17, 0, "Static testing includes desk checking", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(17, 0, "Static testing includes techniques such as reviews and inspections", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(17, 0, "Static testing can give measurements such as cyclomatic complexity", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(18, 0, "Test Implementation and execution", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(18, 1, "Test Analysis and Design", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(18, 0, "Evaluating the Exit Criteria and reporting", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(18, 0, "Test Closure Activities", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(19, 1, "Security Testing", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(19, 0, "Recovery Testing", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(19, 0, "Performance Testing", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(19, 0, "Functionality Testing", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(20, 0, "Moderator.", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(20, 1, "Scribe", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(20, 0, "Author", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(20, 0, "Manager", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(21, 1, "Are most useful in uncovering defects in the process flows during real world use of the system", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(21, 0, "Are most useful in uncovering defects in the process flows during the testing use of the system", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(21, 0, "Are most useful in covering the defects in the process flows during real world use of the system", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(21, 0, "Are most useful in covering the defects at the Integration Level", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(22, 0, "BOOK", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(22, 0, "Book", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(22, 1, "Boo01k", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(22, 0, "book", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(23, 0, "Formal review", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(23, 0, "Unformal review", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(23, 1, "Informal review", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(23, 0, "Bisformal review", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(24, 1, "different market segments.", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(24, 0, "different user classes.", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(24, 0, "Different level of income of users.", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(24, 0, "Different level of income of users.", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(25, 1, "Elicitation", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(25, 0, "Analysis", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(25, 0, "Specification", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(25, 0, "Validation", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(26, 0, "Elicitation", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(26, 0, "Analysis", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(26, 1, "Specification", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(26, 0, "Validation", "D"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(27, 0, "Elicitation", "A"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(27, 1, "Analysis", "B"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(27, 0, "Specification", "C"));
//        n = SelectionDAO.getInstance().addSelection(new Selection(27, 0, "Validation", "D"));
//// insert Student Class Detail
//        n = ClassDAO.getInstance().setStudentClassDetail(new StudentClassDetail(1, 1, 1));
//        n = ClassDAO.getInstance().setStudentClassDetail(new StudentClassDetail(1, 2, 1));
//        n = ClassDAO.getInstance().setStudentClassDetail(new StudentClassDetail(1, 3, 1));
//        n = ClassDAO.getInstance().setStudentClassDetail(new StudentClassDetail(1, 4, 1));
//        n = ClassDAO.getInstance().setStudentClassDetail(new StudentClassDetail(1, 5, 1));
//        n = ClassDAO.getInstance().setStudentClassDetail(new StudentClassDetail(2, 1, 0));
//        n = ClassDAO.getInstance().setStudentClassDetail(new StudentClassDetail(2, 2, 1));
//        n = ClassDAO.getInstance().setStudentClassDetail(new StudentClassDetail(2, 3, 1));
//        n = ClassDAO.getInstance().setStudentClassDetail(new StudentClassDetail(2, 4, 1));
//        n = ClassDAO.getInstance().setStudentClassDetail(new StudentClassDetail(2, 5, 0));
//// insert Test
//        n = TestDAO.getInstance().addTest(new Test("PT1", 3600, Timestamp.valueOf("2023-5-28 07:30:00.0"), Timestamp.valueOf("2023-5-28 09:50:00.0"), 10, 0.08, 2, 1, false));
//        n = TestDAO.getInstance().addTest(new Test("PT2", 3600, Timestamp.valueOf("2023-6-19 07:30:00.0"), Timestamp.valueOf("2023-6-19 12:30:00.0"), 10, 0.08, 2, 1, false));
//        n = TestDAO.getInstance().addTest(new Test("PT1", 3600, Timestamp.valueOf("2023-6-3 09:50:00.0"), Timestamp.valueOf("2023-6-3 12:20:00.0"), 10, 0.1, 3, 1, false));
//// insert Student Test
//        n = StudentTestDAO.getInstance().setStudentTest(new StudentTest(1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 2775 * 1000), 6));
//        n = StudentTestDAO.getInstance().setStudentTest(new StudentTest(2, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 2079 * 1000), 4));
//        n = StudentTestDAO.getInstance().setStudentTest(new StudentTest(1, 3, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3122 * 1000), 8));
//
//// insert Test Detail
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(1, 1, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(1, 3, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(1, 5, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(1, 8, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(1, 11, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(2, 12, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(2, 14, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(2, 15, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(2, 17, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(2, 20, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(3, 23, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(3, 24, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(3, 25, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(3, 26, 0.2));
//        n = TestDAO.getInstance().setTestDetail(new TestDetail(3, 27, 0.2));
//// insert student test detail
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(1, 1, "C", 2));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(1, 2, "B", 0));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(1, 3, "C", 2));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(1, 4, "B", 2));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(1, 5, "C", 0));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(2, 6, "C", 2));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(2, 7, "A", 0));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(2, 8, "A", 2));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(2, 9, "B", 0));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(2, 10, "C", 0));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(1, 11, "C", 2));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(1, 12, "C", 2));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(1, 13, "A", 2));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(1, 14, "C", 2));
//        n = StudentTestDAO.getInstance().setStudentTestDetail(new StudentTestDetail(1, 15, "C", 0));
//
//
//    }
public static void main(String[] args) {
    int n = 0;
    n = UserDAO.getInstance().addUser(new User("user1", "abc", "John", "Doe", "user1@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user2", "abc", "Jane", "Smith", "user2@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user3", "abc", "David", "Johnson", "user3@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user4", "abc", "Sarah", "Williams", "user4@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user5", "abc", "Michael", "Brown", "user5@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user6", "abc", "Jennifer", "Jones", "user6@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user7", "abc", "Christopher", "Davis", "user7@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user8", "abc", "Jessica", "Miller", "user8@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user9", "abc", "Matthew", "Wilson", "user9@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user10", "abc", "Amanda", "Anderson", "user10@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user11", "abc", "Daniel", "Taylor", "user11@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user12", "abc", "Lauren", "Martinez", "user12@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user13", "abc", "Robert", "Hernandez", "user13@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user14", "abc", "Olivia", "Lopez", "user14@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user15", "abc", "William", "Gonzalez", "user15@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user16", "abc", "Emily", "Clark", "user16@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user17", "abc", "Joseph", "Lee", "user17@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user18", "abc", "Sophia", "Perez", "user18@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user19", "abc", "David", "Hall", "user19@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user20", "abc", "Abigail", "Young", "user20@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user21", "abc", "Daniel", "Walker", "user21@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user22", "abc", "Samantha", "Harris", "user22@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user23", "abc", "Andrew", "Lewis", "user23@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user24", "abc", "Emma", "Turner", "user24@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user25", "abc", "Alexander", "Baker", "user25@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user26", "abc", "Grace", "Garcia", "user26@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user27", "abc", "Henry", "Wright", "user27@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user28", "abc", "Victoria", "Scott", "user28@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user29", "abc", "Christopher", "King", "user29@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user30", "abc", "Madison", "Phillips", "user30@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user31", "abc", "Andrew", "Roberts", "user31@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user32", "abc", "Lily", "Hill", "user32@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user33", "abc", "Benjamin", "Carter", "user33@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user34", "abc", "Sophia", "Turner", "user34@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user35", "abc", "Matthew", "Adams", "user35@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user36", "abc", "Charlotte", "Stewart", "user36@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user37", "abc", "James", "Mitchell", "user37@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user38", "abc", "Mia", "Kelly", "user38@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user39", "abc", "Daniel", "Cook", "user39@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user40", "abc", "Ava", "Murphy", "user40@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user41", "abc", "Jacob", "Bell", "user41@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user42", "abc", "Ella", "Bailey", "user42@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user43", "abc", "Alexander", "Howard", "user43@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user44", "abc", "Mia", "Foster", "user44@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user45", "abc", "William", "Russell", "user45@example.com", 2));
    n = UserDAO.getInstance().addUser(new User("user46", "abc", "Sophia", "Ward", "user46@example.com", 2));
}
}
