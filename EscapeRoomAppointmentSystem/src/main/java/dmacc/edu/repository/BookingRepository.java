package dmacc.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.edu.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}