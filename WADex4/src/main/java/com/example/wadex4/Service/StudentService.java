package com.example.wadex4.Service;

import com.example.wadex4.Model.Student;
import com.example.wadex4.Repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // CREATE
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // READ - Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // READ - Get student by ID
    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    // UPDATE
    public Optional<Student> updateStudent(
            String id,
            Student updatedStudent) {

        return studentRepository.findById(id)
                .map(existingStudent -> {

                    existingStudent.setName(
                            updatedStudent.getName());

                    existingStudent.setEmail(
                            updatedStudent.getEmail());

                    existingStudent.setDepartment(
                            updatedStudent.getDepartment());

                    existingStudent.setAge(
                            updatedStudent.getAge());

                    return studentRepository.save(existingStudent);
                });
    }

    // DELETE
    public boolean deleteStudent(String id) {

        if (studentRepository.existsById(id)) {

            studentRepository.deleteById(id);

            return true;
        }

        return false;
    }
}