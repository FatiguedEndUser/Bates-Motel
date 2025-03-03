package dev.besharps.batesmotel.DB.Staff;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity(name = "Staff")
@Table(name = "Staff")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Staff {
    @Id
    @SequenceGenerator(
            name = "staff_sequence",
            sequenceName = "staff_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "staff_sequence"
    )
    @Column(
            name = "staffId",
            updatable = false
    )
    private String id;

    @Column(
            name = "firstName",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "lastName",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "jobTitle",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String jobTitle;

    @Column(
            name = "startDate",
            nullable = false,
            columnDefinition = "DATE"
    )
    private Date startDate;

    @Column(
            name = "endDate",
            columnDefinition = "DATE"
    )
    private Date endDate;

}
