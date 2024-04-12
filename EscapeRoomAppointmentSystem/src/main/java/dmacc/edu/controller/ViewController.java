package dmacc.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // This method handles the GET request for the "/login" endpoint
    @GetMapping("/login")
    public String login() {
        // It returns the string "login" which represents the name of the view template to be rendered
        return "login";
    }
    

    // This method handles the GET request for the "/adminLogin" endpoint
    @GetMapping("/adminLogin")
    public String adminLogin() {
        // It returns the string "adminLogin" which represents the name of the view template to be rendered
        return "adminLogin";
    }
    
}