package ntukhpi.csit.semit.riv.webappsrivlab3.service;

import ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant.Entrant;

import java.time.LocalDate;
import java.util.List;

public interface EntrantService {
    List<Entrant> getAllEntrants();

    List<Entrant> getFilteredAndSortedEntrants(String search,
                                               LocalDate birthdayStart,
                                               LocalDate birthdayEnd,
                                               Double ratingScoreMin,
                                               Double ratingScoreMax,
                                               String sort);

    List<Entrant> findEntrantsWithoutStudents();

    void saveAllEntrants(List<Entrant> entrants);

    Entrant findEntrantById(Long id);

    Entrant findEntrantByExample(Entrant entrant);

    Long findStudentIdForEntrant(Long entrantId);

    Entrant saveEntrant(Entrant entrant);

    Entrant updateEntrant(Entrant entrant);

    void deleteEntrant(Entrant entrant);
}
