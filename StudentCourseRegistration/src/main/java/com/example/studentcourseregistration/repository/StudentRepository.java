package com.example.studentcourseregistration.repository;

import com.example.studentcourseregistration.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
}