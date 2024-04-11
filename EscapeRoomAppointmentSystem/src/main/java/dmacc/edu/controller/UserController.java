package dmacc.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dmacc.edu.model.User;
import dmacc.edu.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }
        
        try {
            userService.registerNewUser(user.getUsername(), user.getPassword(), user.getName(), user.getEmail());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
        
        redirectAttributes.addFlashAttribute("message", "Registration successful! You can now login.");
        return "redirect:/login";
    }

}
