package dmacc.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dmacc.edu.model.Player;
import dmacc.edu.model.User;
import dmacc.edu.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(String username, String password, String name, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEmail(email);
        user.setRole("ROLE_PLAYER"); // Set the default role to "ROLE_PLAYER"

        Player player = new Player();
        player.setName(name);
        user.setPlayer(player);

        return userRepository.save(user);
    }

}
