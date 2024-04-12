package dmacc.edu.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmacc.edu.model.Booking;
import dmacc.edu.model.EscapeRoom;
import dmacc.edu.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        if (existingBooking != null) {
            existingBooking.setEscapeRoom(booking.getEscapeRoom());
            existingBooking.setDate(booking.getDate());
            existingBooking.setStartTime(booking.getStartTime());
            existingBooking.setEndTime(booking.getEndTime());
            existingBooking.setCustomerName(booking.getCustomerName());
            existingBooking.setCustomerEmail(booking.getCustomerEmail());
            existingBooking.setNumberOfPlayers(booking.getNumberOfPlayers());
            existingBooking.setPaid(booking.isPaid());
            existingBooking.setPrice(booking.getPrice());
            return bookingRepository.save(existingBooking);
        }
        return null;
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> findBookingsByCustomerEmail(String email) {
        return bookingRepository.findByCustomerEmail(email);
    }
    
    public boolean isRoomAvailable(Long roomId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Booking> bookings = bookingRepository.findByEscapeRoomIdAndDate(roomId, date);
        LocalTime bufferEndTime = endTime.plusHours(1); // Add buffer time
        return bookings.stream().noneMatch(booking ->
            booking.getStartTime().isBefore(bufferEndTime) && booking.getEndTime().plusHours(1).isAfter(startTime)
        );
    }
    
    public double calculatePrice(Booking booking) {
        long durationInMinutes = Duration.between(booking.getStartTime(), booking.getEndTime()).toMinutes();
        double durationInHours = Math.ceil(durationInMinutes / 60.0); // Round up to the nearest hour
        double basePrice = durationInHours * booking.getEscapeRoom().getPrice();
        double additionalFee = 5.0 * booking.getNumberOfPlayers();
        return basePrice + additionalFee;
    }


    
    public List<LocalTime> getAvailableTimesForRoom(Long roomId, LocalDate date) {
        List<Booking> existingBookings = bookingRepository.findByEscapeRoomIdAndDate(roomId, date);
        List<LocalTime> availableTimes = new ArrayList<>();
        
        LocalTime openingTime = LocalTime.of(9, 0);
        LocalTime closingTime = LocalTime.of(21, 0);

        IntStream.range(openingTime.getHour(), closingTime.getHour()).forEach(hour -> {
            LocalTime proposedStart = LocalTime.of(hour, 0);
            LocalTime proposedEnd = LocalTime.of(hour + 3, 0);

            boolean isAvailable = existingBookings.stream().noneMatch(booking ->
                booking.getStartTime().isBefore(proposedEnd) && booking.getEndTime().plusHours(1).isAfter(proposedStart)
            );

            if (isAvailable) {
                availableTimes.add(proposedStart);
            }
        });

        return availableTimes;
    }
    
    public List<LocalTime> getAvailableTimesForRoomEdit(Long roomId, LocalDate date, Long excludeBookingId) {
        List<Booking> existingBookings = bookingRepository.findByEscapeRoomIdAndDate(roomId, date);
        existingBookings.removeIf(b -> b.getId().equals(excludeBookingId));
        List<LocalTime> availableTimes = new ArrayList<>();
        
        LocalTime openingTime = LocalTime.of(9, 0);
        LocalTime closingTime = LocalTime.of(21, 0);

        IntStream.range(openingTime.getHour(), closingTime.getHour()).forEach(hour -> {
            LocalTime proposedStart = LocalTime.of(hour, 0);
            LocalTime proposedEnd = LocalTime.of(hour + 3, 0);

            boolean isAvailable = existingBookings.stream().noneMatch(booking ->
                booking.getStartTime().isBefore(proposedEnd) && booking.getEndTime().plusHours(1).isAfter(proposedStart)
            );

            if (isAvailable) {
                availableTimes.add(proposedStart);
            }
        });

        return availableTimes;
    }

}