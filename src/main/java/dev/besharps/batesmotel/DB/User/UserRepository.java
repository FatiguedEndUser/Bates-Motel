package dev.besharps.batesmotel.DB.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public User addPoints(User user, int points) {
        User managedUser = entityManager.find(User.class, user.getId());
        if (managedUser != null) {
            int loyaltyPoints = managedUser.getLoyaltyPoints();
            managedUser.setLoyaltyPoints(loyaltyPoints + points);
        }
        return managedUser;
    }

    public void updateUser(User user) {
        if (user.getId() == null) {
            throw new Error("User ID null");
        }
        User managedUser = entityManager.find(User.class, user.getId());
        if (managedUser == null) {
            throw new Error("entityManager unable to find user with Id");
        }
        managedUser.setUsername(user.getUsername());
        managedUser.setPassword(user.getPassword());
        managedUser.setEmail(user.getEmail());
        managedUser.setLoyaltyPoints(user.getLoyaltyPoints());
        //entityManager.merge(user);
        //return managedUser;
    }

    public User createUser(User user) {
        if (user.getId() != null) {
            throw new Error("User has already been created");
        }
        entityManager.persist(user);
        logger.info("Created new user with the Id {} and username of {}", user.getId(), user.getUsername());
        return user;
    }
}