package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.student;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.FundingType;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.FundingTypeConverter;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.ScholarshipStatus;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.ScholarshipStatusConverter;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.student.ValidFundingType;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.student.ValidScholarshipStatus;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.ValidationConstants;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidCorporateEmail;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import java.util.Arrays;
import java.util.List;

/**
 * Entity class representing a student in the system.
 * This class is mapped to the `students` table in the database and provides attributes
 * and validation for managing student-related data.
 * <p>
 * Key attributes:
 * - `id`: The primary key for the student entity.
 * - `entrant`: A one-to-one relationship with the `Entrant` entity, with cascading behavior
 * for refresh and removal operations. Ensures data consistency through foreign key constraints.
 * - `fundingType`: Indicates the funding type (e.g., CONTRACT or BUDGET) with validation and
 * persistence using the `FundingTypeConverter`.
 * - `scholarshipStatus`: Specifies the scholarship status (e.g., ENHANCED, ORDINARY, or NONE),
 * with validation and persistence using the `ScholarshipStatusConverter`.
 * - `corporateEmail`: The student's unique corporate email address, validated against
 * a specific format and length constraints.
 * <p>
 * Additional features:
 * - Custom constructor for initializing a `Student` object with string-based attributes.
 * - Validation annotations to ensure data integrity at both application and database levels.
 * - Utility method `getFieldNamesAsFormattedStrings` provides metadata for UI display purposes.
 * <p>
 * Relationships, constraints, and validations ensure seamless integration with Hibernate
 * and Spring Validation frameworks, maintaining robust and consistent data handling.
 *
 * @author Inessa Repeshko CS-222a
 */


@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(targetEntity = Entrant.class, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "entrant_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_student_entrant",
                    foreignKeyDefinition = "FOREIGN KEY (entrant_id) REFERENCES entrants(id) ON DELETE CASCADE ON UPDATE CASCADE"))
    @Valid
    private Entrant entrant;

    @Column(name = "funding_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @Convert(converter = FundingTypeConverter.class)
    @ColumnDefault(value = "'CONTRACT'")
    @ValidFundingType
    private FundingType fundingType;

    @Column(name = "scholarship_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Convert(converter = ScholarshipStatusConverter.class)
    @ColumnDefault(value = "'NONE'")
    @ValidScholarshipStatus
    private ScholarshipStatus scholarshipStatus;

    @Column(name = "corporate_email", nullable = false, unique = true, length = 123)
    @Check(constraints = "REGEXP_LIKE(corporate_email, '" + ValidationConstants.REGEX_CORPORATE_EMAIL + "', 'c') = 1")
    @Length(min = 17, max = 123, message = "The corporate email length should be between 17 to 123 characters.")
    @org.hibernate.validator.constraints.Email(regexp = ValidationConstants.REGEX_CORPORATE_EMAIL,
            message = "The corporate email should match the format 'name.surname@faculty.khpi.edu.ua'.")
    @ValidCorporateEmail
    private String corporateEmail;

    public Student(Entrant entrant,
                   String fundingType,
                   String scholarshipStatus,
                   String corporateEmail) {
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

