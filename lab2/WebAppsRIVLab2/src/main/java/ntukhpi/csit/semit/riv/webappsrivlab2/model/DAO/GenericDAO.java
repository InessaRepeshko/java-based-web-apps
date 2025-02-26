package ntukhpi.csit.semit.riv.webappsrivlab2.model.DAO;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import ntukhpi.csit.semit.riv.webappsrivlab2.model.util.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.*;
import org.apache.commons.lang3.Range;

/**
 * GenericDAO is a generic interface for performing common database operations using Hibernate.
 * It defines methods for CRUD (Create, Read, Update, Delete) operations, filtering, sorting, and validation.
 * This interface provides default implementations for these operations, allowing reuse across multiple entity types.
 *
 * @param <E> the entity type that this DAO will manage
 *
 * The following operations are provided:
 * - Fetching all entities with optional filtering by the `isDeleted` flag.
 * - Fetching filtered and sorted lists of entities based on specified criteria.
 * - Searching for entities by key or by multiple keys.
 * - Inserting, updating, and deleting entities.
 * - Handling duplicates and validation of entity constraints.
 * - Truncating a table.
 *
 * The class relies on a {@link SessionFactory} and a {@link Validator} for Hibernate and validation operations.
 *
 * @author Inessa Repeshko CS-222a
 */

public interface GenericDAO<E> {
    final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    final Validator validator = HibernateUtil.getValidator();

    Class<E> getEntityClass();

    void hasDuplicate(E entityToCheck) throws IllegalArgumentException;

    default List<E> getAllList(Class<E> entityClass, boolean includeDeleted) {
        try (Session session = sessionFactory.openSession()) {
            if (!includeDeleted) {
                session.enableFilter("deletedEntrantFilter").setParameter("isDeleted", false);
            }

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(entityClass);
            Root<E> rootEntry = cq.from(entityClass);
            CriteriaQuery<E> all = cq.select(rootEntry);
            cq.orderBy(cb.asc(rootEntry.get("id")));

            TypedQuery<E> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        }
    }

    default List<E> getFilteredAndSortedEntrantList(Class<E> entityClass,
                                                        boolean includeDeleted,
                                                        Map<String, Object> filters,
                                                        Map<String, String> sortOrders,
                                                        Map<String, String> search) {
        try (Session session = sessionFactory.openSession()) {
            if (!includeDeleted) {
                session.enableFilter("deletedEntrantFilter").setParameter("isDeleted", false);
            }

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(entityClass);
            Root<E> rootEntry = cq.from(entityClass);

            List<Predicate> predicates = new ArrayList<>();

            if (filters != null && !filters.isEmpty()) {
                for (Map.Entry<String, Object> filter : filters.entrySet()) {

                    String fieldName = filter.getKey();
                    Object fieldValue = filter.getValue();

                    /* String filters */
                    if (fieldValue instanceof String) {
                        String stringFilter = (String) fieldValue;

                        /* Check if there are wildcards for LIKE */
                        if (stringFilter.contains("%")) {
                            predicates.add(cb.like(cb.upper(rootEntry.get(fieldName)), cb.upper(cb.literal(stringFilter))));
                        } else {
                            predicates.add(cb.like(cb.upper(rootEntry.get(fieldName)), cb.upper(cb.literal("%" + stringFilter + "%"))));
                        }
                    }

                    /* Date filters */
                    else if (fieldValue instanceof Range && fieldName.equals("birthday")) {
                        Range<LocalDate> dateRange = (Range<LocalDate>) fieldValue;
                        LocalDate startDate = dateRange.getMinimum();
                        LocalDate endDate = dateRange.getMaximum();
                        predicates.add(cb.between(rootEntry.get(fieldName), startDate, endDate));
                    }

                    /* Numeric filters */
                    else if (fieldValue instanceof Range && fieldName.equals("ratingScore")) {
                        Range<Double> range = (Range<Double>) fieldValue;
                        Double min = range.getMinimum();
                        Double max = range.getMaximum();
                        predicates.add(cb.between(rootEntry.get(fieldName), min, max));
                    }

                    /* Boolean filters */
                    else if (fieldValue instanceof Boolean) {
                        boolean value = (Boolean) fieldValue;
                        predicates.add(cb.equal(rootEntry.get(fieldName), value));
                    }
                }
            }


            if (search != null && !search.isEmpty()) {
                List<Predicate> searchPredicates = new ArrayList<>();

                for (Map.Entry<String, String> searchEntry : search.entrySet()) {
                    String fieldName = searchEntry.getKey();
                    String searchValue = searchEntry.getValue();

                    if (searchValue != null && !searchValue.isEmpty()) {
                        searchPredicates.add(cb.like(cb.upper(rootEntry.get(fieldName)), cb.upper(cb.literal("%" + searchValue + "%"))));
                    }
                }

                /* Combine all search predicates with OR condition */
                if (!searchPredicates.isEmpty()) {
                    predicates.add(cb.or(searchPredicates.toArray(Predicate[]::new)));
                }
            }

            if (!predicates.isEmpty()) {
                cq.where(predicates.toArray(Predicate[]::new));
            }

            List<Order> orderList = new ArrayList<>();

            if (sortOrders != null && !sortOrders.isEmpty()) {
                for (Map.Entry<String, String> sortRule : sortOrders.entrySet()) {
                    String fieldName = sortRule.getKey();
                    String direction = sortRule.getValue().toUpperCase();

                    if ("ASC".equals(direction)) {
                        orderList.add(cb.asc(rootEntry.get(fieldName)));
                    } else if ("DESC".equals(direction)) {
                        orderList.add(cb.desc(rootEntry.get(fieldName)));
                    }
                }
            }

            if (!orderList.isEmpty()) {
                cq.orderBy(orderList);
            }

            TypedQuery<E> allQuery = session.createQuery(cq);
            return allQuery.getResultList();
        }
    }

