package com.example.booking.controller;

import com.example.booking.model.Booking;
import com.example.booking.model.Room;
import com.example.booking.service.BookingService;
import com.example.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final RoomService roomService;

    @GetMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @PostMapping("/book")
    public ResponseEntity<Booking> addRoom(@RequestBody Booking booking){
        Booking saved= bookingService.bookRoom(booking);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);

    }

    @GetMapping("/analytics")
    public Map<String, Long> getBookingAnalytics() {
        return bookingService.getBookingAnalytics();
    }



}