package ntukhpi.csit.semit.riv.webappsrivlab3.model.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * ScholarshipStatusConverter is a JPA attribute converter that converts between the {@link ScholarshipStatus} enum
 * and its string representation in the database.
 *
 * This converter automatically applies to any entity field of type {@link ScholarshipStatus} and ensures
 * that the enum is stored in the database as a string, and converted back to the enum when reading
 * from the database.
 *
 * Key methods:
 * - {@link #convertToDatabaseColumn(ScholarshipStatus)}: Converts a {@link ScholarshipStatus} enum to its string value for storage in the database.
 * - {@link #convertToEntityAttribute(String)}: Converts a string value from the database back to the corresponding {@link ScholarshipStatus} enum.
 *
 * This converter is marked with the {@link Converter} annotation and `autoApply=true`, which means it will automatically apply
 * to all entity fields of type {@link ScholarshipStatus}.
 *
 * @see ScholarshipStatus
 * @see AttributeConverter
 *
 * @author Inessa Repeshko CS-222a
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

