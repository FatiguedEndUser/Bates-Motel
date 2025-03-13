package dev.besharps.batesmotel.DB.User;

import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    @Column(
            name = "username",
            nullable = false,
            unique = true,
            columnDefinition = "TEXT"
    )
    private String username;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "loyalty_points",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private int loyaltyPoints;

    public User(String username, String password, String email, int loyaltyPoints) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.loyaltyPoints = loyaltyPoints;
    }
}