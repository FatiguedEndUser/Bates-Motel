package dev.besharps.batesmotel.RepositoryTests;

import dev.besharps.batesmotel.BatesMotelApplication;
import dev.besharps.batesmotel.DB.User.User;
import dev.besharps.batesmotel.DB.User.UserRepository;
import dev.besharps.batesmotel.DB.User.UserService;
import dev.besharps.batesmotel.DB.UserType.UserType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BatesMotelApplication.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void saveUserTest() {
        User user = userRepository.findById(1).orElse(null);
        assertNotNull(user);

        //userService.createUser(user);
        user.setUsername("Bob");
        userService.updateUser(user);
        assertEquals("Bob", user.getUsername());
        UserType userType = user.getUserType();
        logger.info("Got userType {}", userType.getTypeName());
    }

    @Test
    public void createUserTest() {
        User newUser = User.builder()
                .username("Maxwell")
                .password("max1234")
                .email("maxwell@gmail.com")
                .loyaltyPoints(5)
                .build();

        assertNotNull(newUser);

        userService.createUser(newUser, 1);

    }

    @Test
    public void findUserEmailTest() {
        logger.info("Got user email {}", userRepository.findByEmail(1));
    }

    @Test
    public void loyaltyPointsTest() {
        User user = userRepository.findById(1).orElse(null);
        assertNotNull(user);

        userRepository.addPoints(user.getId(), 10);
        int currentPoints = userRepository.getLoyaltyPoints(user.getId());
        assertEquals(110, currentPoints);

        userRepository.addPoints(user.getId(), 50);
        currentPoints = userRepository.getLoyaltyPoints(user.getId());
        assertEquals(160, currentPoints);

    }

}