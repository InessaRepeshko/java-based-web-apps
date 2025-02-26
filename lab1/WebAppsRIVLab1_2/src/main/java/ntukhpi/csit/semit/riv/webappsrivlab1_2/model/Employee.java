package ntukhpi.csit.semit.riv.webappsrivlab1_2.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The Employee class represents an employee with attributes such as name, birthday, gender, salary,
 * programming language proficiency, and importance. It provides methods to parse and validate
 * input data, ensuring the integrity of the employee's attributes.
 *
 * <p>This class also maintains a static employee number to assign unique IDs to each employee
 * upon creation. The class handles various input formats for name, birthday, gender, salary,
 * and programming language, applying default values when necessary.
 *
 * @author Inessa Repeshko CS-222a
 */
public class Employee {
    /** Default data for creating class instances */
    private static final String DEFAULT_NAME = "Name Surname"; // name template
    private static final String DEFAULT_BIRTHDAY = "01.01.2006"; // minimum working age - 18 years old
    private static final double DEFAULT_SALARY = 8000.00; // minimum salary in Ukraine
    private static final String DEFAULT_PROGLANG = "JavaScript"; // most popular programming language
    private static final int DEFAULT_IMPORTANCE = 0; // minimum importance for company

    private static Long employeeNum = 0L;
    private Long id;
    private String name;
    private LocalDate birthday;
    private boolean gender;
    private double salary;
    private ProgramLanguages programLanguage;
    private int importance;

    /**
     * Constructs a new Employee instance, validating and setting the attributes.
     *
     * @param id              the id of the employee as a long number.
     *                        If is null, the value employeeNum is used.
     * @param name            the name of the employee, formatted as DEFAULT_NAME.
     *                        If the name is invalid or too short, a value DEFAULT_NAME is used.
     * @param birthday        the employee's birthdate in the format "dd.MM.yyyy".
     *                        If the format is invalid, a date of DEFAULT_BIRTHDAY is used.
     * @param gender          the gender of the employee, which must be either true (male) or false (female).
     * @param salary          the employee's salary, which must be a positive double number.
     *                        If the salary is invalid, a value of DEFAULT_SALARY is used.
     * @param programLanguage the employee's primary programming language as a string.
     *                        This value is matched with the ProgramLanguages enum.
     * @param importance      the employee's importance for company as 0 (low), 1 (medium) or 2 (high).
     *                        If the importance is invalid, a value of DEFAULT_IMPORTANCE is used.
     */
    public Employee(
            long id,
            String name,
            String birthday,
            boolean gender,
            double salary,
            String programLanguage,
            int importance
    ) {
        employeeNum++;
        this.id = (id != -1) ? id : employeeNum;
        this.name = parseNameOrDefault(name);
        this.birthday = parseBirthdayOrDefault(birthday);
        this.gender = gender;
        this.salary = parseSalaryOrDefault(salary);
        this.programLanguage = parseProgLangOrDefault(programLanguage);
        this.importance = parseImportanceOrDefault(importance);
    }

    /**
     * Parses the employee's name and ensures it is correctly formatted.
     *
     * @param name the name of the employee
     * @return the formatted name with the first letter of each word capitalized, or
     * DEFAULT_NAME if the input name is invalid.
     */
    private String parseNameOrDefault(String name) {
        name = name.length() > 5 ? name : DEFAULT_NAME;

        return Arrays.stream(name.split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    /**
     * Parses the employee's birthday from a string and validates its format.
     *
     * @param birthday the birthdate string in the format "dd.MM.yyyy"
     * @return the parsed LocalDate object, or a default date of "01.01.2006"
     * (18 years is a minimum working age in Ukraine) if parsing fails.
     */
    private LocalDate parseBirthdayOrDefault(String birthday) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            return LocalDate.parse(birthday, formatter);
        } catch (DateTimeParseException e) {
            return LocalDate.parse(DEFAULT_BIRTHDAY, formatter);
        }
    }

    /**
     * Validates the employee's salary.
     *
     * @param salary the salary amount
     * @return the salary if it is greater than 0, or DEFAULT_SALARY (minimum salary in Ukraine) as the default.
     */
    private double parseSalaryOrDefault(double salary) {
        return salary > 0 ? salary : DEFAULT_SALARY;
    }

    /**
     * Parses the given programming language string and returns the corresponding ProgramLanguages enum value.
     *
     * <p>If the provided program language does not match any enum values, the method defaults to the first enum
     * value in the ProgramLanguages array (usually the default value). The method uses the
     * {@link ProgramLanguages#getEnumIndex(String)} to find the appropriate index based on the display name of the language.
     *
     * @param programLanguage the programming language as a string
     * @return the corresponding ProgramLanguages enum value, or the first enum value if the input is invalid.
     */
    private ProgramLanguages parseProgLangOrDefault(String programLanguage) {
        int index = ProgramLanguages.getEnumIndex(programLanguage);
        index = index > -1 ? index : 0;

        return ProgramLanguages.values()[index];
    }

