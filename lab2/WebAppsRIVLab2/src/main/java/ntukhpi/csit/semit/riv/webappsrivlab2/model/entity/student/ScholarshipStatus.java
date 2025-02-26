package ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.student;

/**
 * Enum ScholarshipStatus represents the scholarship status of a student.
 * It can be one of the following:
 * - ORDINARY: Represents a student receiving a standard scholarship ("звичайна").
 * - INCREASED: Represents a student receiving an increased scholarship ("підвищена").
 * - NONE: Represents a student who does not receive a scholarship ("відсутня").
 *
 * This enum provides a mapping between the enum constants and their corresponding Ukrainian string values.
 * It also includes a method to retrieve an enum constant based on the string value.
 *
 * Key methods:
 * - {@link #getValue()} returns the Ukrainian string representation of the scholarship status.
 * - {@link #fromValue(String)} retrieves the corresponding enum constant based on the provided string value.
 *
 * @author Inessa Repeshko CS-222a
 */
public enum ScholarshipStatus {
    ORDINARY("звичайна"),
    INCREASED("підвищена"),
    NONE("відсутня");

    private final String value;

    ScholarshipStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
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

