package com.example.studentcourseregistration.repository;

import com.example.studentcourseregistration.model.Registration;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Map;

public interface RegistrationRepository
        extends MongoRepository<Registration, String> {

    // =========================================================
    // Basic Registration Operations
    // =========================================================

    List<Registration> findByStudentId(String studentId);

    void deleteByStudentIdAndCourseId(
            String studentId,
            String courseId
    );

    boolean existsByStudentIdAndCourseId(
            String studentId,
            String courseId
    );


    // =========================================================
    // e. Most Popular Courses
    // =========================================================

    @Aggregation(pipeline = {
            "{ '$lookup': { " +
                    "'from': 'courses', " +
                    "'localField': 'courseId', " +
                    "'foreignField': '_id', " +
                    "'as': 'course' " +
                    "} }",

            "{ '$unwind': '$course' }",

            "{ '$group': { " +
                    "'_id': '$course.courseName', " +
                    "'totalRegistrations': { '$sum': 1 } " +
                    "} }",

            "{ '$project': { " +
                    "'_id': 0, " +
                    "'courseName': '$_id', " +
                    "'totalRegistrations': 1 " +
                    "} }",

            "{ '$sort': { 'totalRegistrations': -1 } }"
    })
    List<Map<String, Object>> findPopularCourses();


    // =========================================================
    // f. Department Wise Course Registrations
    // =========================================================

    @Aggregation(pipeline = {
            "{ '$lookup': { " +
                    "'from': 'students', " +
                    "'localField': 'studentId', " +
                    "'foreignField': '_id', " +
                    "'as': 'student' " +
                    "} }",

            "{ '$unwind': '$student' }",

            "{ '$group': { " +
                    "'_id': '$student.department', " +
                    "'students': { '$addToSet': '$studentId' }, " +
                    "'totalCoursesTaken': { '$sum': 1 } " +
                    "} }",

            "{ '$project': { " +
                    "'_id': 0, " +
                    "'department': '$_id', " +
                    "'totalStudents': { '$size': '$students' }, " +
                    "'totalCoursesTaken': 1 " +
                    "} }"
    })
    List<Map<String, Object>> findDepartmentWiseRegistrations();


    // =========================================================
    // g. Students Registered for More Than 2 Courses
    // =========================================================

    @Aggregation(pipeline = {
            "{ '$group': { " +
                    "'_id': '$studentId', " +
                    "'courseCount': { '$sum': 1 } " +
                    "} }",

            "{ '$match': { " +
                    "'courseCount': { '$gt': 2 } " +
                    "} }",

            "{ '$lookup': { " +
                    "'from': 'students', " +
                    "'localField': '_id', " +
                    "'foreignField': '_id', " +
                    "'as': 'student' " +
                    "} }",

            "{ '$unwind': '$student' }",

            "{ '$project': { " +
                    "'_id': 0, " +
                    "'studentName': '$student.studentName', " +
                    "'courseCount': 1 " +
                    "} }",

            "{ '$sort': { 'courseCount': -1 } }"
    })
    List<Map<String, Object>> findStudentsWithMoreThanTwoCourses();


    // =========================================================
    // h. Total Credits Registered Per Student
    // =========================================================

    @Aggregation(pipeline = {
            "{ '$lookup': { " +
                    "'from': 'courses', " +
                    "'localField': 'courseId', " +
                    "'foreignField': '_id', " +
                    "'as': 'course' " +
                    "} }",

            "{ '$unwind': '$course' }",

            "{ '$group': { " +
                    "'_id': '$studentId', " +
                    "'totalCredits': { '$sum': '$course.credits' } " +
                    "} }",

            "{ '$lookup': { " +
                    "'from': 'students', " +
                    "'localField': '_id', " +
                    "'foreignField': '_id', " +
                    "'as': 'student' " +
                    "} }",

            "{ '$unwind': '$student' }",

            "{ '$project': { " +
                    "'_id': 0, " +
                    "'studentName': '$student.studentName', " +
                    "'totalCredits': 1 " +
                    "} }",

            "{ '$sort': { 'totalCredits': -1 } }"
    })
    List<Map<String, Object>> findStudentCredits();
}