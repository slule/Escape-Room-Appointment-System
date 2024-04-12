package dmacc.edu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmacc.edu.model.EscapeRoom;
import dmacc.edu.repository.EscapeRoomRepository;

@Service
public class EscapeRoomService {

    @Autowired
    private EscapeRoomRepository escapeRoomRepository;

    // Retrieve all escape rooms from the repository
    public List<EscapeRoom> getAllEscapeRooms() {
        return escapeRoomRepository.findAll();
    }

    // Retrieve an escape room by its ID from the repository
    public EscapeRoom getEscapeRoomById(Long id) {
        return escapeRoomRepository.findById(id).orElse(null);
    }

    // Update an existing escape room in the repository
    public EscapeRoom updateEscapeRoom(EscapeRoom escapeRoom) {
        EscapeRoom existingEscapeRoom = escapeRoomRepository.findById(escapeRoom.getId()).orElse(null);
        if (existingEscapeRoom != null) {
            // Update the escape room's attributes
            existingEscapeRoom.setName(escapeRoom.getName());
            existingEscapeRoom.setDescription(escapeRoom.getDescription());
            existingEscapeRoom.setCapacity(escapeRoom.getCapacity());
            existingEscapeRoom.setDuration(escapeRoom.getDuration());
            existingEscapeRoom.setPrice(escapeRoom.getPrice());
            existingEscapeRoom.setAvailability(escapeRoom.getAvailability());
            // Save the updated escape room in the repository
            escapeRoomRepository.save(existingEscapeRoom);
            return existingEscapeRoom;
        }
        return null;
    }

    // Delete an escape room from the repository by its ID
    public void deleteEscapeRoom(Long id) {
        escapeRoomRepository.deleteById(id);
    }

    // Create a new escape room and save it in the repository
    public EscapeRoom createEscapeRoom(EscapeRoom escapeRoom) {
        return escapeRoomRepository.save(escapeRoom);
    }

    // Retrieve all available escape rooms from the repository
    public List<EscapeRoom> getAllAvailableEscapeRooms() {
        return escapeRoomRepository.findAll().stream()
                                    .filter(room -> "Available".equals(room.getAvailability()))
                                    .collect(Collectors.toList());
    }
}