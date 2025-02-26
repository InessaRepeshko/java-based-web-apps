package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum class representing roles in the system.
 * This enum defines user roles and their associated human-readable values in Ukrainian.
 * <p>
 * Roles:
 * - ADMIN: Represents the administrator role ("Адміністратор").
 * - ENTRANT_MANAGER: Represents a manager responsible for entrants ("Менеджер абітурієнтів").
 * - STUDENT_MANAGER: Represents a manager responsible for students ("Менеджер студентів").
 * - ENTRANT_VIEWER: Represents a viewer with access to entrant data ("Переглядач абітурієнтів").
 * - STUDENT_VIEWER: Represents a viewer with access to student data ("Переглядач студентів").
 * <p>
 * Key functionalities:
 * - `getFormattedName()`: Converts the enum name into a human-readable format (e.g., "Entrant Manager").
 * - `getAllRoles()`: Returns a list of all `Role` constants.
 * - `getAllFormattedNames()`: Returns a list of all formatted role names.
 * - `getAllValues()`: Returns a list of all role values in Ukrainian.
 * - `fromValue(String value)`: Converts a string value to the corresponding `Role` constant.
 * - `fromName(String name)`: Converts a string name to the corresponding `Role` constant.
 * <p>
 * Lombok's `@Getter` annotation is used to automatically generate getters for the `value` field.
 * This enum ensures consistent handling of user roles across the application.
 *
 * @author Inessa Repeshko CS-222a
 * @see RoleConverter
 * @see java.util.List
 * @see java.util.stream.Collectors
 */


@Getter
public enum Role {
    ADMIN("Адміністратор"),
    ENTRANT_MANAGER("Менеджер абітурієнтів"),
    STUDENT_MANAGER("Менеджер студентів"),
    ENTRANT_VIEWER("Переглядач абітурієнтів"),
    STUDENT_VIEWER("Переглядач студентів");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getFormattedName() {
        return Arrays.stream(this.name().split("_"))
                .map(word -> word.charAt(0) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public static List<Role> getAllRoles() {
        return Arrays.stream(Role.values()).toList();
    }

    public static List<String> getAllFormattedNames() {
        return Arrays.stream(Role.values()).map(Role::getFormattedName).toList();
    }

    public static List<String> getAllValues() {
        return Arrays.stream(Role.values()).map(Role::getValue).toList();
    }

    public static Role fromValue(String value) {
        for (Role role : Role.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }

        throw new IllegalArgumentException("Unknown Role value: " + value);
    }

    public static Role fromName(String name) {
        for (Role role : Role.values()) {
            if (role.name().equals(name)) {
                return role;
            }
        }

        throw new IllegalArgumentException("Unknown Role name: " + name);
    }
}

