package dmacc.edu.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import dmacc.edu.model.EscapeRoom;
import dmacc.edu.repository.EscapeRoomRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final EscapeRoomRepository escapeRoomRepository;

    /**
     * Constructs a DatabaseLoader object with the specified EscapeRoomRepository.
     * 
     * @param escapeRoomRepository the repository for managing escape rooms
     */
    public DatabaseLoader(EscapeRoomRepository escapeRoomRepository) {
        this.escapeRoomRepository = escapeRoomRepository;
    }

    /**
     * Runs the database loader.
     * 
     * @param strings the command line arguments
     * @throws Exception if an error occurs while running the loader
     */
    @Override
    public void run(String... strings) throws Exception {
        if (escapeRoomRepository.count() == 0) {
            loadEscapeRooms();
        }
    }

    /**
     * Loads the initial escape rooms into the database.
     */
    private void loadEscapeRooms() {
        escapeRoomRepository.save(new EscapeRoom("Pirate's Cove", "Find the hidden treasure before time runs out.", 5, 60, 25.00, "Available"));
        escapeRoomRepository.save(new EscapeRoom("Haunted Mansion", "Escape the ghosts that haunt this creepy old house.", 6, 60, 30.00, "Available"));
    }
}