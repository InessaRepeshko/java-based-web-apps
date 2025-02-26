package ntukhpi.csit.semit.riv.webappsrivlab3;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ntukhpi.csit.semit.riv.webappsrivlab3.model.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab3.service.EntrantService;

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
        entrantService.deleteEntrant(testEntrant);
    }

    @Test
    void testCreateEntrant() {
        Entrant savedEntrant = entrantService.saveEntrant(testEntrant);
        assertNotNull(savedEntrant.getId(), "Entrant should have an ID after saving.");
    }

    @Test
    void testReadEntrant() {
        Entrant savedEntrant = entrantService.saveEntrant(testEntrant);
        Entrant foundEntrant = entrantService.findEntrantById(savedEntrant.getId());
        assertNotNull(foundEntrant, "Entrant should be found by ID.");
        assertEquals(savedEntrant.getCaseNumber(), foundEntrant.getCaseNumber(), "Case number should match.");
    }

    @Test
    void testUpdateEntrant() {
        Entrant savedEntrant = entrantService.saveEntrant(testEntrant);
        savedEntrant.setName("Василь");
        Entrant updatedEntrant = entrantService.saveEntrant(savedEntrant);

        Entrant foundEntrant = entrantService.findEntrantById(savedEntrant.getId());
        assertNotNull(foundEntrant, "Updated Entrant should be found by ID.");
        assertEquals("Василь", foundEntrant.getName(), "Name should be updated.");
    }

    @Test
    void testDeleteEntrant() {
        Entrant savedEntrant = entrantService.saveEntrant(testEntrant);
        Long id = savedEntrant.getId();
        entrantService.deleteEntrant(savedEntrant);

        assertThrows(EntityNotFoundException.class, () -> {
            entrantService.findEntrantById(id);
        }, "Entrant should not be found after deletion.");
    }

    @Test
    void testSaveEntrantWithDuplicateCaseNumber() {
        entrantService.saveEntrant(testEntrant);
        Entrant duplicateEntrant = new Entrant("КН22-9876", "Шевченко", "Тарас", "Григорович", "2002-01-16", "FALSE", "180.0");

        assertThrows(IllegalArgumentException.class, () -> {
            entrantService.saveEntrant(duplicateEntrant);
        }, "Duplicate case number should throw IllegalArgumentException.");
    }
}
