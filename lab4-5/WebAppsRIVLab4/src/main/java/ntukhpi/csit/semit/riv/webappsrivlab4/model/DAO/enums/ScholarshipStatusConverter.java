package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA AttributeConverter for the `ScholarshipStatus` enum.
 * This class provides the logic for converting between the `ScholarshipStatus` enum
 * and its corresponding database representation (a `String`).
 * <p>
 * Key functionalities:
 * - `convertToDatabaseColumn(ScholarshipStatus scholarshipStatus)`: Converts a `ScholarshipStatus`
 * enum value to its corresponding `String` representation for storage in the database.
 * - `convertToEntityAttribute(String dbData)`: Converts a `String` value from the database back to
 * the corresponding `ScholarshipStatus` enum constant. Throws an `IllegalArgumentException` if the
 * string value does not match any defined scholarship status.
 * <p>
 * This converter is automatically applied to all `ScholarshipStatus` fields in entities due to
 * the `@Converter(autoApply = true)` annotation.
 * <p>
 * Ensures that the `ScholarshipStatus` enum is seamlessly persisted and retrieved in JPA entities,
 * maintaining data integrity and simplifying application logic.
 *
 * @author Inessa Repeshko CS-222a
 * @see ScholarshipStatus
 * @see jakarta.persistence.AttributeConverter
 * @see IllegalArgumentException
 */

@Converter(autoApply = true)
public class ScholarshipStatusConverter implements AttributeConverter<ScholarshipStatus, String> {

    @Override
    public String convertToDatabaseColumn(ScholarshipStatus scholarshipStatus) {
        if (scholarshipStatus == null) {
            return null;
        }

        return scholarshipStatus.getValue();
    }

    @Override
    public ScholarshipStatus convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        for (ScholarshipStatus status : ScholarshipStatus.values()) {
            if (status.getValue().equals(dbData)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown ScholarshipStatus value: " + dbData);
    }
}

