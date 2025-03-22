package dev.besharps.batesmotel.DB.UserType;

import dev.besharps.batesmotel.DB.User.User;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "UserType")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "userTypeId",
            nullable = false,
            unique = true
    )

    private int id;

    @OneToOne(
            mappedBy = "userType"
    )
    private User user;

    @Column(
            name="typeName",
            nullable = false
    )
    private String typeName;

}