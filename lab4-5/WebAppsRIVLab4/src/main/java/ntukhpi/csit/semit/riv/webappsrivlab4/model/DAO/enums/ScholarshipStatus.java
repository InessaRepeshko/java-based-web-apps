package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Enum class representing the scholarship status for students.
 * This enum defines the possible scholarship statuses in the system:
 * - ENHANCED: Represents an enhanced scholarship ("підвищена").
 * - ORDINARY: Represents an ordinary scholarship ("звичайна").
 * - NONE: Indicates the absence of a scholarship ("відсутня").
 * <p>
 * Key functionalities:
 * - `getValues()`: Returns a list of all scholarship status constants.
 * - `fromValue(String value)`: Converts a string value to the corresponding `ScholarshipStatus` constant.
 * Throws an `IllegalArgumentException` if the value does not match any defined status.
 * <p>
 * Lombok's `@Getter` annotation is used to automatically generate a getter for the `value` field.
 * This enum ensures consistent handling of scholarship statuses across the application.
 *
 * @author Inessa Repeshko CS-222a
 * @see FundingType
 * @see Enum
 * @see List
 * @see Arrays
 * @see IllegalArgumentException
 */

@Getter
public enum ScholarshipStatus {
    ENHANCED("підвищена"),
    ORDINARY("звичайна"),
    NONE("відсутня");

    private final String value;

    ScholarshipStatus(String value) {
        this.value = value;
    }

    public static List<ScholarshipStatus> getValues() {
        return Arrays.stream(ScholarshipStatus.values()).toList();
    }

    public static ScholarshipStatus fromValue(String value) {
        for (ScholarshipStatus status : ScholarshipStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown ScholarshipStatus value: " + value);
    }
}

