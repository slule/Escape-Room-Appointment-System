package dmacc.edu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmacc.edu.model.Booking;
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
            existingBooking.setStartTime(booking.getStartTime());
            existingBooking.setCustomerName(booking.getCustomerName());
            existingBooking.setCustomerEmail(booking.getCustomerEmail());
            existingBooking.setNumberOfPlayers(booking.getNumberOfPlayers());
            existingBooking.setPaid(booking.isPaid());
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
}