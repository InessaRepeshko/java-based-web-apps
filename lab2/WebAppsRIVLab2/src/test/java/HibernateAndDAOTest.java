import lombok.extern.log4j.Log4j2;
import ntukhpi.csit.semit.riv.webappsrivlab2.model.DAO.EntrantDAO;
import ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant.EntrantList;
import org.apache.commons.lang3.Range;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import ntukhpi.csit.semit.riv.webappsrivlab2.model.util.hibernate.HibernateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HibernateAndDAOTest is a JUnit 5 test class designed to test Hibernate interactions and CRUD operations
 * with the {@link EntrantDAO} and {@link Entrant} entity. It focuses on:
 * - Initializing and managing Hibernate sessions.
 * - Performing database operations, such as inserting, updating, and deleting records.
 * - Verifying data retrieval with filtering and sorting using DAO methods.
 *
 * Key methods include:
 * - {@link #testWriteAll()}: Inserts all entrants from {@link EntrantList} into the database.
 * - {@link #testCRUDOperations()}: Tests the full cycle of CRUD operations (create, read, update, delete).
 * - {@link #testGetAll()}: Retrieves all entrants from the database.
 * - {@link #testGetFilteredAndUnsortedList()}: Retrieves a filtered and sorted list of entrants based on specified criteria.
 *
 * The class also includes comparison methods for verifying that the retrieved and expected data are equal.
 *
 * @see EntrantDAO
 * @see Entrant
 * @see EntrantList
 * @see HibernateUtil
 * @see SessionFactory
 * @see Test
 * @see BeforeAll
 * @see AfterAll
 * @see DisplayName
 *
 * @author Inessa Repeshko CS-222a
 */
@Log4j2
public class HibernateAndDAOTest {
    private static SessionFactory sessionFactory;
    private static Session session;

    @BeforeAll
    public static void testsSetUp() {
        sessionSetUp();
        fieldsSetUp();
        testWriteAll();
    }

    static void sessionSetUp() {
        log.info("Initialize Hibernate SessionFactory");
        sessionFactory = HibernateUtil.getSessionFactory();

        log.info("Hibernate Open session");
        session = sessionFactory.openSession();
        try (Session session = sessionFactory.openSession()) {
            assertNotNull(session, "Session should not be null");
        }
    }

    @AfterAll
    public static void sessionShutDown() {
        log.info("Hibernate Shutdown SessionFactory");

        if (sessionFactory != null) {
            sessionFactory.close();
        }

        HibernateUtil.shutdown();
    }

    private static final EntrantDAO operations = new EntrantDAO();
    private static List<Entrant> entrantList;
    private static final int listLength = EntrantList.getInstance().size();
    private final Entrant testEntrant = new Entrant(
            16L,
            "КН22-7819",
            "Репешко",
            "Інесса",
            "Віталіївна",
            LocalDate.parse("2005-05-06"),
            false,
            185.3,
            false
    );

    static void fieldsSetUp() {
        entrantList = null;
    }

    public static void testTruncateTable() {
        assertTrue(operations.truncateTable("entrant"), "Table truncation failed");
    }

    public static void testWriteAll() {
        entrantList = EntrantList.getInstance().getEntrants();
        assertFalse(entrantList.isEmpty());
        assertEquals(listLength, entrantList.size());

        assertEquals(entrantList.size(), operations.insertAll(entrantList).size());
    }

    @Test
    @DisplayName("Get all Entrants from the database")
    void testGetAll() {
        entrantList = operations.getAllList(Entrant.class, false);
        assertFalse(entrantList.isEmpty(), "The list of query results is empty");
        assertEquals(listLength - 1, entrantList.size(), "The size of  list of query results is not expected");
        List<Entrant> expectedList = EntrantList.getInstance().getEntrants();
        compareEntrantLists(expectedList);
        entrantList.forEach(System.out::println);
    }

    @Test
    @DisplayName("Test CRUD operations and get all Entrants from the database including deleted records")
    void testCRUDOperations() {
        log.info("Insert test Entrant");
        Entrant entrantToInsert = new Entrant("КН22-9995", "Марчук", "Регіна", "Віталіївна", "06.06.1996", Boolean.parseBoolean("FALSE"), Double.parseDouble("200.00"));
        Entrant insertedEntrant = operations.insert(entrantToInsert);
        assertEquals(listLength + 1, insertedEntrant == null ? 0 : insertedEntrant.getId(), "Id невалідні");

        log.info("Update test Entrant");
        Entrant entrantToUpdate = insertedEntrant;
        entrantToUpdate.setName("Інесса");
        Entrant updateResult = operations.update(entrantToUpdate);
        assertTrue(updateResult.getId() != null);
        compareEntrantsByFields(entrantToUpdate, updateResult, false);

        log.info("Delete test Entrant");
        boolean deleteResult = operations.delete(entrantToUpdate);
        assertTrue(deleteResult);

        log.info("Get All records including deleted");
        entrantList = operations.getAllList(Entrant.class, true);
        assertFalse(entrantList.isEmpty(), "The list of query results is empty");
        assertEquals(listLength + 1, entrantList.size(), "The size of  list of query results is not expected");
        List<Entrant> expectedList = EntrantList.getInstance().getEntrants();
        expectedList.add(entrantToUpdate);
        compareEntrantLists(expectedList);
        entrantList.forEach(System.out::println);
    }

    private void compareEntrantLists(List<Entrant> expected) {
        assertFalse(entrantList.isEmpty(), "The list of query results is empty");
        boolean includingId = expected.get(0).getId() != null;

        for (int i = 0; i < entrantList.size(); i++) {
            Entrant exp = expected.get(i);
            Entrant act = entrantList.get(i);

            if (includingId) {
                assertEquals(exp, act, "Entrants do not match");
            }

            compareEntrantsByFields(exp, act, includingId);
        }
    }

    private void compareEntrantObjects() {
        assertFalse(entrantList.isEmpty(), "The list of query results is empty");
        assertEquals(testEntrant, entrantList.get(0), "Entrants do not match");

        for (Entrant actual : entrantList) {
            compareEntrantsByFields(testEntrant, actual, true);
        }
    }

    private void compareEntrantObjects(Entrant expected) {
        assertFalse(entrantList.isEmpty(), "The list of query results is empty");
        assertEquals(expected, entrantList.get(0), "Entrants do not match");

        for (Entrant actual : entrantList) {
            compareEntrantsByFields(expected, actual, true);
        }
    }

    private void compareEntrantObjects(Entrant expected, Entrant actual) {
        assertEquals(expected, actual, "Entrants do not match");
        compareEntrantsByFields(expected, actual, true);
    }

    private void compareEntrantsByFields(Entrant expected, Entrant actual, boolean includingId) {
        Map<String, Object> fieldsExpected = expected.getAllFields();
        Map<String, Object> fieldsActual = actual.getAllFields();

        assertEquals(fieldsExpected.size(), fieldsActual.size(),
                "The size of fieldset of Entrants do not match: " + fieldsExpected.size() + " != " + fieldsActual.size());

        for (Map.Entry<String, Object> entry : fieldsExpected.entrySet()) {
            if (!includingId && entry.getKey().equals("id")) {
                continue;
            }

            String fieldName = entry.getKey();
            Object actualValue = entry.getValue();
            Object expectedValue = fieldsExpected.get(fieldName);

            assertEquals(expectedValue, actualValue,
                    "The values of the field " + fieldName + " do not match: " + expectedValue + " != " + actualValue);
        }
    }

    @Test
    @DisplayName("Find an Entrant in the database by id")
    void testFindById() {
        Entrant actual = operations.findById(Entrant.class, testEntrant.getId());
        compareEntrantObjects(testEntrant, actual);
    }

    @Test
    @DisplayName("Find an Entrant in the database by case number")
    void testFindByCaseNumber() {
        entrantList = operations.findByCaseNumber(testEntrant.getCaseNumber());
        compareEntrantObjects();
    }

    @Test
    @DisplayName("Find an Entrant in the database by surname")
    void testFindBySurname() {
        entrantList = operations.findBySurname("Репешко");
        operations.findByKey(Entrant.class, "surname", "Репешко");
        compareEntrantObjects();
    }

    @Test
    @DisplayName("Find an Entrant in the database by surname & name & patronymic")
    void testFindBySurnameNamePatronymic() {
        entrantList = operations.findBySurnameNamePatronymic("Репешко", "Інесса", "Віталіївна");
        compareEntrantObjects();
    }

    @Test
    @DisplayName("Find an Entrant in the database by birthday")
    void testFindByBirthday() {
        entrantList = operations.findByBirthday(testEntrant.getBirthday());
        compareEntrantObjects();
    }

    @Test
    @DisplayName("Find an Entrant in the database by gender")
    void testFindByGender() {
        entrantList = operations.getAllList(Entrant.class, true);
        System.out.println("entrantList" + entrantList);

        entrantList = operations.findByGender(testEntrant.getGender());
        entrantList.forEach(System.out::println);
        compareEntrantObjects(testEntrant, entrantList.get(1));
    }

    @Test
    @DisplayName("Find an Entrant in the database by rating score")
    void testFindByRatingScore() {
        entrantList = operations.findByRatingScore(testEntrant.getRatingScore());
        entrantList.forEach(System.out::println);
        compareEntrantObjects();
    }

    @Test
    @DisplayName("Get a list of records with filtering and sorting")
    void testGetFilteredAndUnsortedList() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("surname", "а");
        filters.put("ratingScore", Range.between(150.0, 170.0));
        filters.put("birthday", Range.between(
                LocalDate.parse("01.04.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                LocalDate.parse("01.12.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        ));

        Map<String, String> sortOrders = new HashMap<>();
        sortOrders.put("surname", "ASC");

        List<Entrant> resultList = operations.getFilteredAndSortedEntrantList(Entrant.class, true, filters, sortOrders, null);

        List<Long> expectedIds = new ArrayList<>();
        expectedIds.add(1L);
        expectedIds.add(21L);
        expectedIds.add(23L);
        expectedIds.add(14L);

        assertEquals(expectedIds.size(), resultList.size());
        assertEquals(expectedIds, resultList.stream().map(Entrant::getId).toList());
        resultList.forEach(System.out::println);
    }
}
