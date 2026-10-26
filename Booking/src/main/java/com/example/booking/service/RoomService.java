package com.example.booking.service;

import com.example.booking.model.Room;
import com.example.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public Room addRoom(Room room){
        return roomRepository.save(room);

    }

    public Optional<Room> getRoomById(String id){
        return roomRepository.findById(id);
    }

    public Optional<Room> updateRoom(String id, Room updatedRoom){
        return roomRepository.findById(id).map(room -> {
            room.setCapacity(updatedRoom.getCapacity());
            room.setPrice(updatedRoom.getPrice());
            room.setRoomType(updatedRoom.getRoomType());
            room.setStatus(updatedRoom.isStatus());

            return roomRepository.save(room);

        });
    }

    public boolean deleteRoom(String id){
        if(roomRepository.existsById(id)){
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }


}