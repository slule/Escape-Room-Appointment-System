package dmacc.edu.controller;

import dmacc.edu.model.Booking;
import dmacc.edu.model.User;
import dmacc.edu.service.BookingService;
import dmacc.edu.service.UserService;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private BookingService bookingService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // This method handles the GET request to the "/profile" endpoint and displays the profile page.
    @GetMapping
    public String showProfilePage(Model model) {
        // Get the current user
        User user = getCurrentUser();
        // Get the bookings (appointments) associated with the current user
        List<Booking> appointments = bookingService.findBookingsByCustomerEmail(user.getEmail());
        // Add the user and appointments to the model for rendering in the view
        model.addAttribute("user", user);
        model.addAttribute("appointments", appointments);
        // Return the name of the view template to be rendered
        return "profile";
    }

    // This method handles the GET request to the "/profile/update/{attribute}" endpoint and displays the update form for the specified attribute.
    @GetMapping("/update/{attribute}")
    public String showUpdateForm(@PathVariable("attribute") String attribute, Model model) {
        // Get the current user
        User user = getCurrentUser();
        // Add the user and the attribute to be updated to the model for rendering in the view
        model.addAttribute("user", user);
        model.addAttribute("updateAttribute", attribute);
        // Return the name of the view template to be rendered
        return "updateProfile";
    }

    // This method handles the POST request to update the username.
    @PostMapping("/update/username")
    public String updateUsername(@RequestParam("username") String newUsername, RedirectAttributes redirectAttributes) {
        // Get the current user
        User currentUser = getCurrentUser();
        // Update the username of the current user
        currentUser.setUsername(newUsername);
        // Update the user in the database
        userService.updateUser(currentUser);
        // Re-authenticate the user with the updated username and password
        reAuthenticateUser(currentUser, currentUser.getPassword());
        // Add a flash attribute to indicate the success of the update
        redirectAttributes.addFlashAttribute("success", "Username updated successfully.");
        // Redirect to the profile page
        return "redirect:/profile";
    }

    // This method handles the POST request to update the name.
    @PostMapping("/update/name")
    public String updateName(@RequestParam("name") String newName, RedirectAttributes redirectAttributes) {
        // Get the current user
        User currentUser = getCurrentUser();
        // Update the name of the current user
        currentUser.setName(newName);
        // Update the user in the database
        userService.updateUser(currentUser);
        // Add a flash attribute to indicate the success of the update
        redirectAttributes.addFlashAttribute("success", "Name updated successfully.");
        // Redirect to the profile page
        return "redirect:/profile";
    }

    // This method handles the POST request to update the email.
    @PostMapping("/update/email")
    public String updateEmail(@RequestParam("email") String newEmail, RedirectAttributes redirectAttributes) {
        // Get the current user
        User currentUser = getCurrentUser();
        // Update the email of the current user
        currentUser.setEmail(newEmail);
        // Update the user in the database
        userService.updateUser(currentUser);
        // Add a flash attribute to indicate the success of the update
        redirectAttributes.addFlashAttribute("success", "Email updated successfully.");
        // Redirect to the profile page
        return "redirect:/profile";
    }

    // This method handles the POST request to update the password.
    @PostMapping("/update/password")
    public String updatePassword(@RequestParam String newPassword, @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
        // Check if the new password and confirm password match
        if (!StringUtils.equals(newPassword, confirmPassword)) {
            // If the passwords do not match, add a flash attribute to indicate the error
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            // Redirect to the password update form
            return "redirect:/profile/update/password";
        }
        // Get the current user
        User currentUser = getCurrentUser();
        // Encode the new password
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        // Update the password of the current user
        currentUser.setPassword(encodedNewPassword);
        // Update the user in the database
        userService.updateUser(currentUser);
        // Re-authenticate the user with the updated username and password
        reAuthenticateUser(currentUser, newPassword);
        // Add a flash attribute to indicate the success of the update
        redirectAttributes.addFlashAttribute("success", "Password updated successfully.");
        // Redirect to the profile page
        return "redirect:/profile";
    }

    // This method retrieves the current user from the security context.
    private User getCurrentUser() {
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get the username of the current user
        String currentPrincipalName = authentication.getName();
        // Find and return the user with the current username
        return userService.findByUsername(currentPrincipalName);
    }

    // This method re-authenticates the user with the updated username and password.
    private void reAuthenticateUser(User user, String rawPassword) {
        // Load the user details by username
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        // Create a new authentication token with the updated user details and password
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                userDetails, rawPassword, userDetails.getAuthorities());
        // Set the new authentication token in the security context
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
