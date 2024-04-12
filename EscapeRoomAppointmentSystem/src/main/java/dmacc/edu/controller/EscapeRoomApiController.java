package dmacc.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import dmacc.edu.model.EscapeRoom;
import dmacc.edu.service.EscapeRoomService;

/**
 * This class represents the API controller for managing escape rooms.
 * It handles HTTP requests related to escape rooms.
 */
@RestController
@RequestMapping("/api/escaperooms")
public class EscapeRoomApiController {

    @Autowired
    private EscapeRoomService escapeRoomService;

    /**
     * Retrieves all escape rooms.
     * 
     * @return ResponseEntity<List<EscapeRoom>> - The response entity containing the list of escape rooms
     */
    @GetMapping
    public ResponseEntity<List<EscapeRoom>> getAllEscapeRooms() {
        List<EscapeRoom> escapeRooms = escapeRoomService.getAllEscapeRooms();
        return ResponseEntity.ok(escapeRooms);
    }

    /**
     * Retrieves an escape room by its ID.
     * 
     * @param id - The ID of the escape room
     * @return ResponseEntity<EscapeRoom> - The response entity containing the escape room
     */
    @GetMapping("/{id}")
    public ResponseEntity<EscapeRoom> getEscapeRoomById(@PathVariable Long id) {
        EscapeRoom escapeRoom = escapeRoomService.getEscapeRoomById(id);
        if (escapeRoom != null) {
            return ResponseEntity.ok(escapeRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new escape room.
     * 
     * @param escapeRoom - The escape room to be created
     * @return ResponseEntity<EscapeRoom> - The response entity containing the created escape room
     */
    @PostMapping
    public ResponseEntity<EscapeRoom> createEscapeRoom(@RequestBody EscapeRoom escapeRoom) {
        EscapeRoom createdEscapeRoom = escapeRoomService.createEscapeRoom(escapeRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEscapeRoom);
    }
}