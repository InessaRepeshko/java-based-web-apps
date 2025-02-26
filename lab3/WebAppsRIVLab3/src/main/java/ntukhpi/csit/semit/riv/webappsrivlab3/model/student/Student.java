package ntukhpi.csit.semit.riv.webappsrivlab3.model.student;

import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.enums.FundingType;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.enums.FundingTypeConverter;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.enums.ScholarshipStatus;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.enums.ScholarshipStatusConverter;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.*;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant.Entrant;

import java.util.Arrays;
import java.util.List;


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
 * - {@link #scholarshipStatus}: The scholarship status (ordinary, enhanced, or none), stored as a string in the database.
 * - {@link #corporateEmail}: The corporate email with a validation pattern ensuring it follows a specific format.
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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private static final String REGEX_EMAIL = "^[a-z](\\.?[a-z]+){1,99}@[a-z]{1,10}\\.khpi\\.edu\\.ua$";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    @Positive(message = "The id must be a positive number.")

    private Long id;


//    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "entrant_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_student_entrant",
                    foreignKeyDefinition = "FOREIGN KEY (entrant_id) REFERENCES entrants(id) ON DELETE CASCADE ON UPDATE CASCADE"))

    private Entrant entrant;


    @Column(name = "funding_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @Convert(converter = FundingTypeConverter.class)
    @ColumnDefault(value = "'CONTRACT'")

    @NotNull(message = "Funding type must not be null.")

    private FundingType fundingType;


    @Column(name = "scholarship_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Convert(converter = ScholarshipStatusConverter.class)
    @ColumnDefault(value = "'NONE'")

    @NotNull(message = "Scholarship status must not be null.")

    private ScholarshipStatus scholarshipStatus;


    @Column(name = "corporate_email", nullable = false, unique = true, length = 123)
    @Check(constraints = "REGEXP_LIKE(corporate_email, '" + REGEX_EMAIL + "', 'c') = 1")
    @Length(min = 17, max = 123, message = "The corporate email length should be between 1 to 50 characters.")
    @org.hibernate.validator.constraints.Email(regexp = REGEX_EMAIL, message = "The corporate email should match the format 'name.surname@faculty.khpi.edu.ua'.")

    @NotBlank(message = "The email must not be blank.")
    @Size(min = 17, max = 123, message = "The corporate email length should be between 1 to 50 characters.")
    @Pattern(regexp = REGEX_EMAIL, message = "The corporate email should match the format 'name.surname@faculty.khpi.edu.ua'.")
    @Email(regexp = REGEX_EMAIL, message = "The corporate email should match the format 'name.surname@faculty.khpi.edu.ua'.")

    private String corporateEmail;


    public Student(Entrant entrant, String fundingType, String scholarshipStatus, String corporateEmail) {
        setEntrant(entrant);
        setFundingType(FundingType.fromValue(fundingType));
        setScholarshipStatus(ScholarshipStatus.fromValue(scholarshipStatus));
        setCorporateEmail(corporateEmail);
    }

    public static String[][] getFieldNamesAsFormattedStrings() {
        List<String[]> fieldNames = Arrays.asList(
                new String[]{"fullName", "Full Name"},
                new String[]{"fundingType", "Funding Type"},
                new String[]{"scholarshipStatus", "Scholarship Status"},
                new String[]{"corporateEmail", "Corporate Email"}
        );

        return fieldNames.toArray(String[][]::new);
    }
}

