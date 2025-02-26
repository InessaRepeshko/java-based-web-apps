package ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Entrant is an entity class representing a record in the "entrants" table.
 * It stores detailed information about an entrant, including personal data and academic rating.
 * The class also includes validation constraints and custom methods for data formatting.
 * <p>
 * Key features:
 * - Validation for fields such as case number, name, surname, patronymic, birthday, gender, and rating score.
 * <p>
 * The class includes constructors for creating Entrant objects and methods to manipulate or validate data fields.
 * It also contains utility methods for converting dates and generating formatted output.
 * <p>
 * Validation rules:
 * - The case number must follow a specific format.
 * - The name, surname, and patronymic must only contain letters from the Ukrainian alphabet.
 * - The birthday must be in the past and the entrant must be between 16 and 110 years old.
 * - The rating score must be between 120.01 and 200.00.
 * <p>
 * This class uses Hibernate annotations for ORM mapping and Jakarta validation for input validation.
 *
 * @author Inessa Repeshko CS-222a
 * @see Table
 * @see Filter
 * @see NotBlank
 * @see Size
 * @see Pattern
 * @see Past
 * @see DecimalMin
 * @see DecimalMax
 * @see FilterDef
 * @see Data
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 */


@Entity
@Table(name = "entrants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrant {
    private static final String REGEX_CASE_NUMBER = "^(?=.{5,20}$)[А-ЩЮЯҐЄІЇ]{1,10}\\d{2}[\\-]\\d{1,4}$";
    private static final String REGEX_NAMES = "^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ \\-][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$";
    private static final DateTimeFormatter dateFormatterUA = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter dateFormatterEN = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Column(name = "id", insertable = false, updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Positive(message = "The id must be a positive number.")

    private Long id;


    @Column(name = "case_number", unique = true, nullable = false, length = 20)
    @Check(constraints = "REGEXP_LIKE(case_number, '^[А-ЩЮЯҐЄІЇ]{1,10}[0-9]{2}-[0-9]{1,4}$', 'c') = 1")
    @Length(min = 5, max = 20, message = "The case number length should be between 5 to 20 characters.")

    @NotBlank(message = "The case number must not be blank.")
    @Pattern(regexp = REGEX_CASE_NUMBER, message = "The case number should be in the format 'LLLLLLLLLLNN-NNNN'.")

    private String caseNumber;


    @Column(name = "surname", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(surname, '" + REGEX_NAMES + "', 'c') = 1")
    @Length(min = 1, max = 50, message = "The surname length should be between 1 to 50 characters.")

    @NotBlank(message = "The surname must not be blank.")
    @Size(min = 1, max = 50, message = "The surname length should be between 1 to 50 characters.")
    @Pattern(regexp = REGEX_NAMES, message = "The surname can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.")

    private String surname;


    @Column(name = "name", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(name, '" + REGEX_NAMES + "', 'c') = 1")
    @Length(min = 1, max = 50, message = "The name length should be between 1 to 50 characters.")

    @NotBlank(message = "The name must not be blank.")
    @Size(min = 1, max = 50, message = "The name length should be between 1 to 50 characters.")
    @Pattern(regexp = REGEX_NAMES, message = "The name can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.")

    private String name;


    @Column(name = "patronymic", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(patronymic, '" + REGEX_NAMES + "', 'c') = 1")
    @Length(min = 1, max = 50, message = "The patronymic length should be between 1 to 50 characters.")

    @NotBlank(message = "The patronymic must not be blank.")
    @Size(min = 1, max = 50, message = "The patronymic length should be between 1 to 50 characters.")
    @Pattern(regexp = REGEX_NAMES, message = "The patronymic can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.")

    private String patronymic;


    @Column(name = "birthday", nullable = false)
    @ColumnDefault(value = "'2000-01-01'")
    @Check(constraints = "birthday BETWEEN '1914-01-01' AND '2008-01-01'")

    @NotNull(message = "The birthday must not be null.")
    @Past(message = "The birthday must be in the past.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    private LocalDate birthday;


    @Column(name = "gender", nullable = false)
    @ColumnDefault(value = "1")

    @NotNull(message = "The gender must not be null.")

    private Boolean gender;


    @Column(name = "rating_score", nullable = false)
    @ColumnDefault(value = "120.001")
    @Check(constraints = "rating_score > 120.000 AND rating_score <= 200.000")

    @NotNull(message = "The rating score must not be null.")
    @DecimalMin(value = "120.000", inclusive = false, message = "The rating score must be greater than 120,000.")
    @DecimalMax(value = "200.000", inclusive = true, message = "The rating score must be less than or equal to 200,000.")
    @Positive(message = "The rating score must be a positive number.")

    @NumberFormat(pattern = "###.###")

    private Double ratingScore;


    @Column(name = "student_id", nullable = true)

    @Positive(message = "The student id must be a positive number.")

    private Long studentId;


    public Entrant(String caseNumber, String surname, String name, String patronymic, String birthday, String gender, String ratingScore) {
        setStringFields(caseNumber, surname, name, patronymic, birthday, gender, ratingScore);
    }

    public Entrant(String id, String caseNumber, String surname, String name, String patronymic, String birthday, String gender, String ratingScore) {
        try {
            setId(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The id should not be empty and should be long.");
        }

        setStringFields(caseNumber, surname, name, patronymic, birthday, gender, ratingScore);
    }

    private void setStringFields(String caseNumber, String surname, String name, String patronymic, String birthday, String gender, String ratingScore) {
        setCaseNumber(caseNumber);
        setSurname(surname);
        setName(name);
        setPatronymic(patronymic);
        setBirthday(birthday);

        if (gender != null && !gender.isBlank()) {
            setGender(Boolean.parseBoolean(gender));
        } else {
            throw new IllegalArgumentException("The gender should not be empty and should be boolean.");
        }

        try {
            setRatingScore(Double.parseDouble(ratingScore));
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("The rating score should not be empty and should be double.");
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = validateCaseNumber(caseNumber);
    }

    public void setSurname(String surname) {
        this.surname = validateName(surname);
    }

    public void setName(String name) {
        this.name = validateName(name);
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic.trim().equals("-") ? patronymic.trim() : validateName(patronymic);
    }

    public void setBirthday(String birthday) {
        this.birthday = validateBirthday(birthday);
    }

    public void setGender(boolean gender) {
        this.gender = Boolean.valueOf(gender);
    }

    public void setRatingScore(double ratingScore) {
        this.ratingScore = validateRatingScore(ratingScore);
    }

    private String validateCaseNumber(String caseNumber) {
        if (caseNumber == null) {
            throw new IllegalArgumentException("The case number cannot be empty.");
        }

        caseNumber = caseNumber.trim();

        if (caseNumber.isEmpty() || !caseNumber.matches(REGEX_CASE_NUMBER)) {
            throw new IllegalArgumentException("The case number should be in the format 'LLLLLLLLLLNN-NNNN'.");
        }

        return caseNumber;
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Parts of the name cannot be empty.");
        }

        name = name.trim();

        String[] words = name.split("\\s+");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            String[] subWords = word.split("-");

            for (String subWord : subWords) {
                if (!subWord.matches("^[А-Ща-щЮюЯяҐґЄєІіЇї](?:[А-Яа-яҐґЄєІіЇїʼ]+)?$")) {
                    throw new IllegalArgumentException("Parts of the name can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.");
                }

                if (!subWord.isEmpty()) {
                    formattedName.append(Character.toUpperCase(subWord.charAt(0)))
                            .append(subWord.substring(1).toLowerCase())
                            .append("-");
                }
            }

            formattedName.setLength(formattedName.length() - 1);
            formattedName.append(" ");
        }

        return formattedName.toString().trim();
    }

    private LocalDate validateBirthday(String birthday) {
        if (birthday == null) {
            throw new IllegalArgumentException("The birthday cannot be empty.");
        }

        LocalDate birthDate;

        try {
            birthDate = LocalDate.parse(birthday, dateFormatterUA);
        } catch (DateTimeParseException e) {
            try {
                birthDate = LocalDate.parse(birthday, dateFormatterEN);
            } catch (DateTimeParseException e1) {
                throw new IllegalArgumentException("The birthday should be in the format 'dd.MM.yyyy' or 'yyyy-MM-dd'.");
            }
        }

        LocalDate now = LocalDate.now();

        if (birthDate.isBefore(now.minusYears(110))
                || birthDate.isAfter(now.minusYears(16))) {
            throw new IllegalArgumentException("Date of birth must be between 16 and 110 years ago.");
        }

        return birthDate;
    }

    private Double validateRatingScore(double ratingScore) {
        if (ratingScore < 120.001 || ratingScore > 200.000) {
            throw new IllegalArgumentException("The rating score should be between 120.01 and 200.00.");
        }

        return ratingScore;
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
