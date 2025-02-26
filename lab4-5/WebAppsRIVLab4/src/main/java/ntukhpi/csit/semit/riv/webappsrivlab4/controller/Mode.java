package ntukhpi.csit.semit.riv.webappsrivlab4.controller;

import lombok.Getter;

/**
 * Enum class representing the various operation modes for controllers and views.
 * This class is used to define specific modes that dictate the behavior of UI elements and logic flow
 * in different parts of the application.
 * <p>
 * Modes:
 * - ADD: Represents the mode for creating a new entity.
 * - VIEW_FORM: Represents the mode for viewing detailed information about an individual entity.
 * - VIEW_TABLE: Represents the mode for viewing a table or list of entities.
 * - EDIT: Represents the mode for modifying an existing entity.
 * - DELETE: Represents the mode for deleting an entity.
 * <p>
 * Each mode has a descriptive string value that is accessible using Lombok's generated `getValue()` method,
 * which allows for seamless integration with templates and logging mechanisms.
 * <p>
 * Lombok's `@Getter` annotation is used to automatically generate a getter for the `value` field.
 *
 * @author Inessa Repeshko CS-222a
 * @see lombok.Getter
 * @see Enum
 * @see String
 */

@Getter
public enum Mode {
    ADD("Add"),
    VIEW_FORM("View"),
    VIEW_TABLE("View"),
    EDIT("Edit"),
    DELETE("Delete");

    private final String value;

    Mode(String value) {
        this.value = value;
    }
}
