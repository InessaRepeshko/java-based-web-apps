import static org.junit.jupiter.api.Assertions.*;

import ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant.Entrant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


/**
 * EntrantTest is a JUnit 5 test class designed to test the functionality of the {@link Entrant} class.
 * It verifies the correctness of data validation, formatting, and business logic within the Entrant class.
 *
 * Key test cases include:
 * - {@link #testEntrantCreation()}: Verifies the creation of an entrant using a no-arguments constructor and setters.
 * - {@link #testEntrantStringRepresentation()}: Ensures the string representation of the entrant object is formatted correctly using Lombok's `toString()` method.
 * - {@link #testInvalidCaseNumber()}: Tests that an invalid case number throws an appropriate exception.
 * - {@link #testNameValidation()}: Tests name validation rules, including valid and invalid name formats.
 * - {@link #testGenderAsString()}: Ensures the correct string representation for gender ("male" or "female").
 * - {@link #testBirthdayValidation()}: Verifies the birthday validation logic, ensuring correct date format and valid age ranges.
 * - {@link #testRatingScoreValidation()}: Tests rating score validation, ensuring it falls within the valid range.
 *
 * It also contains a nested test class, {@link NameValidationTest}, which focuses on testing name format validation based on specific rules.
 *
 * @see Entrant
 * @see Test
 * @see Nested
 * @see BeforeEach
 * @see DisplayName
 *
 * @author Inessa Repeshko CS-222a
 */
public class EntrantTest {
    private Entrant entrant;

    @BeforeEach
    public void setUp() {
        entrant = new Entrant();
        entrant.setCaseNumber("КН22-1234");
        entrant.setSurname("Іванов о-конел а");
        entrant.setName("Іван петро");
        entrant.setPatronymic("Іванович");
        entrant.setBirthday("01.01.2005");
        entrant.setGender(true);
        entrant.setRatingScore(187.98);
    }

    @Test
    @DisplayName("Test Entrant creation with no args constructor and setters")
    public void testEntrantCreation() {
        assertEquals("КН22-1234", entrant.getCaseNumber());
        assertEquals("Іванов О-Конел А", entrant.getSurname());
        assertEquals("Іван Петро", entrant.getName());
        assertEquals("Іванович", entrant.getPatronymic());
        assertEquals(LocalDate.of(2005, 1, 1), entrant.getBirthday());
        assertEquals(Boolean.TRUE, entrant.getGender());
        assertEquals(187.98, entrant.getRatingScore());
    }

    @Test
    @DisplayName("Test Entrant string representation with Lombok toString() method")
    public void testEntrantStringRepresentation() {
        assertEquals("Entrant(id=null, caseNumber=КН22-1234, surname=Іванов О-Конел А, name=Іван Петро, patronymic=Іванович, birthday=2005-01-01, gender=true, ratingScore=187.98, isDeleted=false)",
                entrant.toString());
    }

