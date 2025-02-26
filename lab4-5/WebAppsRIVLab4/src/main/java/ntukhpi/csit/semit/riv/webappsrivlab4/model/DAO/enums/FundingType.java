package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Enum class representing the funding type for students.
 * This enum defines two funding types:
 * - CONTRACT: Represents students on a contract basis ("контракт").
 * - BUDGET: Represents students funded by the government budget ("бюджет").
 * <p>
 * Key features:
 * - `getValue()`: Provides the Ukrainian human-readable value of the funding type.
 * - `getValues()`: Returns a list of all funding types available in the system.
 * - `fromValue(String value)`: Converts a string to the corresponding `FundingType` constant.
 * Throws an `IllegalArgumentException` if the value does not match any funding type.
 * <p>
 * Lombok's `@Getter` annotation is used to generate the getter for the `value` field automatically.
 * This enum ensures consistent handling of funding types throughout the application.
 *
 * @author Inessa Repeshko CS-222a
 * @see ScholarshipStatus
 * @see Enum
 * @see List
 * @see Arrays
 * @see IllegalArgumentException
 */

@Getter
public enum FundingType {
    CONTRACT("контракт"),
    BUDGET("бюджет");

    private final String value;

    FundingType(String value) {
        this.value = value;
    }

    public static List<FundingType> getValues() {
        return Arrays.stream(FundingType.values()).toList();
    }

    public static FundingType fromValue(String value) {
        for (FundingType type : FundingType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown FundingType value: " + value);
    }
}

