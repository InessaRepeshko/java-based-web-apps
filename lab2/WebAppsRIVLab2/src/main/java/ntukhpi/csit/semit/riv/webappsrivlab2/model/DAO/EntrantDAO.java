package ntukhpi.csit.semit.riv.webappsrivlab2.model.DAO;

import ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant.Entrant;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EntrantDAO is a data access object (DAO) that provides methods for interacting with the
 * `Entrant` entity in the database. It implements the {@link GenericDAO} interface and
 * provides specific methods for finding, filtering, sorting, and validating entrant.
 *
 * This class includes methods to:
 * - Find entrant by various fields (e.g., case number, surname, birthday, gender, etc.).
 * - Retrieve all entrant or a filtered and sorted list of entrant.
 * - Check for duplicate entrant records based on unique fields.
 *
 * @see GenericDAO
 * @see Entrant
 *
 * @author Inessa Repeshko CS-222a
 */
public class EntrantDAO implements GenericDAO<Entrant> {
    public Entrant findById(Long id) {
        return findById(getEntityClass(), id);
    }

    public List<Entrant> findByCaseNumber(String caseNumber) {
        return findByKey(Entrant.class, "caseNumber", caseNumber);
    }

    public List<Entrant> findBySurname(String surname) {
        return findByKey(Entrant.class, "surname", surname);
    }

    public List<Entrant> findBySurnameNamePatronymic(String surname, String name, String patronymic) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("surname", surname);
        parameters.put("name", name);
        parameters.put("patronymic", patronymic);
        return findByMultipleKeys(Entrant.class, parameters, false);
    }

    public List<Entrant> findByBirthday(LocalDate birthday) {
        return findByKey(Entrant.class, "birthday", birthday);
    }

    public List<Entrant> findByGender(Boolean gender) {
        return findByKey(Entrant.class, "gender", gender);
    }

    public List<Entrant> findByRatingScore(Double ratingScore) {
        return findByKey(Entrant.class, "ratingScore", ratingScore);
    }

    public List<Entrant> getAllList() {
        return getAllList(Entrant.class, false);
    }

    public List<Entrant> getFilteredAndSortedList(Map<String, Object> filters,
                                                  Map<String, String> sortOrders,
                                                  Map<String, String> search) {
        return getFilteredAndSortedEntrantList(Entrant.class, false,  filters, sortOrders, search);
    }

    @Override
    public Class<Entrant> getEntityClass() {
        return Entrant.class;
    }

    @Override
    public void hasDuplicate(Entrant entityToCheck) throws IllegalArgumentException {
        Map<String, Object> uniqueFields = entityToCheck.getUniqueFields();

        for (Map.Entry<String, Object> field : uniqueFields.entrySet()) {
            if (field.getValue() == null) {
                uniqueFields.remove(field.getKey());
            }
        }

        List<Entrant> results = findByMultipleKeys(Entrant.class, uniqueFields, true);

        if (!results.isEmpty()) {
            StringBuilder message = new StringBuilder("Duplicate record found.");

            for (Map.Entry<String, Object> entry : uniqueFields.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                for (Entrant duplicate : results) {
                    Map<String, Object> duplicateFields = duplicate.getUniqueFields();
                    Object duplicateFieldValue = duplicateFields.get(fieldName);

                    if (fieldValue != null && fieldValue.equals(duplicateFieldValue)) {
                        message.append("\nThe record with the field '").append(fieldName)
                                .append("' having value '").append(fieldValue).append("' already exists.");
                    }
                }
            }

            throw new IllegalArgumentException(message.toString());
        }
    }
}
