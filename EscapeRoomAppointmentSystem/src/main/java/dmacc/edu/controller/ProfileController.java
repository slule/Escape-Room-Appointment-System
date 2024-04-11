package dmacc.edu.controller;

import dmacc.edu.model.User;
import dmacc.edu.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showProfilePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findByUsername(currentPrincipalName);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update")
    public String updateUserProfile(@ModelAttribute("user") User updatedUser,
                                    @RequestParam(required = false) String newPassword,
                                    @RequestParam(required = false) String confirmPassword,
                                    RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        
        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setName(updatedUser.getName());
        currentUser.setEmail(updatedUser.getEmail());

        if (!StringUtils.isBlank(newPassword) && !StringUtils.isBlank(confirmPassword)) {
            if (newPassword.equals(confirmPassword)) {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
            } else {
                redirectAttributes.addFlashAttribute("error", "The passwords do not match.");
                return "redirect:/profile";
            }
        }

        userService.updateUser(currentUser);
        return "redirect:/profile";
    }

    @PostMapping("/book")
    public String bookRoom() {
        return "redirect:/booking";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userService.findByUsername(currentPrincipalName);
    }
}
