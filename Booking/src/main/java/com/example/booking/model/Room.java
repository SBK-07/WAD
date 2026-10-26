package com.example.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "room")
public class Room {

    @Id
    private String id;

    private String roomType;

    private boolean status;

    private double price;

    private int capacity;
}