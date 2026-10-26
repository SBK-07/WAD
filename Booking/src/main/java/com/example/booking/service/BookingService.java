package com.example.booking.service;

import com.example.booking.model.Booking;
import com.example.booking.model.Room;
import com.example.booking.repository.BookingRepository;
import com.example.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletRegistration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Booking bookRoom(Booking booking){
        Booking saved= bookingRepository.save(booking);

        String roomId=booking.getRoomId();
        Room room = roomRepository.findById(roomId).orElse(null);

        if(room != null){
            room.setStatus(false);
            roomRepository.save(room);
        }

        return saved;
    }

    public Map<String, Long> getBookingAnalytics() {

        List<Booking> bookings = bookingRepository.findAll();

        Map<String, Long> analytics = new HashMap<>();

        analytics.put("SINGLE", 0L);
        analytics.put("DOUBLE", 0L);
        analytics.put("DELUXE", 0L);
        analytics.put("SUITE", 0L);

        for (Booking booking : bookings) {

            Room room = roomRepository
                    .findById(booking.getRoomId())
                    .orElse(null);

            if (room != null && room.getRoomType() != null) {
                String roomType =
                        room.getRoomType().toUpperCase();

                if (analytics.containsKey(roomType)) {
                    analytics.put(
                            roomType,
                            analytics.get(roomType) + 1
                    );
                }
            }
        }

        return analytics;
    }


}