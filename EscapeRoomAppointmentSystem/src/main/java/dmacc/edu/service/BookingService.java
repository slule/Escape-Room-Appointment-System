package dmacc.edu.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmacc.edu.model.Booking;
import dmacc.edu.repository.BookingRepository;

/**
 * This class represents the service layer for managing bookings in the Escape Room Appointment System.
 * It provides methods for retrieving, creating, updating, and deleting bookings, as well as checking room availability
 * and calculating booking prices.
 */
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Retrieves all bookings from the database.
     *
     * @return a list of all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id the ID of the booking
     * @return the booking with the specified ID, or null if not found
     */
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    /**
     * Creates a new booking.
     *
     * @param booking the booking to be created
     * @return the created booking
     */
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    /**
     * Updates an existing booking.
     *
     * @param id      the ID of the booking to be updated
     * @param booking the updated booking information
     * @return the updated booking, or null if the booking with the specified ID does not exist
     */
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

    /**
     * Deletes a booking by its ID.
     *
     * @param id the ID of the booking to be deleted
     */
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    /**
     * Retrieves all bookings associated with a customer's email.
     *
     * @param email the customer's email
     * @return a list of bookings associated with the specified email
     */
    public List<Booking> findBookingsByCustomerEmail(String email) {
        return bookingRepository.findByCustomerEmail(email);
    }

    /**
     * Checks if a room is available for booking at the specified date and time.
     *
     * @param roomId    the ID of the room
     * @param date      the date of the booking
     * @param startTime the start time of the booking
     * @param endTime   the end time of the booking
     * @return true if the room is available, false otherwise
     */
    public boolean isRoomAvailable(Long roomId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Booking> bookings = bookingRepository.findByEscapeRoomIdAndDate(roomId, date);
        LocalTime bufferEndTime = endTime.plusHours(1); // Add buffer time
        return bookings.stream().noneMatch(booking ->
                booking.getStartTime().isBefore(bufferEndTime) && booking.getEndTime().plusHours(1).isAfter(startTime)
        );
    }

    /**
     * Calculates the price for a booking based on the duration and number of players.
     *
     * @param booking the booking for which to calculate the price
     * @return the calculated price
     */
    public double calculatePrice(Booking booking) {
        long durationInMinutes = Duration.between(booking.getStartTime(), booking.getEndTime()).toMinutes();
        double durationInHours = Math.ceil(durationInMinutes / 60.0); // Round up to the nearest hour
        double basePrice = durationInHours * booking.getEscapeRoom().getPrice();
        double additionalFee = 5.0 * booking.getNumberOfPlayers();
        return basePrice + additionalFee;
    }

    /**
     * Retrieves the available times for booking a room on the specified date.
     *
     * @param roomId the ID of the room
     * @param date   the date for which to retrieve available times
     * @return a list of available times for booking
     */
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

    /**
     * Retrieves the available times for editing a booking of a room on the specified date,
     * excluding the booking with the specified ID.
     *
     * @param roomId           the ID of the room
     * @param date             the date for which to retrieve available times
     * @param excludeBookingId the ID of the booking to be excluded
     * @return a list of available times for editing a booking
     */
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