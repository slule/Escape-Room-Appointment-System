package dmacc.edu.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dmacc.edu.model.Booking;
import dmacc.edu.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Get all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }
    
    // Get available times for a specific room and date
    @GetMapping("/available-times/{roomId}/{date}")
    public ResponseEntity<List<LocalTime>> getAvailableTimes(@PathVariable Long roomId, @PathVariable String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<LocalTime> availableTimes = bookingService.getAvailableTimesForRoom(roomId, parsedDate);
        return ResponseEntity.ok(availableTimes);
    }
    
    // Get available times for a specific room and date (for editing)
    @GetMapping("/available-times-edit/{roomId}/{date}")
    public ResponseEntity<List<LocalTime>> getAvailableTimesForRoomEdit(
            @PathVariable Long roomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long excludeBookingId) {
        
        List<LocalTime> times = bookingService.getAvailableTimesForRoomEdit(roomId, date, excludeBookingId);
        return ResponseEntity.ok(times);
    }

}