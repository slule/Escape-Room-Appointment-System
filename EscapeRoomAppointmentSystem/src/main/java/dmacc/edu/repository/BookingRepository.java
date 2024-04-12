package dmacc.edu.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.edu.model.Booking;

/**
 * This interface represents a repository for managing bookings in the Escape Room Appointment System.
 * It extends the JpaRepository interface, providing CRUD operations for the Booking entity.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Retrieves a list of bookings associated with a specific customer email.
     *
     * @param customerEmail the email of the customer
     * @return a list of bookings associated with the customer email
     */
    List<Booking> findByCustomerEmail(String customerEmail);

    /**
     * Retrieves a list of bookings associated with a specific escape room ID and date.
     *
     * @param escapeRoomId the ID of the escape room
     * @param date the date of the bookings
     * @return a list of bookings associated with the escape room ID and date
     */
    List<Booking> findByEscapeRoomIdAndDate(Long escapeRoomId, LocalDate date);
}
