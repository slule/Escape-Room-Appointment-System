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

    public List<EscapeRoom> getAllEscapeRooms() {
        return escapeRoomRepository.findAll();
    }

    public EscapeRoom getEscapeRoomById(Long id) {
        return escapeRoomRepository.findById(id).orElse(null);
    }

    public EscapeRoom updateEscapeRoom(EscapeRoom escapeRoom) {
        EscapeRoom existingEscapeRoom = escapeRoomRepository.findById(escapeRoom.getId()).orElse(null);
        if (existingEscapeRoom != null) {
            existingEscapeRoom.setName(escapeRoom.getName());
            existingEscapeRoom.setDescription(escapeRoom.getDescription());
            existingEscapeRoom.setCapacity(escapeRoom.getCapacity());
            existingEscapeRoom.setDuration(escapeRoom.getDuration());
            existingEscapeRoom.setPrice(escapeRoom.getPrice());
            existingEscapeRoom.setAvailability(escapeRoom.getAvailability());
            escapeRoomRepository.save(existingEscapeRoom);
            return existingEscapeRoom;
        }
        return null;
    }

    public void deleteEscapeRoom(Long id) {
        escapeRoomRepository.deleteById(id);
    }

    public EscapeRoom createEscapeRoom(EscapeRoom escapeRoom) {
        return escapeRoomRepository.save(escapeRoom);
    }

    public List<EscapeRoom> getAllAvailableEscapeRooms() {
        return escapeRoomRepository.findAll().stream()
                                    .filter(room -> "Available".equals(room.getAvailability()))
                                    .collect(Collectors.toList());
    }
}