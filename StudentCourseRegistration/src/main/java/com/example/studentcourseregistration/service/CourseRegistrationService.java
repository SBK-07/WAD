package com.example.studentcourseregistration.service;

import com.example.studentcourseregistration.model.Course;
import com.example.studentcourseregistration.model.Registration;
import com.example.studentcourseregistration.repository.CourseRepository;
import com.example.studentcourseregistration.repository.RegistrationRepository;
import com.example.studentcourseregistration.repository.StudentRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Service
public class CourseRegistrationService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final RegistrationRepository registrationRepository;

    // ---------------------------------------------------------
    // a. Retrieve All Courses
    // ---------------------------------------------------------

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    // ---------------------------------------------------------
    // b. Register for a Course
    // ---------------------------------------------------------

    public Registration registerCourse(
            String studentId,
            String courseId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found: " + studentId);
        }

        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found: " + courseId);
        }

        if (registrationRepository
                .existsByStudentIdAndCourseId(studentId, courseId)) {

            throw new RuntimeException(
                    "Student is already registered for this course");
        }

        Registration registration = new Registration();

        registration.setRegistrationId(UUID.randomUUID().toString());
        registration.setStudentId(studentId);
        registration.setCourseId(courseId);

        // You can modify these according to your requirement
        registration.setSemester("Semester 1");
        registration.setAcademicYear("2026-2027");

        return registrationRepository.save(registration);
    }


    // ---------------------------------------------------------
    // c. Get Registered Courses for Student
    // ---------------------------------------------------------

    public List<Course> getRegisteredCourses(String studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found: " + studentId);
        }

        List<Registration> registrations =
                registrationRepository.findByStudentId(studentId);

        return registrations.stream()
                .map(registration ->
                        courseRepository
                                .findById(registration.getCourseId())
                                .orElse(null))
                .filter(course -> course != null)
                .toList();
    }


    // ---------------------------------------------------------
    // d. Remove Registered Course
    // ---------------------------------------------------------

    public void removeCourse(
            String studentId,
            String courseId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found: " + studentId);
        }

        if (!registrationRepository
                .existsByStudentIdAndCourseId(studentId, courseId)) {

            throw new RuntimeException(
                    "Registration not found");
        }

        registrationRepository
                .deleteByStudentIdAndCourseId(studentId, courseId);
    }


    // ---------------------------------------------------------
    // e. Most Popular Courses
    // ---------------------------------------------------------

    public List<Map<String, Object>> getPopularCourses() {
        return registrationRepository.findPopularCourses();
    }


    // ---------------------------------------------------------
    // f. Department Wise Registrations
    // ---------------------------------------------------------

    public List<Map<String, Object>> getDepartmentRegistrations() {
        return registrationRepository
                .findDepartmentWiseRegistrations();
    }


    // ---------------------------------------------------------
    // g. Students with More Than 2 Courses
    // ---------------------------------------------------------

    public List<Map<String, Object>> getMultipleCourseStudents() {
        return registrationRepository
                .findStudentsWithMoreThanTwoCourses();
    }

    // ---------------------------------------------------------
    // h. Total Credits Per Student
    // ---------------------------------------------------------

    public List<Map<String, Object>> getStudentCredits() {
        return registrationRepository.findStudentCredits();
    }
}