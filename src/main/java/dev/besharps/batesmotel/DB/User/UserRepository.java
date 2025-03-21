package dev.besharps.batesmotel.DB.User;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.loyaltyPoints = u.loyaltyPoints + :points WHERE u.id = :id")
    void addPoints(
            @Param("id") Integer id,
            @Param("points") Integer points
    );

    @Query("SELECT u.loyaltyPoints FROM User u where u.id = :id")
    int getLoyaltyPoints(@Param("id") Integer id);



import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}

