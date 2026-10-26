package com.example.booking.repository;

import com.example.booking.model.Booking;
import com.example.booking.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.print.Book;
import java.util.Optional;

public interface BookingRepository extends MongoRepository< Booking,String> {
    public Optional<Booking> findByRoomId(String roomId);
}