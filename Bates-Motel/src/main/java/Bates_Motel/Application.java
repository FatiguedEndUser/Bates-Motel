package Bates_Motel;

/*------------------------------------------IMPORTS---------------------------------*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
/*----------------------------------------------------------------------------------*/

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("Application started succesfully");
		
	}
}