    @Test
    @DisplayName("Test invalid case number format")
    public void testInvalidCaseNumber() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            entrant.setCaseNumber("НевірнийНомер");
        });
        assertEquals("The case number should be in the format 'LLLLLLLLLLNN-NNNN'.", exception.getMessage());
    }

    @Test
    @DisplayName("Test valid and invalid names")
    public void testNameValidation() {
        // Valid names
        entrant.setName("Марія");
        assertEquals("Марія", entrant.getName());

        entrant.setName("Олексій-Петро");
        assertEquals("Олексій-Петро", entrant.getName());

        entrant.setName("анна марія");
        assertEquals("Анна Марія", entrant.getName());

        // Invalid name with digits
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> entrant.setName("Марія123"));
        assertEquals("Parts of the name can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.", exception.getMessage());

        // Invalid name with special symbols
        exception = assertThrows(IllegalArgumentException.class, () -> entrant.setName("%Марія!"));
        assertEquals("Parts of the name can contain only letters of the Ukrainian alphabet and can be complex using a space or dash.", exception.getMessage());
    }

    @Nested
    public class NameValidationTest {
        private static final String REGEX_NAMES = "^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[- ][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$";

        private boolean isValidName(String name) {
            return name.matches(REGEX_NAMES);
        }

        @Test
        @DisplayName("Test for matching one word without apostrophes")
        public void testSingleWordValid() {
            assertTrue(isValidName("Іван"));
            assertTrue(isValidName("Олександр"));
            assertTrue(isValidName("Коцур-Щербина"));
            assertTrue(isValidName("Анна Марія"));
        }

        @Test
        @DisplayName("Test for an incorrect word with invalid characters")
        public void testSingleWordInvalid() {
            assertFalse(isValidName("ʼМʼяч"));
            assertFalse(isValidName("Ьом"));
        }

        @Test
        @DisplayName("Test for matching one word with an apostrophe")
        public void testSingleWordWithApostrophe() {
            assertTrue(isValidName("ОʼБраєн"));
            assertFalse(isValidName("ʼОʼБраєн"));
            assertFalse(isValidName("ОʼБраєнʼ"));
        }

        @Test
        @DisplayName("Test for correct combinations of several words")
        public void testMultipleWordsValid() {
            assertTrue(isValidName("Іван-Петро"));
            assertTrue(isValidName("Петровʼян Іван-ОʼБраєн"));
        }

        @Test
        @DisplayName("Test for incorrect combinations of several words")
        public void testMultipleWordsInvalid() {
            assertFalse(isValidName("Іван--Петро"));
            assertFalse(isValidName("Іван Петро-"));
            assertFalse(isValidName("ʼПетро Іван"));
        }

        @Test
        @DisplayName("Test for mixed case")
        public void testMixedCaseValid() {
            assertTrue(isValidName("ІВАН"));
            assertTrue(isValidName("ОлЕкСАнДР"));
            assertFalse(isValidName("анна"));
        }

        @Test
        @DisplayName("Test for incorrect characters from other alphabets")
        public void testInvalidAlphabets() {
            assertFalse(isValidName("John"));
            assertFalse(isValidName("Ыван"));
        }

        @Test
        @DisplayName("Test for the correct number of words with hyphens and apostrophes")
        public void testValidMultipleWordsWithHyphenAndApostrophe() {
            assertTrue(isValidName("Микола-Іванович Петренко-ОʼБраєн"));
            assertTrue(isValidName("Анна-Марія Іван-ОʼБраєн"));
        }

        @Test
        @DisplayName("Test to check the minimum and maximum number of characters")
        public void testMinMaxCharacters() {
            assertTrue(isValidName("А"));
            assertTrue(isValidName("Олександр-Миколайович"));
            assertFalse(isValidName("Олександр-Миколайович-Петро-Іванович-Сидоров-Олександр-Миколайович-Петро-Іванович-Сидоров Олександр-Миколайович-Петро-Іванович-Сидоров-Олександр-Миколайович-Петро-Іванович-Сидоров"));
        }
    }


    @Test
    @DisplayName("Test birthday validation for valid and invalid dates")
    public void testBirthdayValidation() {
        // Valid birthday
        entrant.setBirthday("01.01.1990");
        assertEquals(LocalDate.of(1990, 1, 1), entrant.getBirthday());

        // Invalid birthday (too young)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            entrant.setBirthday("01.01.2010");
        });
        assertEquals("The age should be between 16 and 110 years old.", exception.getMessage());

        // Invalid birthday (too old)
        exception = assertThrows(IllegalArgumentException.class, () -> entrant.setBirthday("01.01.1900"));
        assertEquals("The age should be between 16 and 110 years old.", exception.getMessage());

        // Invalid format
        exception = assertThrows(IllegalArgumentException.class, () -> entrant.setBirthday("1990/01/01"));
        assertEquals("The birthday should be in the format 'dd.MM.yyyy'.", exception.getMessage());
    }

    @Test
    @DisplayName("Test gender representation as string")
    public void testGenderAsString() {
        assertEquals("male", entrant.getGenderAsString());

        entrant.setGender(false);
        assertEquals("female", entrant.getGenderAsString());
    }

    @Test
    @DisplayName("Test rating score validation")
    public void testRatingScoreValidation() {
        // Valid rating score
        entrant.setRatingScore(150.50);
        assertEquals(150.50, entrant.getRatingScore());

        // Invalid rating score (too low)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> entrant.setRatingScore(119.99));
        assertEquals("The rating score should be between 120.01 and 200.00.", exception.getMessage());

        // Invalid rating score (too high)
        exception = assertThrows(IllegalArgumentException.class, () -> entrant.setRatingScore(200.01));
        assertEquals("The rating score should be between 120.01 and 200.00.", exception.getMessage());
    }
}
