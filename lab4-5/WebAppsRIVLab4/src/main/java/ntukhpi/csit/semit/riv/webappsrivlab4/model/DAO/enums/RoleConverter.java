package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA AttributeConverter for the `Role` enum.
 * This class provides the logic for converting between the `Role` enum and its
 * corresponding database representation (a `String`).
 *
 * Key functionalities:
 * - `convertToDatabaseColumn(Role role)`: Converts a `Role` enum value to its
 *   corresponding `String` representation for storage in the database.
 * - `convertToEntityAttribute(String dbData)`: Converts a `String` value from the database back to
 *   the corresponding `Role` enum constant. Throws an `IllegalArgumentException` if the
 *   string value does not match any defined role.
 *
 * This converter is automatically applied to all `Role` fields in entities due to
 * the `@Converter(autoApply = true)` annotation.
 *
 * Ensures that the `Role` enum is seamlessly persisted and retrieved in JPA entities,
 * maintaining data integrity and simplifying application logic.
 *
 * @author Inessa Repeshko CS-222a
 * @see Role
 * @see jakarta.persistence.AttributeConverter
 */


@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }

        return role.getValue();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        for (Role role : Role.values()) {
            if (role.getValue().equals(dbData)) {
                return role;
            }
        }

        throw new IllegalArgumentException("Unknown Role value: " + dbData);
    }
}

