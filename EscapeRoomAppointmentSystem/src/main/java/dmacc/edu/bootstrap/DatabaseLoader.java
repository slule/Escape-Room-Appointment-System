package dmacc.edu.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import dmacc.edu.model.EscapeRoom;
import dmacc.edu.repository.EscapeRoomRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final EscapeRoomRepository escapeRoomRepository;

    public DatabaseLoader(EscapeRoomRepository escapeRoomRepository) {
        this.escapeRoomRepository = escapeRoomRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        if (escapeRoomRepository.count() == 0) {
            loadEscapeRooms();
        }
    }

    private void loadEscapeRooms() {
        escapeRoomRepository.save(new EscapeRoom("Pirate's Cove", "Find the hidden treasure before time runs out.", 5, 60, 25.00, "Available"));
        escapeRoomRepository.save(new EscapeRoom("Haunted Mansion", "Escape the ghosts that haunt this creepy old house.", 6, 90, 30.00, "Available"));
    }
}