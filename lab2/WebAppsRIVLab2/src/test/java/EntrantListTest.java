import static org.junit.jupiter.api.Assertions.*;

import ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant.EntrantList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * EntrantListTest is a JUnit 5 test class for testing the functionality of the {@link EntrantList} class.
 * It verifies that the list of entrants is created correctly, the data is valid, and specific fields
 * meet the defined constraints such as unique case numbers, valid gender, and birthday format.
 *
 * Key test cases:
 * - {@link #testEntrantListCreation()}: Ensures the entrant list is created and populated correctly.
 * - {@link #testEntrantDataInList()}: Verifies the first entrant's data matches the expected values.
 * - {@link #testGenderValidationInList()}: Ensures all entrants have a valid gender value.
 * - {@link #testRatingScoreRange()}: Validates that the rating score for each entrant is within the allowed range.
 * - {@link #testEntrantListStringRepresentation()}: Confirms the string representation of the entrant list matches the expected format.
 * - {@link #testUniqueCaseNumbers()}: Ensures all entrants have unique case numbers.
 * - {@link #testBirthdayDateFormat()}: Verifies that the birthday format of all entrants is correct.
 *
 * This class ensures the integrity of the entrant data and the correct behavior of the {@link EntrantList} class.
 *
 * @see EntrantList
 * @see Entrant
 * @see Test
 * @see BeforeEach
 * @see DisplayName
 *
 * @author Inessa Repeshko CS-222a
 */
public class EntrantListTest {
    private List<Entrant> entrantList;
    private static final int listLength = EntrantList.getInstance().size();

    @BeforeEach
    public void setUp() {
        entrantList = EntrantList.getInstance().getEntrants();
    }

    @Test
    @DisplayName("Create Entrant List")
    public void testEntrantListCreation() {
        assertFalse(entrantList.isEmpty(), "The list should not be empty.");
        assertEquals(listLength, entrantList.size(), "The list must contain the " + listLength + " of the elements.");
    }

    @Test
    @DisplayName("Check Entrant Data in the List")
    public void testEntrantDataInList() {
        Entrant firstEntrant = entrantList.get(0);
        assertEquals("КН22-4814", firstEntrant.getCaseNumber());
        assertEquals("Бабічєв", firstEntrant.getSurname());
        assertEquals("Олексій", firstEntrant.getName());
        assertEquals("Вікторович", firstEntrant.getPatronymic());
        assertEquals(LocalDate.of(2005, 5, 22), firstEntrant.getBirthday());
        assertEquals(true, firstEntrant.getGender());
        assertEquals(168.11, firstEntrant.getRatingScore(), 0.01);
    }

    @Test
    @DisplayName("Validate Gender Data in Entrant List")
    public void testGenderValidationInList() {
        entrantList.forEach(entrant -> {
            assertNotNull(entrant.getGender(), "Gender cannot be null.");
            assertTrue(entrant.getGender() == Boolean.TRUE || entrant.getGender() == Boolean.FALSE, "Gender must be either ‘true’ or ‘false’.");
        });
    }

    @Test
    @DisplayName("Validate Rating Score Range in Entrant List")
    public void testRatingScoreRange() {
        entrantList.forEach(entrant -> {
            assertTrue(entrant.getRatingScore() >= 120.01 && entrant.getRatingScore() <= 200.00,
                    "The rating score should be between 120.01 and 200.00.");
        });
    }

    @Test
    @DisplayName("Get Entrant List string representation")
    public void testEntrantListStringRepresentation() {
        String expected = """
                Entrant(id=null, caseNumber=КН22-4814, surname=Бабічєв, name=Олексій, patronymic=Вікторович, birthday=2005-05-22, gender=true, ratingScore=168.119, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-5835, surname=Волков, name=Дмитро, patronymic=Олександрович, birthday=2005-07-16, gender=true, ratingScore=151.2, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-5511, surname=Гєєнко, name=Анна, patronymic=Олексіївна, birthday=2005-12-03, gender=false, ratingScore=150.01, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-9299, surname=Голопьоров, name=Микита, patronymic=Сергійович, birthday=2005-11-07, gender=true, ratingScore=161.48, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-9012, surname=Гриценко, name=Максим, patronymic=Георгійович, birthday=2005-12-04, gender=true, ratingScore=191.941, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-6636, surname=Жарий, name=Вадим, patronymic=Віталійович, birthday=2005-10-25, gender=true, ratingScore=128.349, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-5140, surname=Жупанов, name=Андрій, patronymic=Володимирович, birthday=2005-06-22, gender=true, ratingScore=183.626, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-4896, surname=Кльоз, name=Михайло, patronymic=Романович, birthday=2005-01-15, gender=true, ratingScore=127.286, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-4694, surname=Коваль, name=Микита, patronymic=Артемович, birthday=2005-10-11, gender=true, ratingScore=139.736, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-5529, surname=Ковтун, name=Єгор, patronymic=Вячеславович, birthday=2005-04-10, gender=true, ratingScore=187.794, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-6874, surname=Колесниченко, name=Денис, patronymic=Євгенович, birthday=2005-03-26, gender=true, ratingScore=165.604, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-2947, surname=Ласкевич, name=Андрій, patronymic=Сергійович, birthday=2005-09-05, gender=true, ratingScore=175.399, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-3856, surname=Лукаш, name=Антон, patronymic=Олександрович, birthday=2005-11-24, gender=true, ratingScore=184.473, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-7534, surname=Палій, name=Нікіта, patronymic=Андрійович, birthday=2005-05-05, gender=true, ratingScore=168.0, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-3843, surname=Прокопов, name=Владислав, patronymic=Сергійович, birthday=2004-12-12, gender=true, ratingScore=164.66, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-7819, surname=Репешко, name=Інесса, patronymic=Віталіївна, birthday=2005-05-06, gender=false, ratingScore=185.3, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-9115, surname=Скиба, name=Андрій, patronymic=Григорович, birthday=2004-02-26, gender=true, ratingScore=125.909, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-3812, surname=Цибань, name=Ольга, patronymic=Юріївна, birthday=2004-05-19, gender=false, ratingScore=190.298, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-1828, surname=Шевченко, name=Данило, patronymic=Валерійович, birthday=2004-10-25, gender=true, ratingScore=166.847, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-9514, surname=Антипенко, name=Катерина, patronymic=Віталіївна, birthday=2005-01-06, gender=false, ratingScore=168.467, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-2929, surname=Багрянцева, name=Марина, patronymic=Русланівна, birthday=2005-10-01, gender=false, ratingScore=157.631, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-1637, surname=Бондаренко, name=Ярослав, patronymic=Юрійович, birthday=2005-04-24, gender=true, ratingScore=199.53, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-6681, surname=Булгаков, name=Віктор, patronymic=Олександрович, birthday=2005-05-17, gender=true, ratingScore=153.68, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-7557, surname=Васильєв, name=Михайло, patronymic=Олександрович, birthday=2005-01-25, gender=true, ratingScore=170.867, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-8759, surname=Деркач, name=Дарина, patronymic=Євгенівна, birthday=2005-03-03, gender=false, ratingScore=171.711, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-7309, surname=Ілюхін, name=Тимур, patronymic=Ігорович, birthday=2005-01-24, gender=true, ratingScore=125.771, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-8254, surname=Криженко, name=Аліна, patronymic=Степанівна, birthday=2005-08-29, gender=false, ratingScore=138.746, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-3825, surname=Посмашний, name=Ярослав, patronymic=Дмитрович, birthday=2000-02-25, gender=true, ratingScore=160.715, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-8153, surname=Рожкова, name=Вікторія, patronymic=Андріївна, birthday=1993-10-04, gender=false, ratingScore=188.304, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-4788, surname=Сизоненко, name=Єлизавета, patronymic=Олександрівна, birthday=1997-02-17, gender=false, ratingScore=124.241, isDeleted=false)
                Entrant(id=null, caseNumber=КН22-9998, surname=Марчук, name=Регіна, patronymic=Віталіївна, birthday=1996-06-06, gender=false, ratingScore=200.0, isDeleted=true)
                Entrant(id=null, caseNumber=АБВГҐІЇ99-0000, surname=Микол-Іванович Петренко-Оʼбраєн, name=Іван Петро, patronymic=Петрович Іванович-Оʼбраєн, birthday=1996-06-06, gender=false, ratingScore=200.0, isDeleted=false)
                """;
        StringBuilder actual = new StringBuilder();
        entrantList.forEach(e -> actual.append(e).append("\n"));
        assertEquals(expected, actual.toString());
    }

    @Test
    @DisplayName("Check uniqueness of case numbers in Entrant List")
    public void testUniqueCaseNumbers() {
        long uniqueCaseNumbersCount = entrantList.stream()
                .map(Entrant::getCaseNumber)
                .distinct()
                .count();

        assertEquals(entrantList.size(), uniqueCaseNumbersCount, "Case numbers must be unique..");
    }

    @Test
    @DisplayName("Check birthday date format in Entrant List")
    public void testBirthdayDateFormat() {
        entrantList.forEach(entrant -> {
            assertDoesNotThrow(() -> LocalDate.parse(entrant.getBirthdayAsUADate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    "The birthday should be in the format 'dd.MM.yyyy'.");
        });
    }
}
