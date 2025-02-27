package dev.besharps.batesmotel;

/*------------------------------------------IMPORTS---------------------------------*/
import dev.besharps.batesmotel.DB.Student;
import dev.besharps.batesmotel.DB.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
/*----------------------------------------------------------------------------------*/

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BatesMotelApplication {
    private static final Logger log = LoggerFactory.getLogger(BatesMotelApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BatesMotelApplication.class, args);
        log.info("Application started succesfully");
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student maria = new Student(
                    "Maria",
                    "Jones",
                    "maria.jones@gmail.com",
                    21
            );
            studentRepository.save(maria);
        };

    }
}
