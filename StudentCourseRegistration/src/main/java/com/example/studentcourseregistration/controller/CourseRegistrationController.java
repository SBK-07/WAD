package com.example.studentcourseregistration.controller;

import com.example.studentcourseregistration.model.Course;
import com.example.studentcourseregistration.model.Registration;
import com.example.studentcourseregistration.service.CourseRegistrationService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.studentcourseregistration.dto.RegistrationRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CourseRegistrationController {

    private final CourseRegistrationService service;


    // ---------------------------------------------------------
    // a. Retrieve All Courses
    // GET /api/courses
    // ---------------------------------------------------------

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {

        return ResponseEntity.ok(
                service.getAllCourses()
        );
    }


    // ---------------------------------------------------------
    // b. Register for a Course
    // POST /api/register
    // ---------------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity<?> registerCourse(
            @RequestBody RegistrationRequest request) {

        try {
            Registration registration =
                    service.registerCourse(
                            request.getStudentId(),
                            request.getCourseId()
                    );

            return ResponseEntity.ok(registration);

        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.badRequest()
                    .body(response);
        }
    }


    // ---------------------------------------------------------
    // c. Get Registered Courses
    // GET /api/students/{studentId}/courses
    // ---------------------------------------------------------

    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<?> getRegisteredCourses(
            @PathVariable String studentId) {

        try {

            List<Course> courses =
                    service.getRegisteredCourses(studentId);

            return ResponseEntity.ok(courses);

        } catch (RuntimeException e) {

            Map<String, String> response = new HashMap<>();

            response.put("error", e.getMessage());

            return ResponseEntity.badRequest()
                    .body(response);
        }
    }


    // ---------------------------------------------------------
    // d. Remove Registered Course
    // DELETE /api/students/{studentId}/courses/{courseId}
    // ---------------------------------------------------------

    @DeleteMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<?> removeCourse(
            @PathVariable String studentId,
            @PathVariable String courseId) {

        try {

            service.removeCourse(
                    studentId,
                    courseId
            );

            Map<String, String> response = new HashMap<>();

            response.put(
                    "message",
                    "Course removed successfully"
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            Map<String, String> response = new HashMap<>();

            response.put("error", e.getMessage());

            return ResponseEntity.badRequest()
                    .body(response);
        }
    }


    // ---------------------------------------------------------
    // e. Most Popular Courses
    // GET /api/analytics/popular-courses
    // ---------------------------------------------------------

    @GetMapping("/analytics/popular-courses")
    public ResponseEntity<List<Map<String, Object>>>
    getPopularCourses() {

        return ResponseEntity.ok(
                service.getPopularCourses()
        );
    }


    // ---------------------------------------------------------
    // f. Department Wise Registrations
    // GET /api/analytics/department-registrations
    // ---------------------------------------------------------

    @GetMapping("/analytics/department-registrations")
    public ResponseEntity<List<Map<String, Object>>>
    getDepartmentRegistrations() {

        return ResponseEntity.ok(
                service.getDepartmentRegistrations()
        );
    }


    // ---------------------------------------------------------
    // g. Students Registered for More Than 2 Courses
    // GET /api/analytics/multiple-courses
    // ---------------------------------------------------------

    @GetMapping("/analytics/multiple-courses")
    public ResponseEntity<List<Map<String, Object>>>
    getMultipleCourseStudents() {

        return ResponseEntity.ok(
                service.getMultipleCourseStudents()
        );
    }


    // ---------------------------------------------------------
    // h. Total Credits Per Student
    // GET /api/analytics/student-credits
    // ---------------------------------------------------------

    @GetMapping("/analytics/student-credits")
    public ResponseEntity<List<Map<String, Object>>>
    getStudentCredits() {

        return ResponseEntity.ok(
                service.getStudentCredits()
        );
    }
}