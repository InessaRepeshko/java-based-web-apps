package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidName;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidPatronymic;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidSurname;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator.ValidationConstants;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant.ValidBirthday;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant.ValidCaseNumber;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant.ValidGender;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant.ValidRatingScore;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Entity class representing an entrant in the system.
 * This class is mapped to the `entrants` table in the database and provides
 * attributes and validation for managing entrant-related data.
 * <p>
 * Key attributes:
 * - `id`: The primary key for the entrant entity.
 * - `caseNumber`: A unique identifier for each entrant, validated with specific regex patterns.
 * - `surname`, `name`, `patronymic`: Personal details of the entrant with length and pattern validations.
 * - `birthday`: The date of birth, constrained to a specific range (1914-01-01 to 2008-01-01).
 * - `gender`: A boolean field representing the gender of the entrant.
 * - `ratingScore`: The entrant's rating score, constrained to be within the range of 120.000 to 200.000.
 * - `studentId`: Optional field linking the entrant to a student entity.
 * <p>
 * Additional features:
 * - Custom constructors for initializing entrant data with string values.
 * - Validation annotations ensure data integrity at the application and database levels.
 * - Utility methods, such as `getFieldNamesAsFormattedStrings`, provide metadata for UI or other purposes.
 * <p>
 * This entity integrates with Hibernate and Spring Validation to ensure robust data handling.
 *
 * @author Inessa Repeshko CS-222a
 * @see LocalDate
 * @see Arrays
 * @see List
 * @see ValidationConstants
 */

@Entity
@Table(name = "entrants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrant {
    @Column(name = "id", insertable = false, updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "The id must be a positive number.")
    private Long id;

    @Column(name = "case_number", unique = true, nullable = false, length = 20)
    @Check(constraints = "REGEXP_LIKE(case_number, '^[А-ЩЮЯҐЄІЇ]{1,10}[0-9]{2}-[0-9]{1,4}$', 'c') = 1")
    @Length(min = 5, max = 20, message = "The case number length should be between 5 to 20 characters.")
    @ValidCaseNumber
    private String caseNumber;

    @Column(name = "surname", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(surname, '" + ValidationConstants.REGEX_NAMES + "', 'c') = 1")
    @Length(min = 1, max = 50, message = "The surname length should be between 1 to 50 characters.")
    @ValidSurname
    private String surname;

    @Column(name = "name", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(name, '" + ValidationConstants.REGEX_NAMES + "', 'c') = 1")
    @Length(min = 1, max = 50, message = "The name length should be between 1 to 50 characters.")
    @ValidName
    private String name;

    @Column(name = "patronymic", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(patronymic, '" + ValidationConstants.REGEX_NAMES + "', 'c') = 1")
    @Length(min = 1, max = 50, message = "The patronymic length should be between 1 to 50 characters.")
    @ValidPatronymic
    private String patronymic;

    @Column(name = "birthday", nullable = false)
    @ColumnDefault(value = "'2000-01-01'")
    @Check(constraints = "birthday BETWEEN '1914-01-01' AND '2008-01-01'")
    @NotNull(message = "The birthday must not be null.")
    @ValidBirthday
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    @Column(name = "gender", nullable = false)
    @ColumnDefault(value = "1")
    @ValidGender
    private Boolean gender;

    @Column(name = "rating_score", nullable = false)
    @ColumnDefault(value = "120.001")
    @Check(constraints = "rating_score > 120.000 AND rating_score <= 200.000")
    @NotNull(message = "The rating score must not be null.")
    @ValidRatingScore
    @NumberFormat(pattern = "###.###")
    private Double ratingScore;

    @Column(name = "student_id", nullable = true)
    @Positive(message = "The student id must be a positive number.")
    private Long studentId;

    public Entrant(@NotNull String caseNumber,
                   @NotNull String surname,
                   @NotNull String name,
                   @NotNull String patronymic,
                   @NotNull String birthday,
                   @NotNull String gender,
                   @NotNull String ratingScore) {
        setFieldsFromStringValues(caseNumber, surname, name, patronymic, birthday, gender, ratingScore);
    }

    public Entrant(@NotNull String id,
                   @NotNull String caseNumber,
                   @NotNull String surname,
                   @NotNull String name,
                   @NotNull String patronymic,
                   @NotNull String birthday,
                   @NotNull String gender,
                   @NotNull String ratingScore) {
        try {
            setId(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The id must be a numeric value greater than 0.");
        }

        setFieldsFromStringValues(caseNumber, surname, name, patronymic, birthday, gender, ratingScore);
    }

    private void setFieldsFromStringValues(@NotNull String caseNumber,
                                           @NotNull String surname,
                                           @NotNull String name,
                                           @NotNull String patronymic,
                                           @NotNull String birthday,
                                           @NotNull String gender,
                                           @NotNull String ratingScore) {
        setCaseNumber(caseNumber);
        setSurname(surname);
        setName(name);
        setPatronymic(patronymic);

        try {
            setBirthday(LocalDate.parse(birthday, ValidationConstants.DATE_FORMATTER_EN));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("The birthday should be in the format 'yyyy-MM-dd'.");
        }

        if (gender.trim().matches(Pattern.compile(ValidationConstants.REGEX_GENDER).pattern())) {
            setGender(Boolean.parseBoolean(gender));
        } else {
            throw new IllegalArgumentException("The gender must be boolean value in numeric or string format");
        }

        try {
            setRatingScore(Double.parseDouble(ratingScore));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The rating score must be a numeric value.");
        }
    }

    public static String[][] getFieldNamesAsFormattedStrings() {
        List<String[]> fieldNames = Arrays.asList(
                new String[]{"caseNumber", "Case Number"},
                new String[]{"fullName", "Full Name"},
                new String[]{"birthday", "Birthday"},
                new String[]{"gender", "Gender"},
                new String[]{"ratingScore", "Rating Score"}
        );

        return fieldNames.toArray(String[][]::new);
    }
}
