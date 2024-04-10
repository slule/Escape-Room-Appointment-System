package dmacc.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.edu.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}