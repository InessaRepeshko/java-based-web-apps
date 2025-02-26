package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA AttributeConverter for the `FundingType` enum.
 * This class provides the logic for converting between the `FundingType` enum and its
 * corresponding database representation (a `String`).
 * <p>
 * Key functionalities:
 * - `convertToDatabaseColumn(FundingType fundingType)`: Converts a `FundingType` enum value to its
 * corresponding `String` representation for storage in the database.
 * - `convertToEntityAttribute(String dbData)`: Converts a `String` value from the database back to
 * the corresponding `FundingType` enum constant. Throws an `IllegalArgumentException` if the
 * string value does not match any valid funding type.
 * <p>
 * This converter is automatically applied to all `FundingType` fields in entities due to
 * the `@Converter(autoApply = true)` annotation.
 * <p>
 * Ensures that the `FundingType` enum is seamlessly persisted and retrieved in JPA entities,
 * maintaining data integrity and simplifying application logic.
 *
 * @author Inessa Repeshko CS-222a
 * @see FundingType
 * @see jakarta.persistence.AttributeConverter
 * @see IllegalArgumentException
 */

@Converter(autoApply = true)
public class FundingTypeConverter implements AttributeConverter<FundingType, String> {

    @Override
    public String convertToDatabaseColumn(FundingType fundingType) {
        if (fundingType == null) {
            return null;
        }

        return fundingType.getValue();
    }

    @Override
    public FundingType convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        for (FundingType type : FundingType.values()) {
            if (type.getValue().equals(dbData)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown FundingType value: " + dbData);
    }
}

