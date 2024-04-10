package dmacc.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.edu.model.EscapeRoom;

@Repository
public interface EscapeRoomRepository extends JpaRepository<EscapeRoom, Long> {
}