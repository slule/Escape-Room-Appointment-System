import dmacc.edu.model.EscapeRoom;
import dmacc.edu.repository.EscapeRoomRepository;
import dmacc.edu.service.EscapeRoomService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EscapeRoomServiceTest {

    @Mock
    private EscapeRoomRepository escapeRoomRepository;

    @InjectMocks
    private EscapeRoomService escapeRoomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllEscapeRooms() {
        List<EscapeRoom> escapeRooms = new ArrayList<>();
        escapeRooms.add(new EscapeRoom());
        when(escapeRoomRepository.findAll()).thenReturn(escapeRooms);

        List<EscapeRoom> result = escapeRoomService.getAllEscapeRooms();

        assertEquals(1, result.size());
    }

    @Test
    public void testGetEscapeRoomById() {
        Long roomId = 1L;
        EscapeRoom escapeRoom = new EscapeRoom();
        escapeRoom.setId(roomId);
        when(escapeRoomRepository.findById(roomId)).thenReturn(Optional.of(escapeRoom));

        EscapeRoom result = escapeRoomService.getEscapeRoomById(roomId);

        assertNotNull(result);
        assertEquals(roomId, result.getId());
    }

    @Test
    public void testUpdateEscapeRoom() {
        Long roomId = 1L;
        EscapeRoom existingEscapeRoom = new EscapeRoom();
        existingEscapeRoom.setId(roomId);
        when(escapeRoomRepository.findById(roomId)).thenReturn(Optional.of(existingEscapeRoom));
        when(escapeRoomRepository.save(any(EscapeRoom.class))).thenReturn(existingEscapeRoom);

        EscapeRoom updatedEscapeRoom = new EscapeRoom();
        updatedEscapeRoom.setId(roomId);
        updatedEscapeRoom.setName("Updated Room");

        EscapeRoom result = escapeRoomService.updateEscapeRoom(updatedEscapeRoom);

        assertNotNull(result);
        assertEquals("Updated Room", result.getName());
    }

    @Test
    public void testDeleteEscapeRoom() {
        Long roomId = 1L;
        doNothing().when(escapeRoomRepository).deleteById(roomId);

        escapeRoomService.deleteEscapeRoom(roomId);

        verify(escapeRoomRepository, times(1)).deleteById(roomId);
    }

    @Test
    public void testCreateEscapeRoom() {
        EscapeRoom newEscapeRoom = new EscapeRoom();
        when(escapeRoomRepository.save(any(EscapeRoom.class))).thenReturn(newEscapeRoom);

        EscapeRoom result = escapeRoomService.createEscapeRoom(newEscapeRoom);

        assertNotNull(result);
    }

    @Test
    public void testGetAllAvailableEscapeRooms() {
        List<EscapeRoom> availableRooms = new ArrayList<>();
        availableRooms.add(new EscapeRoom("Room 1", "Description", 6, 60, 100.0, "Available"));
        availableRooms.add(new EscapeRoom("Room 2", "Description", 4, 45, 80.0, "Available"));

        when(escapeRoomRepository.findAll()).thenReturn(availableRooms);

        List<EscapeRoom> result = escapeRoomService.getAllAvailableEscapeRooms();

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(room -> "Available".equals(room.getAvailability())));
    }

    // Add more test cases for different scenarios (e.g., no escape rooms found, specific room details, etc.)
}

