package dev.besharps.batesmotel.DB.User;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "User")
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    @Id
    @SequenceGenerator(
            name = "user",
            sequenceName = "user",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            columnDefinition = "TEXT"
    )
    private int UserName;

    @Column(
            name = "password",
            updatable = true,
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String Password;

    @Column(
            name = "email",
            updatable = true,
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String Email;

    @Column(
            name = "loyalty points",
            updatable = true,
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private int LoyaltyPoints;
}
