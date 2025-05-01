package dev.besharps.batesmotel.DB.Staff;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)

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
            nullable = true,
            columnDefinition = "DATE"
    )
    private Date endDate;

    @Column(
            name = "imageUrl",
            columnDefinition = "TEXT"
    )
    private String imageUrl;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name = "contactEmail",
            columnDefinition = "TEXT"
    )
    private String contactEmail;

    @Column(
            name = "contactPhone",
            columnDefinition = "TEXT"
    )
    private String contactPhone;

    @Column(
            name = "department",
            columnDefinition = "TEXT"
    )
    private String department;
}