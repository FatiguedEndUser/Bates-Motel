package dev.besharps.batesmotel;

/*------------------------------------------IMPORTS---------------------------------*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


/*----------------------------------------------------------------------------------*/
// In your main application class (BatesMotelApplication.java)
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BatesMotelApplication {
    //@Bean
    //public PasswordEncoder encoder() {
    //    return new BCryptPasswordEncoder();
    //}

    public static void main(String[] args) {
        SpringApplication.run(BatesMotelApplication.class, args);
    }
}
