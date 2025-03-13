package dev.besharps.batesmotel.DB.UserType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "userTypeId"
    )
    private Long id;

    private String typeName;
}