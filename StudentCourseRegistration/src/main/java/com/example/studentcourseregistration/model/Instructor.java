package com.example.studentcourseregistration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "instructors")
public class Instructor {

    @Id
    private String instructorId;

    private String instructorName;
    private int experience;
    private String department;

}