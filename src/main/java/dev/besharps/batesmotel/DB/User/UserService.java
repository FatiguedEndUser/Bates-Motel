package dev.besharps.batesmotel.DB.User;

import dev.besharps.batesmotel.DB.UserType.UserType;
import dev.besharps.batesmotel.DB.UserType.UserTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Transactional
    public void createUser(User user, String Type) {
        UserType userType = UserType.builder()
                .typeName(Type)
                .build();
        userTypeRepository.save(userType);
        user.setUserType(userType);
        userRepository.save(user);

        logger.info("Created user successfully");
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

}
