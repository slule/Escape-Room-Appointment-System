package dmacc.edu.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;
}