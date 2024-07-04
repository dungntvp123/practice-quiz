/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;


import dao.LectureDAO;
import dao.StudentDAO;
import entity.Question;
import entity.Selection;

/**
 *
 * @author ACER NITRO
 */
public class TestClass {

    public static void main(String[] args) {
      int n = StudentDAO.getInstance().getStudent(0).getId();
        System.out.println(n);
    }
}
