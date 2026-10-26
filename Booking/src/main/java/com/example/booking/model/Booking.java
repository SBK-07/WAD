package com.example.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "booking")
public class Booking{
    @Id
    private String id;

    private String roomId;

    private String customerName;

    private String checkIn;

    private String checkOut;

    private double amount;

}