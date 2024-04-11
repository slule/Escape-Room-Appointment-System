package dmacc.edu.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime; // Added endTime to store the end time of the booking
    private String customerName;
    private String customerEmail;
    private int numberOfPlayers;
    private boolean paid;
    private double price; // Added price to calculate and store the booking price

    // Constructors, getters, and setters
    public Booking() {
    }

    public Booking(EscapeRoom escapeRoom, LocalDate date, LocalTime startTime, LocalTime endTime,
                   String customerName, String customerEmail, int numberOfPlayers, boolean paid, double price) {
        this.escapeRoom = escapeRoom;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.numberOfPlayers = numberOfPlayers;
        this.paid = paid;
        this.price = price;
    }
}