    /**
     * Parses the importance level of the employee and returns a valid default value.
     *
     * @param importance the importance level as an integer
     * @return the validated importance level, either 0 (low), 1 (medium), 2 (high), or DEFAULT_IMPORTANCE 0 (low).
     */
    private int parseImportanceOrDefault(int importance) {
        return (importance == 0 || importance == 1 || importance == 2)
                ? importance
                : DEFAULT_IMPORTANCE;
    }

    /**
     * Retrieves the employee's id.
     *
     * @return the employee's id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the employee's name.
     *
     * @return the employee's name as a string.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the employee's gender.
     *
     * @return the employee's gender as a boolean.
     */
    public boolean getGender() {
        return gender;
    }

    /**
     * Retrieves the employee's gender.
     *
     * @return the employee's gender as a string.
     */
    public String getGenderAsString() {
        return getGender() ? "male" : "female";
    }

    /**
     * Retrieves the employee's birthday as a string in the format "dd.MM.yyyy".
     *
     * @return the formatted birthday string.
     */
    public String getBirthdayAsString() {
        return birthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    /**
     * Retrieves the employee's salary formatted as currency.
     *
     * @return the salary formatted as a string with two decimal places and "UAH".
     */
    public String getSalary() {
        return String.format("%.2f", salary);
    }


    /**
     * Retrieves the employee's primary programming language.
     *
     * @return the programming language as a string.
     */
    public String getProgramLanguage() {
        return programLanguage.getDisplayName();
    }

    /**
     * Retrieves the importance level of the employee.
     *
     * @return the importance level as an integer.
     */
    public int getImportance() {
        return importance;
    }

    /**
     * Retrieves the importance level of the employee.
     *
     * @return the importance level as a string.
     */
    public String getImportanceAsString() {
        switch (getImportance()) {
            case 1:
                return "medium";
            case 2:
                return "high";
            default:
                return "low";
        }
    }

    /**
     * Constructs a string representation of the employee object in the form of URL parameters.
     *
     * <p>This method returns a string that contains key-value pairs of the employee's attributes,
     * formatted as if they were passed as URL query parameters. The string includes the employee's
     * ID, name, birthday, gender, salary, programming language, and importance level.
     *
     * @return a string representing the employee's attributes formatted as URL parameters.
     */
    public String getEmployeeAsParameters() {
        StringBuilder parameters = new StringBuilder();

        parameters.append("Employee {");
        parameters.append("id=").append(getId());
        parameters.append(", name=").append(getName());
        parameters.append(", birthday=").append(getBirthdayAsString());
        parameters.append(", gender=").append(getGender());
        parameters.append(", salary=").append(getSalary());
        parameters.append(", programLanguage=").append(getProgramLanguage());
        parameters.append(", importance=").append(getImportance());
        parameters.append(" }");

        return parameters.toString();
    }

    /**
     * Parses a string representation in the form of URL parameters to an Employee object.
     *
     * <p>This method returns an Employee object with its data
     * (id, name, birthday, gender, salary, programming language, and importance level)
     * parsed from string that contains key-value pairs of the employee's attributes,
     * formatted as if they were passed as URL query parameters.
     *
     * @return an Employee object parsed form the employee's attributes formatted as URL parameters.
     */
    public static Employee parseEmployeeFromParameters(String parameters) {
        Matcher idMatcher = Pattern.compile("id=(\\d+)").matcher(parameters),
                nameMatcher = Pattern.compile("name='([a-zA-Z]+)(\\s+[a-zA-Z]+)*'").matcher(parameters),
                birthdayMatcher = Pattern.compile("birthday=(\\d{2}.\\d{2}.\\d{4})").matcher(parameters),
                genderMatcher = Pattern.compile("gender=(true|false)").matcher(parameters),
                salaryMatcher = Pattern.compile("salary=(\\d+\\.\\d+)").matcher(parameters),
                programLanguageMatcher = Pattern.compile("programLanguage=([a-zA-Z]+)(\\s+[a-zA-Z]+)*").matcher(parameters),
                importanceMatcher = Pattern.compile("importance=(\\d+)").matcher(parameters);

        long id = idMatcher.find()
                ? Long.parseLong(idMatcher.group(1))
                : -1;
        String name = nameMatcher.find()
                ? nameMatcher.group(1)
                : DEFAULT_NAME;
        String birthday = birthdayMatcher.find()
                ? birthdayMatcher.group(1)
                : DEFAULT_BIRTHDAY;
        boolean gender = genderMatcher.find()
                ? Boolean.parseBoolean(genderMatcher.group(1))
                : false;
        double salary = salaryMatcher.find()
                ? Double.parseDouble(salaryMatcher.group(1))
                : DEFAULT_SALARY;
        String programLanguage = programLanguageMatcher.find()
                ? programLanguageMatcher.group(1)
                : DEFAULT_PROGLANG;
        int importance = importanceMatcher.find()
                ? Integer.parseInt(importanceMatcher.group(1))
                : DEFAULT_IMPORTANCE;

        return new Employee(id, name, birthday, gender, salary, programLanguage, importance);
    }
}
