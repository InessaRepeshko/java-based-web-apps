package ntukhpi.csit.semit.riv.webappsrivlab4.service.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.common.ValidId;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant.ValidBirthday;
import ntukhpi.csit.semit.riv.webappsrivlab4.validator.entrant.ValidRatingScore;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing operations on `Entrant` entities.
 * This interface defines methods for CRUD operations, filtering, sorting, and managing
 * relationships with other entities, such as `Student`.
 * <p>
 * Key functionalities:
 * - Fetch all entrants or a filtered and sorted list based on criteria.
 * - Save single or multiple entrants with validation.
 * - Update existing entrants or delete them by ID.
 * - Find entrants without associated students or locate specific entrants by example or ID.
 * - Retrieve the associated student ID for a specific entrant.
 * <p>
 * Validation:
 * - Enforces input validation using annotations like `@Valid`, `@ValidId`, `@ValidBirthday`,
 * and `@ValidRatingScore`.
 * - Throws appropriate exceptions for invalid input or business rule violations.
 * <p>
 * Dependencies:
 * - Annotated with `@Validated` to enable method-level validation.
 * - Relies on custom validators for domain-specific constraints.
 *
 * @author Inessa Repeshko CS-222a
 * @see Entrant
 * @see ValidId
 * @see ValidBirthday
 * @see ValidRatingScore
 * @see Validated
 */

@Validated
public interface EntrantService {
    List<Entrant> getAllEntrants();

    List<Entrant> getFilteredAndSortedEntrants(String search,
                                               @ValidBirthday LocalDate birthdayStart,
                                               @ValidBirthday LocalDate birthdayEnd,
                                               @ValidRatingScore Double ratingScoreMin,
                                               @ValidRatingScore Double ratingScoreMax,
                                               String sort);

    List<Entrant> findEntrantsWithoutStudents();

    void saveAllEntrants(@NotEmpty(message = "The entrant list must not be null.") List<@Valid Entrant> entrants);

    Entrant findEntrantById(@ValidId Long id);

    Entrant findEntrantByExample(@Valid Entrant entrant);

    Long findStudentIdForEntrant(@ValidId Long entrantId);

    Entrant saveEntrant(@Valid Entrant entrant);

    Entrant updateEntrant(@Valid Entrant entrant);

    void deleteEntrantById(@ValidId Long id);
}
