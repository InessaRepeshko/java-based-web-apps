package ntukhpi.csit.semit.riv.webappsrivlab3.repository;

import ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant.Entrant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
