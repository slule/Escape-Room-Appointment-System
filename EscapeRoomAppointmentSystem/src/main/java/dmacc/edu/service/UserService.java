package dmacc.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dmacc.edu.model.Player;
import dmacc.edu.model.User;
import dmacc.edu.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Method to register a new user
    public User registerNewUser(String username, String password, String name, String email, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEmail(email);
        user.setRole("ROLE_PLAYER");

        Player player = new Player();
        player.setName(name);
        user.setPlayer(player);

        return userRepository.save(user);
    }

    // Method to load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
    
    // Method to get the currently logged in user
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return findByUsername(currentPrincipalName);
    }

    // Method to find a user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Method to update a user
    public void updateUser(User user) {
        userRepository.save(user);
    }
}