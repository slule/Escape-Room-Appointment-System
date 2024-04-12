package dmacc.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import dmacc.edu.model.EscapeRoom;
import dmacc.edu.service.EscapeRoomService;

@Controller
@RequestMapping("/escapeRooms")
public class EscapeRoomViewController {

    @Autowired
    private EscapeRoomService escapeRoomService;

    @GetMapping
    public String showEscapeRooms(Model model) {
        List<EscapeRoom> escapeRooms = escapeRoomService.getAllEscapeRooms();
        model.addAttribute("escapeRooms", escapeRooms);
        return "escapeRooms";
    }
}