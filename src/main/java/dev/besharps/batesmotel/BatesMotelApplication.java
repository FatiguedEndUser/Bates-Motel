package dev.besharps.batesmotel;

/*------------------------------------------IMPORTS---------------------------------*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*----------------------------------------------------------------------------------*/
// In your main application class (BatesMotelApplication.java)
@SpringBootApplication
@EntityScan(basePackages = "dev.besharps.batesmotel.DB") // For entities
@EnableJpaRepositories(basePackages = "dev.besharps.batesmotel.DB") // For repositories
public class BatesMotelApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatesMotelApplication.class, args);
    }
}
