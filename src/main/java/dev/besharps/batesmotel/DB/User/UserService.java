package dev.besharps.batesmotel.DB.User;

import dev.besharps.batesmotel.DB.UserType.UserType;
import dev.besharps.batesmotel.DB.UserType.UserTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    Logger logger = Logger.getLogger(UserService.class.getName());

    @Transactional
    public User updateUser(User user, UserDetails myDetails) {

        if (myDetails.email() != null) {
            user.setEmail(myDetails.email());
        }
        if (myDetails.password() != null) {
            user.setPassword(myDetails.password());
        }
        if (myDetails.username() != null) {
            user.setUsername(myDetails.username());
        }

        return userRepository.save(user);
    }

    @Transactional
    public void createUser(User user, int typeNumber) {
        String userRole = switch (typeNumber) {
            case 1 -> "Customer";
            case 2 -> "Maintenance";
            case 3 -> "Clerk";
            case 4 -> "Manager";
            case 5 -> "Admin";
            default -> throw new IllegalStateException("Unexpected value: " + typeNumber);
        };
        UserType userType = UserType.builder()
                .typeName(userRole)
                .build();
        userTypeRepository.save(userType);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType(userType);
        userRepository.save(user);

        logger.info("Created user successfully");
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

}
