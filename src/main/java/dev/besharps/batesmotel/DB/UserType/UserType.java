package dev.besharps.batesmotel.DB.UserType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.besharps.batesmotel.DB.User.User;
import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity(name = "UserType")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userTypeId", nullable = false, unique = true)
    private int userTypeId;

    @OneToOne(mappedBy = "userType")
    @JsonBackReference
    private User user;

    @Column(name="typeName", nullable = false)
    private String typeName;

}