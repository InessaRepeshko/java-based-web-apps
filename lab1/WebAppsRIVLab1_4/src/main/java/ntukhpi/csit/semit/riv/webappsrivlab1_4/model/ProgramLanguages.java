package ntukhpi.csit.semit.riv.webappsrivlab1_4.model;

/**
 * The ProgramLanguages enum represents a set of common programming languages, each associated with
 * a display name. This enum provides methods to retrieve the display name of each language and
 * determine the index of a language based on its display name.
 *
 * <p>Each constant in the enum corresponds to a programming language, and the display name is the
 * human-readable name of the language.
 *
 * @author Inessa Repeshko CS-222a
 */
public enum ProgramLanguages {
    javascript("JavaScript"),
    typescript("TypeScript"),
    python("Python"),
    java("Java"),
    csharp("C#"),
    php("PHP");

    private final String displayName;

    /**
     * Constructor for the ProgramLanguages enum.
     *
     * @param displayName the human-readable name of the programming language.
     */
    ProgramLanguages(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retrieves the display name of the programming language.
     *
     * @return the display name of the language as a string.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Finds the index of the programming language in the enum based on its display name.
     *
     * <p>If the given value matches the display name of a programming language, the method
     * returns its index in the enum; otherwise, it returns -1 if no match is found.
     *
     * @param value the display name of the programming language to search for.
     * @return the index of the programming language in the enum, or -1 if not found.
     */
    public static int getEnumIndex(String value) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getDisplayName().equals(value)) {
                return i;
            }
        }

        return -1;
    }
}
