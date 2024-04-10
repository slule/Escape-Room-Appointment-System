package dmacc.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dmacc.edu.model.EscapeRoom;
import dmacc.edu.service.EscapeRoomService;

@RestController
@RequestMapping("/api/escaperooms")
public class EscapeRoomController {

    @Autowired
    private EscapeRoomService escapeRoomService;

    @GetMapping
    public ResponseEntity<List<EscapeRoom>> getAllEscapeRooms() {
        List<EscapeRoom> escapeRooms = escapeRoomService.getAllEscapeRooms();
        return ResponseEntity.ok(escapeRooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscapeRoom> getEscapeRoomById(@PathVariable Long id) {
        EscapeRoom escapeRoom = escapeRoomService.getEscapeRoomById(id);
        if (escapeRoom != null) {
            return ResponseEntity.ok(escapeRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EscapeRoom> createEscapeRoom(@RequestBody EscapeRoom escapeRoom) {
        EscapeRoom createdEscapeRoom = escapeRoomService.createEscapeRoom(escapeRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEscapeRoom);
    }

}