    default E findById(Class<E> entityClass, Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Unable to find a record by an invalid identifier.");
        }

        try (Session session = sessionFactory.openSession()) {
            session.enableFilter("deletedEntrantFilter").setParameter("isDeleted", false);
            E entity = session.get(entityClass, id);

            if (entity == null) {
                throw new IllegalArgumentException("Could not find a record by the passed ID.");
            }

            return entity;
        }
    }

    default List<E> findByKey(Class<E> entityClass, String fieldName, Object fieldValue) {
        try (Session session = sessionFactory.openSession()) {
            session.enableFilter("deletedEntrantFilter").setParameter("isDeleted", false);
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(entityClass);
            Root<E> root = cq.from(entityClass);
            cq.select(root).where(cb.equal(root.get(fieldName), fieldValue));

            TypedQuery<E> query = session.createQuery(cq);
            List<E> results = query.getResultList();
            return results;
        }
    }

    default List<E> findByKeyWhereBetween(Class<E> entityClass, String columnName, Object fromValue, Object toValue) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(entityClass);
            Root<E> root = cq.from(entityClass);

            cq.select(root).where(cb.between(root.get(columnName), (Comparable) fromValue, (Comparable) toValue));

            TypedQuery<E> query = session.createQuery(cq);
            List<E> results = query.getResultList();
            return results;
        }
    }

    default List<E> findByMultipleKeys(Class<E> entityClass, Map<String, Object> parameters, boolean includeDeleted) {
        try (Session session = sessionFactory.openSession()) {
            if (!includeDeleted) {
                session.enableFilter("deletedEntrantFilter").setParameter("isDeleted", false);
            }

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(entityClass);
            Root<E> root = cq.from(entityClass);

            List<Predicate> predicatesList = new ArrayList<>();

            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                predicatesList.add(cb.equal(root.get(entry.getKey()), entry.getValue()));
            }

            Predicate[] predicatesArray = predicatesList.toArray(Predicate[]::new);
            cq.select(root).where(predicatesArray);

            TypedQuery<E> query = session.createQuery(cq);
            List<E> results = query.getResultList();
            return results;
        }
    }

    default void validateEntity(E entity) throws IllegalArgumentException {
        Set<ConstraintViolation<E>> violationSet = validator.validate(entity);

        if (!violationSet.isEmpty()) {
            StringBuilder violationMessages = new StringBuilder("Invalid entity values:");

            for (ConstraintViolation<E> violation : violationSet) {
                violationMessages.append("\n").append(violation.getMessage());
            }

            throw new IllegalArgumentException(violationMessages.toString());
        }
    }

    default E insert(E entityToSave) {
        try {
            validateEntity(entityToSave);
            hasDuplicate(entityToSave);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return null;
        }

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            E entity = session.merge(entityToSave);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
            return null;
        }
    }

    default List<E> insertAll(List<E> entitiesToSave) {
        List<E> dublicates = new ArrayList<>();

        for (E entity : entitiesToSave) {
            try {
                validateEntity(entity);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return null;
            }

            try {
                hasDuplicate(entity);
            } catch (IllegalArgumentException e) {
                dublicates.add(entity);
            }
        }

        dublicates.forEach(entitiesToSave::remove);

        if (entitiesToSave.isEmpty()) {
            return null;
        }

        Transaction transaction = null;
        Set<E> savedEntities = new HashSet<>();

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            for (E entity : entitiesToSave) {
                E savedEntity = session.merge(entity);
                savedEntities.add(savedEntity);
            }

            if (savedEntities.size() == entitiesToSave.size()) {
                transaction.commit();
                return savedEntities.stream().toList();
            } else {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }

                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            return null;
        }
    }


    default E update(E entityToUpdate) {
        try {
            validateEntity(entityToUpdate);

            var getIdMethod = getEntityClass().getMethod("getId");
            Long id = (Long) getIdMethod.invoke(entityToUpdate);

            if (id == null) {
                throw new IllegalArgumentException("Cannot update a record without an id.");
            }

            if (findById(getEntityClass(), id) == null) {
                throw new IllegalArgumentException("No record exists to update.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            E entity = session.merge(entityToUpdate);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
            return null;
        }
    }

    default boolean delete(E entityToDelete, Long id) {
        try {
            if (findById(getEntityClass(), id) == null) {
                throw new IllegalArgumentException("No record exists to delete.");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entityToDelete);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            return false;
        }
    }


    default boolean delete(E entityToDelete) {
        try {
            var getIdMethod = getEntityClass().getMethod("getId");
            Long idToDelete = (Long) getIdMethod.invoke(entityToDelete);

            if (idToDelete == null) {
                throw new IllegalArgumentException("Cannot delete a record without an id.");
            }

            E entrantExists = findById(getEntityClass(), idToDelete);

            if (entrantExists == null) {
                throw new IllegalArgumentException("No record exists for deletion.");
            }

            var setIsDeletedMethod = getEntityClass().getMethod("setIsDeleted", Boolean.class);
            setIsDeletedMethod.invoke(entityToDelete, Boolean.TRUE);

            return update(entityToDelete) != null;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean truncateTable(String tableName) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE " + tableName;
            session.createNativeQuery(sql, getEntityClass()).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
            return false;
        }
    }
}

