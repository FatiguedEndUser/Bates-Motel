package dev.besharps.batesmotel.RepositoryTests;

import dev.besharps.batesmotel.BatesMotelApplication;
import dev.besharps.batesmotel.DB.User.User;
import dev.besharps.batesmotel.DB.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BatesMotelApplication.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DirtiesContext
    public void saveUserTest() {
        User user = new User("Jake", "12345", "jake@gmail.com", 100);
        userRepository.createUser(user);
        assertNotNull(user.getId());

        user.setUsername("Bob");
        userRepository.updateUser(user);
        assertEquals("Bob", user.getUsername());
    }

    @Test
    @DirtiesContext
    public void loyaltyPointsTest() {
        User user = new User("Jake", "12345", "jake@gmail.com", 0);


        user = userRepository.createUser(user);
        user = userRepository.addPoints(user, 10);

        assertEquals(10, user.getLoyaltyPoints());

        user = userRepository.addPoints(user, 50);
        assertEquals(60, user.getLoyaltyPoints());

    }

}