package ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.student;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * FundingTypeConverter is a JPA attribute converter that converts between the {@link FundingType} enum
 * and its string representation in the database.
 *
 * This converter automatically applies to any entity field of type {@link FundingType} and ensures
 * that the enum is stored in the database as a string, and converted back to the enum when reading
 * from the database.
 *
 * Key methods:
 * - {@link #convertToDatabaseColumn(FundingType)}: Converts a {@link FundingType} enum to its string value for storage in the database.
 * - {@link #convertToEntityAttribute(String)}: Converts a string value from the database back to the corresponding {@link FundingType} enum.
 *
 * This converter is marked with {@link Converter} annotation and `autoApply=true`, which means it will automatically apply
 * to all entity fields of type {@link FundingType}.
 *
 * @see FundingType
 * @see AttributeConverter
 *
 * @author Inessa Repeshko CS-222a
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

