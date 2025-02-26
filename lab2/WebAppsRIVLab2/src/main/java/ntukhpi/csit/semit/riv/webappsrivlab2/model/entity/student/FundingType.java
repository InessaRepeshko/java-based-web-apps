package ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.student;

/**
 * Enum FundingType represents the type of funding for a student.
 * It can either be "contract" (контракт) or "budget" (бюджет).
 *
 * This enum provides a mapping between the enum constants and their corresponding Ukrainian string values.
 * It also includes a method to retrieve an enum constant based on the string value.
 *
 * Available types:
 * - CONTRACT: Students who are funded on a contract basis.
 * - BUDGET: Students who are funded by the state.
 *
 * Key methods:
 * - {@link #getValue()} returns the Ukrainian string representation of the funding type.
 * - {@link #fromValue(String)} retrieves the corresponding enum constant based on the provided string value.
 *
 * @author Inessa Repeshko CS-222a
 */
public enum FundingType {
    CONTRACT("контракт"),
    BUDGET("бюджет");

    private final String value;

    FundingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
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

