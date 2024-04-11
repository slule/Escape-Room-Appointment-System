package dmacc.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import dmacc.edu.service.EscapeRoomService;

@Controller
public class ViewController {
    
    private final EscapeRoomService escapeRoomService;

    public ViewController(EscapeRoomService escapeRoomService) {
        this.escapeRoomService = escapeRoomService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/escapeRooms")
    public String showEscapeRooms(Model model) {
        model.addAttribute("escapeRooms", escapeRoomService.getAllEscapeRooms());
        return "escapeRooms";
    }

}