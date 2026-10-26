package com.example.booking.controller;

import com.example.booking.model.Room;
import com.example.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody Room room){
        Room saved=roomService.addRoom(room);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);


    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable String id){
        return roomService.getRoomById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable String id, @RequestBody Room room){
        return roomService.updateRoom(id, room).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String id){
        boolean del= roomService.deleteRoom(id);

        if(del){
            return ResponseEntity.noContent().build();
        }

        return  ResponseEntity.notFound().build();
    }
}