package ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.student;

import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant.Entrant;
import org.hibernate.annotations.*;
import org.hibernate.type.descriptor.java.BooleanJavaType;


/**
 * The Student class represents an entity of a student who has transitioned from an entrant.
 * It includes details such as the funding type, scholarship status, and corporate email.
 *
 * The class is mapped to the "students" table in the database and uses various JPA and Hibernate annotations for
 * defining relationships, constraints, and filters.
 *
 * Key attributes:
 * - {@link #entrant}: The one-to-one relationship with the {@link Entrant} entity, representing the entrant information.
 * - {@link #fundingType}: The funding type (contract or budget), stored as a string in the database.
 * - {@link #scholarshipStatus}: The scholarship status (ordinary, increased, or none), stored as a string in the database.
 * - {@link #corporateEmail}: The corporate email with a validation pattern ensuring it follows a specific format.
 * - {@link #deleted}: A flag for logical deletion of the student record.
 *
 * Filters:
 * - A filter is applied to allow the exclusion of logically deleted students from queries.
 *
 * Relationships:
 * - One-to-one relationship with the {@link Entrant} entity.
 * - A foreign key constraint is defined for the entrant ID, which sets the foreign key to null on deletion or update.
 *
 * @see Entrant
 * @see FundingType
 * @see ScholarshipStatus
 * @see FundingTypeConverter
 * @see ScholarshipStatusConverter
 *
 * @author Inessa Repeshko CS-222a
 */
@Entity
@Table(name = "students")
@FilterDef(name = "deletedStudentFilter", parameters = @ParamDef(name = "deletedStudentFilter", type = BooleanJavaType.class))
@Filters({
        @Filter(name = "deletedStudentFilter", condition = ":deleted = 0")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "entrant_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (entrant_id) REFERENCES entrant(id) ON DELETE SET NULL ON UPDATE SET NULL"))
    private Entrant entrant;

    @Column(name = "funding_type",
            nullable = false)
    @Enumerated(EnumType.STRING)
    @Convert(converter = FundingTypeConverter.class)
    private FundingType fundingType;

    @Column(name = "scholarship_status",
            nullable = false)
    @Enumerated(EnumType.STRING)
    @Convert(converter = ScholarshipStatusConverter.class)
    @ColumnDefault(value = "'NONE'")
    private ScholarshipStatus scholarshipStatus;

    @Column(name = "corporate_email",
            nullable = false,
            unique = true,
            length = 123)
    @Pattern(regexp = "^[a-z]{1,100}@[a-z]{1,10}\\.khpi\\.edu\\.ua$", message = "The corporate email should match the format 'name.surname@faculty.khpi.edu.ua'")
    private String corporateEmail;

    @Column(name = "deleted", nullable = false)
    @ColumnDefault(value = "0")
    private Boolean deleted = Boolean.FALSE;
}

