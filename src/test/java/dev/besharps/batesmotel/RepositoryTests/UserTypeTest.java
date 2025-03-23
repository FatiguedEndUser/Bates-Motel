package dev.besharps.batesmotel.RepositoryTests;

import dev.besharps.batesmotel.BatesMotelApplication;
import dev.besharps.batesmotel.DB.UserType.UserType;
import dev.besharps.batesmotel.DB.UserType.UserTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BatesMotelApplication.class)
public class UserTypeTest {

    private final Logger logger = LoggerFactory.getLogger(UserTypeTest.class);

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Test
    void getUserTypeByTypeName() {
        for (UserType userType : userTypeRepository.getUserTypeByTypeName("Admin")) {
            logger.info(userType.getTypeName());
            logger.info(userType.getUser().getUsername());
        }
    }
}
