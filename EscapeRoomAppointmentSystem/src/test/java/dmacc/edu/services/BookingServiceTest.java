package dmacc.edu.services;

import dmacc.edu.model.Booking;
import dmacc.edu.repository.BookingRepository;
import dmacc.edu.service.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookings();

        assertEquals(1, result.size());
    }

    @Test
    public void testGetBookingById() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setId(bookingId);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        Booking result = bookingService.getBookingById(bookingId);

        assertNotNull(result);
        assertEquals(bookingId, result.getId());
    }

    @Test
    public void testCreateBooking() {
        Booking booking = new Booking();
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.createBooking(new Booking());

        assertNotNull(result);
    }

    @Test
    public void testUpdateBooking() {
        Long bookingId = 1L;
        Booking existingBooking = new Booking();
        existingBooking.setId(bookingId);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(existingBooking);

        Booking updatedBooking = new Booking();
        updatedBooking.setId(bookingId);
        Booking result = bookingService.updateBooking(bookingId, updatedBooking);

        assertNotNull(result);
        assertEquals(bookingId, result.getId());
    }

    @Test
    public void testDeleteBooking() {
        Long bookingId = 1L;
        doNothing().when(bookingRepository).deleteById(bookingId);

        bookingService.deleteBooking(bookingId);

        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

   
}