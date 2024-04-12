package dmacc.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dmacc.edu.model.User;
import dmacc.edu.service.UserService;

/**
 * This class represents the controller for managing user-related operations.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Displays the registration form for a new user.
     * 
     * @param model the model object for passing data to the view
     * @return the name of the view to be rendered
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Registers a new user account.
     * 
     * @param user                the user object containing the registration details
     * @param result              the binding result object for validation errors
     * @param redirectAttributes  the redirect attributes object for flash messages
     * @return the name of the view to be rendered or a redirect URL
     */
    @PostMapping("/register")
    public String registerUserAccount(User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }
        
        try {
            userService.registerNewUser(user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), passwordEncoder);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
        
        redirectAttributes.addFlashAttribute("message", "Registration successful! You can now login.");
        return "redirect:/login";
    }

}
