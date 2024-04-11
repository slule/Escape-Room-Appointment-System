// File: \src\main\java\dmacc\edu\controller\BookRoomController.java
package dmacc.edu.controller;

import java.time.LocalDate;

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

    public BookRoomController(BookingService bookingService, EscapeRoomService escapeRoomService, UserService userService) {
        this.bookingService = bookingService;
        this.escapeRoomService = escapeRoomService;
        this.userService = userService;
    }

    @GetMapping("/bookRoom")
    public String showBookingForm(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("escapeRooms", escapeRoomService.getAllAvailableEscapeRooms());
        model.addAttribute("booking", new Booking());
        model.addAttribute("currentUser", currentUser);
        return "bookRoom";
    }

    @PostMapping("/bookRoom")
    public String processBooking(Booking booking, @RequestParam Long escapeRoomId, @RequestParam String date) {
        EscapeRoom escapeRoom = escapeRoomService.getEscapeRoomById(escapeRoomId);
        if (escapeRoom != null && escapeRoom.getAvailability().equals("Available")) {
            booking.setEscapeRoom(escapeRoom);
            booking.setDate(LocalDate.parse(date));
            bookingService.createBooking(booking);
            escapeRoom.setAvailability("Booked");
            escapeRoomService.updateEscapeRoom(escapeRoomId, escapeRoom);
            return "redirect:/profile";
        } else {
            return "redirect:/bookRoom?error=true";
        }
    }
}