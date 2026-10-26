package com.example.booking.repository;

import com.example.booking.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository< Room,String> {
}