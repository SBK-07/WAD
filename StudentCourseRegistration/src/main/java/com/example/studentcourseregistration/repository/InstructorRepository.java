package com.example.studentcourseregistration.repository;

import com.example.studentcourseregistration.model.Instructor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InstructorRepository
        extends MongoRepository<Instructor, String> {
}