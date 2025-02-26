package ntukhpi.csit.semit.riv.webappsrivlab4.repository;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.FundingType;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.ScholarshipStatus;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.student.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing `Student` entities in the database.
 * Provides CRUD operations and custom queries for filtering and sorting students.
 * <p>
 * Key functionalities:
 * - Extends `JpaRepository` to provide basic CRUD operations.
 * - Implements a custom query for filtering and sorting students based on various criteria.
 * <p>
 * Key methods:
 * - `findFilteredAndSortedStudents`: Filters and sorts students based on search criteria,
 * funding type, and scholarship status. Includes sorting functionality.
 * <p>
 * Query Parameters:
 * - `search`: Filters students by full name (concatenated surname, name, and patronymic) containing the search term.
 * - `fundingType`: Filters students by their funding type (e.g., budget, contract).
 * - `scholarshipStatuses`: Filters students by their scholarship status (e.g., ordinary, enhanced, none).
 * - `sortBy`: Specifies sorting order for the query results.
 * <p>
 * Relationships:
 * - Interacts with the `Student` entity and its related `Entrant` entity.
 * - Includes `FundingType` and `ScholarshipStatus` for filtering based on enum values.
 * <p>
 * Usage:
 * - Designed for use within the service layer to fetch filtered and sorted student data.
 * - Enables flexible and efficient data retrieval for student-related functionalities.
 *
 * @author Inessa Repeshko CS-222a
 * @see Student
 * @see FundingType
 * @see ScholarshipStatus
 * @see JpaRepository
 * @see Sort
 */


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s "
            + "JOIN s.entrant e "
            + "WHERE (:search IS NULL OR UPPER(CONCAT(e.surname, ' ', e.name, ' ', e.patronymic)) LIKE UPPER(CONCAT(:search, '%'))) "
            + "AND (:fundingType IS NULL OR s.fundingType = :fundingType) "
            + "AND (:scholarshipStatuses IS NULL OR s.scholarshipStatus IN :scholarshipStatuses) ")
    List<Student> findFilteredAndSortedStudents(@Param("search") String search,
                                                @Param("fundingType") FundingType fundingType,
                                                @Param("scholarshipStatuses") List<ScholarshipStatus> scholarshipStatuses,
                                                Sort sortBy);
}
