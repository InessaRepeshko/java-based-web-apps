package ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant;

import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.*;
import org.hibernate.type.descriptor.java.BooleanJavaType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Entrant is an entity class representing a record in the "entrants" table.
 * It stores detailed information about an entrant, including personal data and academic rating.
 * The class also includes validation constraints and custom methods for data formatting.
 *
 * Key features:
 * - Validation for fields such as case number, name, surname, patronymic, birthday, gender, and rating score.
 * - Logical deletion using the `isDeleted` field.
 * - Filters for database queries, such as filtering out logically deleted entrants.
 *
 * The class includes constructors for creating Entrant objects and methods to manipulate or validate data fields.
 * It also contains utility methods for converting dates and generating formatted output.
 *
 * Validation rules:
 * - The case number must follow a specific format.
 * - The name, surname, and patronymic must only contain letters from the Ukrainian alphabet.
 * - The birthday must be in the past and the entrant must be between 16 and 110 years old.
 * - The rating score must be between 120.01 and 200.00.
 *
 * This class uses Hibernate annotations for ORM mapping and Jakarta validation for input validation.
 *
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
 *
 * @author Inessa Repeshko CS-222a
 */

@Entity
@Table(name = "entrants")
@FilterDef(name = "deletedEntrantFilter", parameters = @ParamDef(name = "isDeleted", type = BooleanJavaType.class))
@Filters({
        @Filter(name = "deletedEntrantFilter", condition = "is_deleted = :isDeleted")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrant {
    private static final String REGEX_CASE_NUMBER = "^(?=.{5,20}$)[А-ЩЮЯҐЄІЇ]{1,10}\\d{2}[\\-]\\d{1,4}$";
    private static final String REGEX_NAMES = "^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ \\-][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "case_number", nullable = false, unique = true, length = 20)
    @Check(constraints = "REGEXP_LIKE(case_number, '^[А-ЩЮЯҐЄІЇ]{1,10}[0-9]{2}-[0-9]{1,4}$', 'c') = 1")
    @NotBlank(message = "The case number must not be blank.")
    @Pattern(regexp = REGEX_CASE_NUMBER, message = "The case number should be in the format 'LLLLLLLLLLNN-NNNN'.")
    private String caseNumber;


    @Column(name = "surname", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(surname, '" + REGEX_NAMES + "', 'c') = 1")
    @NotBlank(message = "The surname must not be blank.")
    @Size(min = 1, max = 50, message = "The surname size should be between 1 to 50.")
    @Pattern(regexp = REGEX_NAMES, message = "The surname can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.")
    private String surname;


    @Column(name = "name", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(name, '" + REGEX_NAMES + "', 'c') = 1")
    @NotBlank(message = "The name must not be blank.")
    @Size(min = 1, max = 50, message = "The name size should be between 1 to 50.")
    @Pattern(regexp = REGEX_NAMES, message = "The name can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.")
    private String name;


    @Column(name = "patronymic", nullable = false, length = 50)
    @Check(constraints = "REGEXP_LIKE(patronymic, '" + REGEX_NAMES + "', 'c') = 1")
    @NotBlank(message = "The patronymic must not be blank.")
    @Size(min = 1, max = 50, message = "The patronymic size should be between 1 to 50.")
    @Pattern(regexp = REGEX_NAMES, message = "The patronymic can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.")
    private String patronymic;


    @Column(name = "birthday", nullable = false)
    @Check(constraints = "birthday BETWEEN '1914-01-01' AND '2008-01-01'")
    @NotNull(message = "The birthday must not be null.")
    @Past(message = "The birthday must be in the past.")
    private LocalDate birthday;


    @Column(name = "gender", nullable = false)
    @ColumnDefault(value = "1")
    @NotNull(message = "The gender must not be null.")
    private Boolean gender = Boolean.FALSE;


    @Column(name = "rating_score", nullable = false)
    @Check(constraints = "rating_score > 120.00 AND rating_score <= 200.00")
    @NotNull(message = "The rating score must not be null.")
    @DecimalMin(value = "120.001", inclusive = true)
    @DecimalMax(value = "200.000", inclusive = true)
    private Double ratingScore;


    @Column(name = "is_deleted", nullable = false)
    @ColumnDefault(value = "0")
    @NotNull(message = "The deleted state must not be null.")
    private Boolean isDeleted = Boolean.FALSE;


    public Entrant(String caseNumber, String surname, String name, String patronymic, String birthday, boolean gender, double ratingScore) {
        setCaseNumber(caseNumber);
        setSurname(surname);
        setName(name);
        setPatronymic(patronymic);
        setBirthday(birthday);
        setGender(gender);
        setRatingScore(ratingScore);
    }

    public Entrant(String id, String caseNumber, String surname, String name, String patronymic, String birthday, String gender, String ratingScore) {
        try {
            setId(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The id should not be empty and should be long.");
        }

        setStringFields(caseNumber, surname, name, patronymic, birthday, gender, ratingScore);
    }

    public Entrant(String caseNumber, String surname, String name, String patronymic, String birthday, String gender, String ratingScore) {
        setStringFields(caseNumber, surname, name, patronymic, birthday, gender, ratingScore);
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

    private void setStringFields(String caseNumber, String surname, String name, String patronymic, String birthday, String gender, String ratingScore) {
        setCaseNumber(caseNumber);
        setSurname(surname);
        setName(name);
        setPatronymic(patronymic);
        setBirthday(changeDateFormatFromDefaulttoUA(birthday));

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
            birthDate = LocalDate.parse(birthday, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("The birthday should be in the format 'dd.MM.yyyy'.");
        }

        int age = Period.between(birthDate, LocalDate.now()).getYears();

        if (age < 16 || age > 110) {
            throw new IllegalArgumentException("The age should be between 16 and 110 years old.");
        }

        return birthDate;
    }

    private Double validateRatingScore(double ratingScore) {
        if (ratingScore < 120.001 || ratingScore > 200.000) {
            throw new IllegalArgumentException("The rating score should be between 120.01 and 200.00.");
        }

        return ratingScore;
    }

    private static String changeDateFormatFromDefaulttoUA(String inputDate) {
        if (inputDate == null) {
            throw new IllegalArgumentException("The birthday cannot be empty.");
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        return date.format(outputFormatter);
    }

    public String getGenderAsString() {
        return getGender() ? "male" : "female";
    }

    public String getBirthdayAsUAString() {
        return getBirthday().format(DateTimeFormatter.ofPattern("dd MMM yyyy", new Locale("uk")));
    }

    public String getBirthdayAsUADate() {
        return getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getBirthdayAsDefaultDate() {
        return getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getRatingScoreAsString() {
        return String.format("%.3f", getRatingScore());
    }

    public Map<String, Object> getAllFields() {
        Map<String, Object> fields = new HashMap<>();
        Field[] declaredFields = this.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                field.setAccessible(true);

                try {
                    fields.put(field.getName(), field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return fields;
    }

    public Map<String, Object> getUniqueFields() {
        Map<String, Object> fields = new HashMap<>();

        for (Map.Entry<String, Object> entry : getAllFields().entrySet()) {
            if (entry.getKey().equals("id")
                    || entry.getKey().equals("caseNumber")
                    || entry.getKey().equals("isDeleted")) {
                fields.put(entry.getKey(), entry.getValue());
            }
        }

        return fields;
    }

    public List<String[]> getFieldNamesAsFormattedString() {
        List<String[]> fieldNames = new ArrayList<>();
        fieldNames.add(new String[]{"caseNumber", "Case Number"});
        fieldNames.add(new String[]{"surname", "Surname"});
        fieldNames.add(new String[]{"name", "Name"});
        fieldNames.add(new String[]{"patronymic", "Patronymic"});
        fieldNames.add(new String[]{"birthday", "Birthday"});
        fieldNames.add(new String[]{"gender", "Gender"});
        fieldNames.add(new String[]{"ratingScore", "Rating Score"});

        return fieldNames.stream().toList();
    }
}
