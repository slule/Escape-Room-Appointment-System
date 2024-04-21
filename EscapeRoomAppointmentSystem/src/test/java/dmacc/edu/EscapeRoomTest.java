/**  
* @Author Shalom Lule - slule@dmacc.edu  
* CIS175 <Spring 2024>
* Apr 21, 2024 
*/ 
package dmacc.edu;

import dmacc.edu.model.EscapeRoom;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EscapeRoomTest {

    @Test
    public void testEscapeRoomConstructor() {
        String name = "The Enchanted Forest";
        String description = "Navigate through a mystical forest to solve puzzles.";
        int capacity = 6;
        int duration = 60;
        double price = 100.0;
        String availability = "Available";

        EscapeRoom escapeRoom = new EscapeRoom(name, description, capacity, duration, price, availability);

        assertEquals(name, escapeRoom.getName());
        assertEquals(description, escapeRoom.getDescription());
        assertEquals(capacity, escapeRoom.getCapacity());
        assertEquals(duration, escapeRoom.getDuration());
        assertEquals(price, escapeRoom.getPrice());
        assertEquals(availability, escapeRoom.getAvailability());
    }
}