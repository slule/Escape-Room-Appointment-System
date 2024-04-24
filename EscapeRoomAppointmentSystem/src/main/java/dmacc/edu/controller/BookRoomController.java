package dmacc.edu.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmacc.edu.model.Booking;
import dmacc.edu.model.EscapeRoom;
import dmacc.edu.model.User;
import dmacc.edu.service.BookingService;
import dmacc.edu.service.EscapeRoomService;
import dmacc.edu.service.UserService;

@Controller
public class BookRoomController {

    private final BookingService bookingService;
    private final EscapeRoomService escapeRoomService;
    private final UserService userService;

    // Constructor injection to initialize the required services
    public BookRoomController(BookingService bookingService, EscapeRoomService escapeRoomService, UserService userService) {
        this.bookingService = bookingService;
        this.escapeRoomService = escapeRoomService;
        this.userService = userService;
    }

    // Handler method for GET request to "/bookRoom"
    @GetMapping("/bookRoom")
    public String showBookingForm(@RequestParam(required = false) Long roomId, Model model) {
        // Get the current user
        User currentUser = userService.getCurrentUser();
        // Add the list of available escape rooms and the current user to the model
        model.addAttribute("escapeRooms", escapeRoomService.getAllAvailableEscapeRooms());
        model.addAttribute("currentUser", currentUser);

        // Create a new booking object 
        Booking booking = new Booking();
        if (roomId != null) {
            // If a room ID is provided, get the corresponding escape room 
            EscapeRoom selectedRoom = escapeRoomService.getEscapeRoomById(roomId);
            if (selectedRoom != null) {
                // Set the selected escape room in the booking object 
                booking.setEscapeRoom(selectedRoom);
            }
        }
        // Add the booking object to the model 
        model.addAttribute("booking", booking);
        // Return the view name "bookRoom" 
        return "bookRoom";
    }

    // Handler method for POST request to "/bookRoom"
    @PostMapping("/bookRoom")
    public String processBooking(Booking booking, @RequestParam Long escapeRoomId, 
                                 @RequestParam String date, @RequestParam String startTime,
                                 @RequestParam String endTime, @RequestParam int numberOfPlayers) {
        // Get the escape room by ID
        EscapeRoom room = escapeRoomService.getEscapeRoomById(escapeRoomId);
        // Set the escape room, date, start time, end time, and number of players in the booking object
        booking.setEscapeRoom(room);
        booking.setDate(LocalDate.parse(date));
        booking.setStartTime(LocalTime.parse(startTime));
        booking.setEndTime(LocalTime.parse(endTime));
        booking.setNumberOfPlayers(numberOfPlayers);

        // Check if the room is available for the specified date and time
        if (!bookingService.isRoomAvailable(escapeRoomId, booking.getDate(), booking.getStartTime(), booking.getEndTime())) {
            // If the room is not available, redirect to the booking form with an error message
            return "redirect:/bookRoom?error=unavailable";
        }

        // Set the paid status of the booking to false
        booking.setPaid(false);
        // Calculate the price for the booking
        double calculatedPrice = bookingService.calculatePrice(booking);
        // Set the price in the booking object
        booking.setPrice(calculatedPrice);
        // Create the booking
        bookingService.createBooking(booking);
        // Redirect to the user's profile page
        return "redirect:/profile";
    }
}