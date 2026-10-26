package com.example.studentcourseregistration.repository;

import com.example.studentcourseregistration.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
}