package ntukhpi.csit.semit.riv.webappsrivlab4.repository;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.student.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing `Entrant` entities in the database.
 * Provides CRUD operations and custom queries for handling entrants and their relationships with students.
 * <p>
 * Key functionalities:
 * - Extends `JpaRepository` to provide basic CRUD operations.
 * - Implements custom methods for filtering, sorting, and managing entrants.
 * <p>
 * Key methods:
 * - `getEntrantsBySurnameAndNameAndPatronymic`: Finds entrants by their full name.
 * - `findFilteredAndSortedEntrants`: Retrieves a filtered and sorted list of entrants based on search criteria and date or score ranges.
 * - `findAllWithoutStudent`: Fetches all entrants who are not associated with any students.
 * - `findStudentIdByEntrantId`: Retrieves the student ID associated with a specific entrant.
 * <p>
 * Relationships:
 * - Directly interacts with the `Entrant` entity.
 * - Associated with the `Student` entity to manage entrant-student relationships.
 * <p>
 * Usage:
 * - Designed to be used within the service layer for managing entrant-related workflows.
 * - Facilitates efficient querying and manipulation of entrant data.
 *
 * @author Inessa Repeshko CS-222a
 * @see Entrant
 * @see Student
 * @see JpaRepository
 * @see Sort
 */

@Repository
public interface EntrantRepository extends JpaRepository<Entrant, Long> {
    List<Entrant> getEntrantsBySurnameAndNameAndPatronymic(String surname, String name, String patronymic);

    @Query("SELECT e FROM Entrant e "
            + "WHERE (:search IS NULL OR UPPER(e.caseNumber) LIKE UPPER(CONCAT(:search, '%')) "
            + "OR UPPER(CONCAT(e.surname, ' ', e.name, ' ', e.patronymic)) LIKE UPPER(CONCAT(:search, '%'))) "
            + "AND (:birthdayStart IS NULL OR e.birthday >= :birthdayStart) "
            + "AND (:birthdayEnd IS NULL OR e.birthday <= :birthdayEnd) "
            + "AND (:ratingScoreMin IS NULL OR e.ratingScore >= :ratingScoreMin) "
            + "AND (:ratingScoreMax IS NULL OR e.ratingScore <= :ratingScoreMax) ")
    List<Entrant> findFilteredAndSortedEntrants(@Param("search") String search,
                                                @Param("birthdayStart") LocalDate birthdayStart,
                                                @Param("birthdayEnd") LocalDate birthdayEnd,
                                                @Param("ratingScoreMin") Double ratingScoreMin,
                                                @Param("ratingScoreMax") Double ratingScoreMax,
                                                Sort sortBy);

    @Query("SELECT e FROM Entrant e WHERE e.id NOT IN (SELECT s.entrant.id FROM Student s WHERE s.entrant IS NOT NULL)")
    List<Entrant> findAllWithoutStudent(Sort sort);

    @Query("SELECT s.id FROM Student s WHERE s.entrant.id = :entrantId")
    Optional<Long> findStudentIdByEntrantId(@Param("entrantId") Long entrantId);
}
