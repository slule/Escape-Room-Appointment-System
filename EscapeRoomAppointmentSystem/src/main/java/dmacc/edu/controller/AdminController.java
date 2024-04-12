package dmacc.edu.controller;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmacc.edu.model.Booking;
import dmacc.edu.model.EscapeRoom;
import dmacc.edu.service.BookingService;
import dmacc.edu.service.EscapeRoomService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EscapeRoomService escapeRoomService;

    @Autowired
    private BookingService bookingService;
    
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("escapeRooms", escapeRoomService.getAllEscapeRooms());
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "adminDashboard";
    }

    @GetMapping("/editEscapeRoom")
    public String editEscapeRoom(@RequestParam Long id, Model model) {
        model.addAttribute("escapeRoom", escapeRoomService.getEscapeRoomById(id));
        return "editEscapeRoom";
    }

    @PostMapping("/updateEscapeRoom")
    public String updateEscapeRoom(EscapeRoom escapeRoom) {
        escapeRoomService.updateEscapeRoom(escapeRoom);
        return "redirect:dashboard";
    }

    @GetMapping("/deleteEscapeRoom")
    public String deleteEscapeRoom(@RequestParam Long id) {
        escapeRoomService.deleteEscapeRoom(id);
        return "redirect:dashboard";
    }

    @GetMapping("/editBooking")
    public String editBooking(@RequestParam Long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        model.addAttribute("availableTimes", bookingService.getAvailableTimesForRoomEdit(booking.getEscapeRoom().getId(), booking.getDate(), booking.getId()));
        model.addAttribute("escapeRooms", escapeRoomService.getAllEscapeRooms());
        return "editBooking";
    }


    @PostMapping("/updateBooking")
    public String updateBooking(Booking booking, @RequestParam Long escapeRoomId, @RequestParam String endTime) {
        System.out.println("Received endTime string: " + endTime);
        if (booking == null || booking.getId() == null) {
            throw new IllegalArgumentException("Booking or Booking ID must not be null");
        }
        System.out.println("Updating booking with ID: " + booking.getId());

        System.out.println("Received endTime string: " + endTime);

        try {
            System.out.println("Before setting endTime: " + booking.getEndTime());
            booking.setEndTime(LocalTime.parse(endTime));
            System.out.println("After setting endTime: " + booking.getEndTime());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format provided", e);
        }

        EscapeRoom escapeRoom = escapeRoomService.getEscapeRoomById(escapeRoomId);

        booking.setEscapeRoom(escapeRoom);

        if (booking.getEscapeRoom() != null) {
            double updatedPrice = bookingService.calculatePrice(booking);
            booking.setPrice(updatedPrice);
        } else {

            booking.setPrice(0.0);
        }

        System.out.println("Received booking: " + booking);
        bookingService.updateBooking(booking.getId(), booking);
        return "redirect:dashboard";
    }


    @GetMapping("/deleteBooking")
    public String deleteBooking(@RequestParam Long id) {
        bookingService.deleteBooking(id);
        return "redirect:dashboard";
    }
    
    @GetMapping("/manageRooms")
    public String manageRooms(Model model) {
        List<EscapeRoom> rooms = escapeRoomService.getAllEscapeRooms();
        model.addAttribute("escapeRooms", rooms);
        return "manageRooms";
    }
    
    @GetMapping("/manageBookings")
    public String manageBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        bookings = bookings.stream()
                            .filter(b -> b.getEscapeRoom() != null)
                            .collect(Collectors.toList());
        model.addAttribute("bookings", bookings);
        return "manageBookings";
    }
    
    @GetMapping("/addEscapeRoom")
    public String showAddEscapeRoomForm(Model model) {
        model.addAttribute("escapeRoom", new EscapeRoom());
        return "addEscapeRoom";
    }
    
    @PostMapping("/createEscapeRoom")
    public String createEscapeRoom(@ModelAttribute("escapeRoom") EscapeRoom escapeRoom) {
        escapeRoomService.createEscapeRoom(escapeRoom);
        return "redirect:/admin/manageRooms";
    }
}