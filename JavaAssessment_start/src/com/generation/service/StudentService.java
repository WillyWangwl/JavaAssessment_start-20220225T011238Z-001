package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Student;

import java.util.*;

public class StudentService {
    private final Map<String, Student> students = new HashMap<>();
    private double grade;


    public void subscribeStudent( Student student )
    {
        students.put( student.getId(), student );
    }

    public Student findStudent( String studentId )
    {
        return students.get(studentId);
    }

    public boolean showSummary() {
        //TODO
        if( !students.isEmpty() ) {
            for ( String id : this.students.keySet() ) {
                Student display = this.students.get( id );
                System.out.println( display );
                displayEnrolledCourses ( display, id );
            }
            return true;
        }
        return false;
    }

    public void enrollToCourse( String studentId, Course course ) {
        Student student = students.get( studentId );
        student.enrollToCourse( course );
    }

    public void displayEnrolledCourses( Student student ) {
        List<Course> courses = student.getEnrolledCourses();
        if( !courses.isEmpty() ) {
            System.out.println( "Enrolled Course" );
            courses.forEach(course -> {
                System.out.println( course );
            });
        }
    }

    public void displayEnrolledCourses( Student student, String studentID ) {
        List<Course> courses = student.getEnrolledCourses();

        if( !courses.isEmpty() ) {
            System.out.println( "Enrolled Course" );
            courses.forEach( course -> {
                grade = student.displayGrade( course.getCode() );
                System.out.println( String.format ( course + " Grade : %.1f", grade ) );
            });
        }
    }

}
