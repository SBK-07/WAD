package com.example.wadex4.Repository;

import com.example.wadex4.Model.Student;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface StudentRepository
        extends MongoRepository<Student, String> {

}