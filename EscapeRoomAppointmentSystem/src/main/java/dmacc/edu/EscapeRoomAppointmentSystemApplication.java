package dmacc.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import dmacc.edu.config.SecurityConfig;

@SpringBootApplication
@Import({SecurityConfig.class})
public class EscapeRoomAppointmentSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EscapeRoomAppointmentSystemApplication.class, args);
    }
}