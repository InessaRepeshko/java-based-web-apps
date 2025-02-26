package ntukhpi.csit.semit.riv.webappsrivlab4;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.CustomServiceException;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.EntrantService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

/**
 * Unit test class for the `EntrantService` class.
 * This class tests the functionality of the `EntrantService` in managing `Entrant` entities.
 * It verifies the basic CRUD operations and ensures that constraints like duplicate case numbers are enforced.
 * <p>
 * Key functionalities tested:
 * - Creating and saving an entrant.
 * - Reading an entrant by ID and verifying that the case number matches.
 * - Updating an entrant and ensuring the changes are saved correctly.
 * - Deleting an entrant and ensuring it cannot be found afterward.
 * - Checking that attempting to save an entrant with a duplicate case number throws an exception.
 * <p>
 * Each test method ensures that the `EntrantService` behaves as expected and performs the required validation and exception handling.
 * <p>
 * Test annotations:
 * - `@BeforeEach` to set up the test data before each test method.
 * - `@AfterEach` to clean up any test data after each test method.
 * <p>
 * Dependencies:
 * - `EntrantService`: Provides methods for handling `Entrant` entities.
 *
 * @author Inessa Repeshko CS-222a
 * @see Entrant
 * @see EntrantService
 * @see CustomServiceException
 */

@SpringBootTest
class EntrantServiceTest {
    @Autowired
    private EntrantService entrantService;

    private Entrant testEntrant;

    @BeforeEach
    void setUp() {
        testEntrant = new Entrant("КН22-9876", "Франко", "Іван", "Якович", "2002-01-15", "TRUE", "150.0");
    }

    @AfterEach
    void reset() {
        entrantService.deleteEntrantById(testEntrant.getId());
    }

    @Test
    void testCreateEntrant() {
        Entrant savedEntrant = entrantService.saveEntrant(testEntrant);
        assertNotNull("Entrant should have an ID after saving.", savedEntrant.getId());
    }

    @Test
    void testReadEntrant() {
        Entrant savedEntrant = entrantService.saveEntrant(testEntrant);
        Entrant foundEntrant = entrantService.findEntrantById(savedEntrant.getId());
        assertNotNull("Entrant should be found by ID.", foundEntrant);
        assertEquals("Case number should match. Current value: '" + foundEntrant.getCaseNumber() + "'.",
                savedEntrant.getCaseNumber(), foundEntrant.getCaseNumber());
    }

    @Test
    void testUpdateEntrant() {
        String newName = "Василь";
        Entrant savedEntrant = entrantService.saveEntrant(testEntrant);
        savedEntrant.setName(newName);
        Entrant updatedEntrant = entrantService.saveEntrant(savedEntrant);

        Entrant foundEntrant = entrantService.findEntrantById(savedEntrant.getId());
        assertNotNull("Updated Entrant should be found by ID.", foundEntrant);
        assertEquals("Name should be updated. Current value: '" + foundEntrant.getName() + "'.",
                newName, foundEntrant.getName());
    }

    @Test
    void testDeleteEntrant() {
        Entrant savedEntrant = entrantService.saveEntrant(testEntrant);
        Long id = savedEntrant.getId();
        entrantService.deleteEntrantById(savedEntrant.getId());

        assertThrows(CustomServiceException.class, () -> {
            entrantService.findEntrantById(id);
        }, "Entrant should not be found after deletion.");
    }

    @Test
    void testSaveEntrantWithDuplicateCaseNumber() {
        entrantService.saveEntrant(testEntrant);
        Entrant duplicateEntrant = new Entrant("КН22-9876", "Шевченко", "Тарас", "Григорович", "2002-01-16", "FALSE", "180.0");

        assertThrows(CustomServiceException.class, () -> {
            entrantService.saveEntrant(duplicateEntrant);
        }, "Duplicate case number should throw CustomServiceException.");
    }
}
