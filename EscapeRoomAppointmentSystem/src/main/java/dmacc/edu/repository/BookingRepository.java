package dmacc.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.edu.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
 List<Booking> findByCustomerEmail(String customerEmail);
}
