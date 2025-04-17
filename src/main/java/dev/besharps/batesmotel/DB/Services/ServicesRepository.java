package dev.besharps.batesmotel.DB.Services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer> {

    List<Services> findByServiceCategory(String category);

    List<Services> findByAvailable(boolean available);

    @Query("SELECT s FROM Services s WHERE " +
            "(:category IS NULL OR s.serviceCategory = :category) AND " +
            "(:maxPrice IS NULL OR s.serviceCharge <= :maxPrice) AND " +
            "(:keyword IS NULL OR " +
            "LOWER(s.serviceName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Services> searchServices(
            @Param("category") String category,
            @Param("maxPrice") Double maxPrice,
            @Param("keyword") String keyword);
}