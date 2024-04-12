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

    // This method handles the GET request for "/escapeRooms" URL
    @GetMapping
    public String showEscapeRooms(Model model) {
        // Retrieve a list of all escape rooms from the service
        List<EscapeRoom> escapeRooms = escapeRoomService.getAllEscapeRooms();
        
        // Add the list of escape rooms to the model attribute
        model.addAttribute("escapeRooms", escapeRooms);
        
        // Return the name of the view template to be rendered
        return "escapeRooms";
    }
}