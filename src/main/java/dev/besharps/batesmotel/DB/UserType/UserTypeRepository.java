package dev.besharps.batesmotel.DB.UserType;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    List<UserType> getUserTypeByTypeName(String typeName);
}
