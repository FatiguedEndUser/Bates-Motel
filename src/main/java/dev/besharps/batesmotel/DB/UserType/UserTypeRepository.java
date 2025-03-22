package dev.besharps.batesmotel.DB.UserType;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    List<UserType> getUserTypeByTypeName(String typeName);
}
