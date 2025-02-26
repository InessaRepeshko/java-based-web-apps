package ntukhpi.csit.semit.riv.webappsrivlab4.validator.validator;

import jakarta.validation.constraints.Pattern;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidCorporateEmail;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidName;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant.ValidCaseNumber;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidPassword;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.user.ValidUsername;

import java.time.format.DateTimeFormatter;

/**
 * This class contains various constants used for validation purposes.
 * These constants include regular expressions for validating case numbers, names, gender, email addresses, usernames, and passwords.
 * It also defines date formatters used for parsing dates in Ukrainian and English formats.
 * <p>
 * Key constants:
 * - **REGEX_CASE_NUMBER**: Regular expression for validating case numbers in the format `LLLLLLLLLLNN-NNNN`.
 * - **REGEX_NAMES**: Regular expression for validating names (first name, surname, and patronymic) with support for Ukrainian characters, spaces, and dashes.
 * - **DATE_FORMATTER_UA**: Date formatter for parsing dates in the format `dd.MM.yyyy` (Ukrainian date format).
 * - **DATE_FORMATTER_EN**: Date formatter for parsing dates in the format `yyyy-MM-dd` (English date format).
 * - **REGEX_GENDER**: Regular expression for validating gender input, allowing values such as `1`, `0`, `true`, `false`, and their variants.
 * - **REGEX_CORPORATE_EMAIL**: Regular expression for validating corporate email addresses in the format `name.surname@faculty.khpi.edu.ua`.
 * - **REGEX_USERNAME**: Regular expression for validating usernames, allowing Latin letters, digits, and special characters such as `.`, `_`, and `-`.
 * - **REGEX_PASSWORD**: Regular expression for validating passwords, ensuring the inclusion of uppercase letters, digits, and special characters.
 * <p>
 * These constants are utilized in validation annotations to enforce consistent and precise validation rules across the application.
 *
 * @author Inessa Repeshko CS-222a
 * @see DateTimeFormatter
 * @see Pattern
 * @see ValidCaseNumber
 * @see ValidName
 * @see ValidCorporateEmail
 * @see ValidUsername
 * @see ValidPassword
 */

public final class ValidationConstants {
    public static final String REGEX_CASE_NUMBER = "^(?=.{5,20}$)[А-ЩЮЯҐЄІЇ]{1,10}\\d{2}[\\-]\\d{1,4}$";
    public static final String REGEX_NAMES = "^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ \\-][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$";
    public static final DateTimeFormatter DATE_FORMATTER_UA = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter DATE_FORMATTER_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String REGEX_GENDER = "^(1|0|true|false|TRUE|FALSE|True|False)$";
    public static final String REGEX_CORPORATE_EMAIL = "^(?=.{17,123}$)[a-z](\\.?[a-z]+){1,99}@[a-z]{1,10}\\.khpi\\.edu\\.ua$";
    public static final String REGEX_USERNAME = "^(?=.{8,32}$)[a-zA-Z0-9._-]+$";
    public static final String REGEX_PASSWORD = "^(?=.{8,50}$)(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\\\[\\\\]{};\\':\\\\\"|,.<>/?~])[A-Za-z0-9!@#$%^&*()_+\\-=\\\\[\\\\]{};\\':\\\\\"|,.<>/?~]*$";
}
