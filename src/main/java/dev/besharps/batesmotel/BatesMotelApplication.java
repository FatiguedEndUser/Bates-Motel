package dev.besharps.batesmotel;

/*------------------------------------------IMPORTS---------------------------------*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
/*----------------------------------------------------------------------------------*/

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BatesMotelApplication {
    private static final Logger log = LoggerFactory.getLogger(BatesMotelApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BatesMotelApplication.class, args);
        log.info("Application started succesfully");

    }
}
