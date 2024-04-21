/**  
* @Author Shalom Lule - slule@dmacc.edu  
* CIS175 <Spring 2024>
* Apr 21, 2024 
*/ 
package dmacc.edu;

import dmacc.edu.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    public void testUserAuthorities() {
        String role = "ROLE_USER";
        User user = new User();
        user.setRole(role);

        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) user.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(role)));
    }
}
