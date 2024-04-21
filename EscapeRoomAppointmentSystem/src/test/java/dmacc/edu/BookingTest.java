/**  
* @Author Shalom Lule - slule@dmacc.edu  
* CIS175 <Spring 2024>
* Apr 21, 2024 
*/ 
package dmacc.edu;

import dmacc.edu.model.Booking;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTest {

    @Test
    public void testBookingConstructor() {
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);
        String customerName = "John Doe";
        String customerEmail = "john.doe@example.com";
        int numberOfPlayers = 4;
        boolean paid = false;
        double price = 50.0;

        Booking booking = new Booking(null, date, startTime, endTime,
                customerName, customerEmail, numberOfPlayers, paid, price);

        assertEquals(date, booking.getDate());
        assertEquals(startTime, booking.getStartTime());
        assertEquals(endTime, booking.getEndTime());
        assertEquals(customerName, booking.getCustomerName());
        assertEquals(customerEmail, booking.getCustomerEmail());
        assertEquals(numberOfPlayers, booking.getNumberOfPlayers());
        assertEquals(paid, booking.isPaid());
        assertEquals(price, booking.getPrice());
    }
}