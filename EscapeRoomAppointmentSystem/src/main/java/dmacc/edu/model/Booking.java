package dmacc.edu.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private EscapeRoom escapeRoom;

    private LocalDateTime startTime;
    private String customerName;
    private String customerEmail;
    private int numberOfPlayers;
    private boolean paid;

    // Constructors
    public Booking() {
    }

    public Booking(EscapeRoom escapeRoom, LocalDateTime startTime, String customerName,
                   String customerEmail, int numberOfPlayers, boolean paid) {
        this.escapeRoom = escapeRoom;
        this.startTime = startTime;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.numberOfPlayers = numberOfPlayers;
        this.paid = paid;
    }

}