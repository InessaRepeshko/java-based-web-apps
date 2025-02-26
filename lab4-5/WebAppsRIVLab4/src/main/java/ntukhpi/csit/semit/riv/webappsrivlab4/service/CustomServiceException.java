package ntukhpi.csit.semit.riv.webappsrivlab4.service;

/**
 * Custom exception class for handling service layer errors.
 * This class extends `RuntimeException` and is designed to provide meaningful error
 * messages and encapsulate underlying causes of exceptions occurring in the service layer.
 * <p>
 * Key functionalities:
 * - Allows instantiation with a custom error message.
 * - Supports wrapping of a cause (`Throwable`) alongside the error message.
 * <p>
 * Usage:
 * - Throw this exception in service methods to indicate errors specific to business logic.
 * - Provides consistent error reporting across service implementations.
 * <p>
 * Dependencies:
 * - Extends `RuntimeException` for unchecked exception handling.
 * - Accepts `String` as a message and `Throwable` as an optional cause.
 *
 * @author Inessa Repeshko CS-222a
 * @see RuntimeException
 * @see String
 * @see Throwable
 */

public class CustomServiceException extends RuntimeException {
    public CustomServiceException(String message) {
        super(message);
    }

    public CustomServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
