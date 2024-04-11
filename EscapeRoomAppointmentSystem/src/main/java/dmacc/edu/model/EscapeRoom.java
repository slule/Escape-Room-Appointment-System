package dmacc.edu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EscapeRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int capacity;
    private int duration; // in minutes
    private double price;
    private String availability; //Available, Booked, or Unavailable

    // Constructors
    public EscapeRoom() {
    }

    public EscapeRoom(String name, String description, int capacity, int duration, double price, String availability) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.duration = duration;
        this.price = price;
        this.availability = availability;
    }
}