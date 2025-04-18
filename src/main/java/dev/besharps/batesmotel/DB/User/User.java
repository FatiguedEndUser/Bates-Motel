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

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "loyalty_points", nullable = false)
    private int loyaltyPoints;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usertype_id", referencedColumnName = "userTypeId")
    private UserType userType;
}