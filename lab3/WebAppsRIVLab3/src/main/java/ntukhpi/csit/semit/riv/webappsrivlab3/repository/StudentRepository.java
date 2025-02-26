package ntukhpi.csit.semit.riv.webappsrivlab3.repository;

import ntukhpi.csit.semit.riv.webappsrivlab3.model.enums.FundingType;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.enums.ScholarshipStatus;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.student.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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