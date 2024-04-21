package dmacc.edu.services;

import dmacc.edu.model.Player;
import dmacc.edu.model.User;
import dmacc.edu.repository.UserRepository;
import dmacc.edu.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterNewUser() {
        String username = "john.doe";
        String password = "password";
        String name = "John Doe";
        String email = "john.doe@example.com";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);
        user.setRole("ROLE_PLAYER");

        Player player = new Player();
        player.setName(name);
        user.setPlayer(player);

        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerNewUser(username, password, name, email, passwordEncoder);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals("encodedPassword", result.getPassword()); // Password should be encoded
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals("ROLE_PLAYER", result.getRole());
        assertNotNull(result.getPlayer());
        assertEquals(name, result.getPlayer().getName());
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        String username = "john.doe";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "john.doe";

        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(username);
        });
    }

    @Test
    public void testGetCurrentUser() {
        String username = "john.doe";
        User user = new User();
        user.setUsername(username);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        User currentUser = userService.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals(username, currentUser.getUsername());
    }

    @Test
    public void testFindByUsername() {
        String username = "john.doe";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        User result = userService.findByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("john.doe");

        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUser(user);

        verify(userRepository, times(1)).save(user);
    }

   
}

