package dev.besharps.batesmotel.DB.User;

import dev.besharps.batesmotel.DB.UserType.UserType;
import jakarta.persistence.*;
import lombok.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity(name = "User") // Logical name of the entity
@Table(name = "Users") // Physical table name in the database
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @Column(
            name = "loyalty_points",
            nullable = true,
            columnDefinition = "INTEGER"
    )
    private int loyaltyPoints;

    @OneToOne(
            fetch = FetchType.EAGER)
    @JoinColumn(name = "usertype_id",
            referencedColumnName = "userTypeId"
    )
    private UserType userType;
}