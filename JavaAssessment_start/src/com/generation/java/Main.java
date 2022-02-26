package com.generation.java;

import com.generation.model.Course;
import com.generation.model.Student;
import com.generation.service.CourseService;
import com.generation.service.StudentService;
import com.generation.utils.PrinterHelper;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main( String[] args ) throws ParseException {

        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        Scanner scanner = new Scanner( System.in );
        int option = 0;
        boolean displayMenu = true;

        // Loop for Invalid Input
        do {
            if(displayMenu) {
                // Menu Won't be display when invalid Enter.
                PrinterHelper.showMainMenu();
            }
            try {
                option = scanner.nextInt();
                //  Menu display
                displayMenu = true;
            } catch (InputMismatchException e) {
                //  to display the error message and repeat the loop
                option = 0;
                scanner.next();
            }
            switch ( option ) {
                case 1:
                    registerStudent( studentService, scanner );
                    break;
                case 2:
                    findStudent( studentService, scanner );
                    break;
                case 3:
                    gradeStudent( studentService, scanner );
                    break;
                case 4:
                    enrollCourse( studentService, courseService, scanner );
                    break;
                case 5:
                    showStudentsSummary( studentService, scanner );
                    break;
                case 6:
                    showCoursesSummary( courseService, scanner );
                    break;
                case 7:
                    showPassedCourses( studentService, scanner );
                    break;
                case 8:
                    break;
                default:
                    displayMenu = false;
                    System.out.println( "Invalid Input! Please Enter ( 1 - 8 )" );
            }

        }
        while ( option != 8 );
    }

    //  OPTION 1
    //  1.1 Main ( registerStudent )
    //  1.2 PrinterHelper ( createStudentMenu )
    //  1.3 StudentService ( subscribeStudent )
    private static void registerStudent( StudentService studentService, Scanner scanner ) throws ParseException {
        Student student = PrinterHelper.createStudentMenu( scanner );
        studentService.subscribeStudent( student );
    }

    //  OPTION 2
    //  2.1 Main ( findStudent )
    //  2.2 Main ( getStudentInformation )
    //  - 2.2.1 StudentService ( findStudent )
    //  2.3 StudentService ( displayEnrolledCourses )
    //  - 2.3.1 Student ( getEnrolledCourses )
    private static void findStudent( StudentService studentService, Scanner scanner ) {
        Student student = getStudentInformation( studentService, scanner );
        if ( student != null ) {
            System.out.println( "Student Found: " );
            System.out.println( student );
            studentService.displayEnrolledCourses( student );
        }
    }

    //  OPTION 3
    //  3.1 Main ( gradeStudent )
    //  3.2 Main ( getStudentInformation )
    //  - 3.2.1 StudentService ( findStudent )
    //  3.3 studentService ( displayEnrolledCourses )
    //  - 3.3.1 Student ( getEnrolledCourses )
    //  3.4 Student ( findCourseById )
    //  3.5 Student ( editGrade )
    private static void gradeStudent( StudentService studentService, Scanner scanner ) {
        float grade;
        boolean isValid = false;

        Student student = getStudentInformation( studentService, scanner );
        if( student == null ) {
            //  return to main menu if student not found.
            return;
        }
        studentService.displayEnrolledCourses( student );

        System.out.println( "Enrolled course:" );
        String courseId = scanner.next();
        Course course = student.findCourseById( courseId );
        if( course == null ) {
            System.out.println( "Course not found" );
            return;
        }

        do {
            try {
                System.out.println( "Insert course grade for : " + course.getName());
                grade = Float.parseFloat( scanner.next() );
                if( grade < 1 || grade > 6 ) {
                    System.out.println( "Please Enter a number ( 1 - 6 )" );
                } else {
                    student.editGrade( grade, courseId );
                    isValid = true;
                }
            } catch ( NumberFormatException e ) {
                System.out.println( "Please Enter a number" );
            }
        }while( !isValid );
    }

    //  OPTION 4
    //  4.1 Main ( enrollCourse )
    //  4.2 StudentService ( findStudent )
    //  4.3 CourseService ( getCourse )
    //  4.4 StudentService ( enrollToCourse )
    //  - 4.4.1 Student ( enrollToCourse )
    private static void enrollCourse( StudentService studentService, CourseService courseService, Scanner scanner ) {

        System.out.println( "Insert student ID" );
        String studentId = scanner.next();
        //  1. Find the Student Id
        Student student = studentService.findStudent( studentId );

        if ( student == null ) {
            // IF not found -> return to menu
            System.out.println( "Invalid Student ID" );
            return;
        }
        //  Student found
        System.out.println( student );
        System.out.println( "Insert course ID" );
        String courseId = scanner.next();
        // Search Course by ID
        Course course = courseService.getCourse( courseId );
        if ( course == null ) {
            // IF not found -> return to menu
            System.out.println( "Invalid Course ID" );
            return;
        }
        //  Course Found
        System.out.println( course );
        studentService.enrollToCourse( studentId, course );
        System.out.println( "Student with ID: " + studentId + " enrolled successfully to " + courseId );

    }

    //  OPTION 5
    //  5.1 Main ( showStudentsSummary )
    //  5.2 StudentService ( showSummary )
    //  - 5.2.1 StudentService ( displayEnrolledCourses )
    private static void showStudentsSummary( StudentService studentService, Scanner scanner ) {
        if ( !studentService.showSummary() )
        {
            System.out.println( "No Student Yet" );
        }
    }

    //  OPTION 6
    //  6.1 Main ( showCoursesSummary )
    //  6.2 CourseService ( showSummary )
    private static void showCoursesSummary( CourseService courseService, Scanner scanner ) {
        courseService.showSummary();
    }

    //  OPTION 7
    //  7.1 Main ( showPassedCourses )
    //  7.2 StudentService ( findStudent )
    //  7.3 Student ( findPassedCourses )
    private static void showPassedCourses(StudentService studentService, Scanner scanner ) {
        System.out.println( "Enter student ID: " );
        String studentId = scanner.next();
        Student student = studentService.findStudent( studentId );
        if ( student == null ) {
            System.out.println( "Student not found" );
        } else {
            if (student.findPassedCourses().size() == 0) {
                System.out.println( "No passed courses available" );
            } else {
                List<Course> passedCourses = student.findPassedCourses();
                passedCourses.forEach(System.out::println);
           }
        }
    }


    private static Student getStudentInformation( StudentService studentService, Scanner scanner ) {
        System.out.println( "Enter student ID: " );
        String studentId = scanner.next();
        Student student = studentService.findStudent( studentId );
        if ( student == null ) {
            System.out.println( "Student not found" );
        }
        return student;
    }

}
