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

    @GetMapping
    public String showProfilePage(Model model) {
        User user = getCurrentUser();
        List<Booking> appointments = bookingService.findBookingsByCustomerEmail(user.getEmail());
        model.addAttribute("user", user);
        model.addAttribute("appointments", appointments);
        return "profile";
    }

    @GetMapping("/update/{attribute}")
    public String showUpdateForm(@PathVariable("attribute") String attribute, Model model) {
        User user = getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("updateAttribute", attribute);
        return "updateProfile";
    }

    @PostMapping("/update/username")
    public String updateUsername(@RequestParam("username") String newUsername, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        currentUser.setUsername(newUsername);
        userService.updateUser(currentUser);
        reAuthenticateUser(currentUser, currentUser.getPassword());
        redirectAttributes.addFlashAttribute("success", "Username updated successfully.");
        return "redirect:/profile";
    }

    @PostMapping("/update/name")
    public String updateName(@RequestParam("name") String newName, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        currentUser.setName(newName);
        userService.updateUser(currentUser);
        redirectAttributes.addFlashAttribute("success", "Name updated successfully.");
        return "redirect:/profile";
    }

    @PostMapping("/update/email")
    public String updateEmail(@RequestParam("email") String newEmail, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        currentUser.setEmail(newEmail);
        userService.updateUser(currentUser);
        redirectAttributes.addFlashAttribute("success", "Email updated successfully.");
        return "redirect:/profile";
    }

    @PostMapping("/update/password")
    public String updatePassword(@RequestParam String newPassword, @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
        if (!StringUtils.equals(newPassword, confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/profile/update/password";
        }
        User currentUser = getCurrentUser();
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        currentUser.setPassword(encodedNewPassword);
        userService.updateUser(currentUser);
        reAuthenticateUser(currentUser, newPassword);
        redirectAttributes.addFlashAttribute("success", "Password updated successfully.");
        return "redirect:/profile";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userService.findByUsername(currentPrincipalName);
    }

    private void reAuthenticateUser(User user, String rawPassword) {
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                userDetails, rawPassword, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
