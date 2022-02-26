package com.generation.model;

import com.generation.service.CourseService;

import java.util.*;

public class Student extends Person implements Evaluation {

    float PASS_MIN_GRADE = 3.0f;
    List<Course> enrolledCourses;
    CourseService courseService = new CourseService();
    float grade = 0.0f;
    // Use HashMap to store grade.
    Map <String, Float> gradeSystem;
    // ArrayList to store Courses greater than 3.0
    List<Course> passedEnrolledCourses = new ArrayList<>();


    public Student( String id, String name, String email, Date birthDate ) {
        super( id, name, email, birthDate );
        this.enrolledCourses = new ArrayList<>();
        this.gradeSystem = new HashMap<>();
    }

    public void enrollToCourse( Course course ) {
        this.enrolledCourses.add( course );
        this.gradeSystem.put( course.getCode(), grade );
    }

    @Override
    public List<Course> findPassedCourses() {
        // clear all the data of arrayList before use.
        passedEnrolledCourses.clear();
        // loop for HashMap
        for( String courseID : gradeSystem.keySet() ) {
            //  check the grade store in HashMap is greater than 3
            if(gradeSystem.get(courseID) >= PASS_MIN_GRADE){
                //  loop for ArrayList
                enrolledCourses.forEach(course -> {
                    //  get the information from Arraylist
                    if( course.getCode().equals( courseID ) ) {
                        //  Store to ArrayList that use for return.
                        passedEnrolledCourses.add( course );
                    }
                });
            }
        }
        return passedEnrolledCourses;
    }

    public Course findCourseById( String courseId ) {
        //TODO
        for( Course foundCourse : enrolledCourses ) {
            if( foundCourse.getCode().equals( courseId ) ) {
                return courseService.getCourse( courseId );
            }
        }
       return null;
    }

    public float displayGrade(String courseId){
        return gradeSystem.get(courseId);
    }

    public void editGrade(float grade,String courseId){
        gradeSystem.replace(courseId,grade);
    }

    @Override
    public List<Course> getEnrolledCourses() {
        return this.enrolledCourses;
    }

    @Override
    public String toString() {
        return "Student {" + super.toString() + "}";
    }

}
