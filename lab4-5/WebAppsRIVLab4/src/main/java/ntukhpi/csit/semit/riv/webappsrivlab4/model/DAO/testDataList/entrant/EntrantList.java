package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.testDataList.entrant;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Singleton class representing a list of predefined entrant test data.
 * This class provides a collection of `Entrant` objects for testing and demonstration purposes.
 * The list is initialized with sample data, which can be manipulated (added, removed, or cleared)
 * as needed.
 * <p>
 * Key functionalities:
 * - Provides access to a predefined list of `Entrant` objects.
 * - Allows adding, removing, and clearing entrants dynamically.
 * - Ensures that the list is shared across the application using the singleton pattern.
 * <p>
 * Sample data includes various combinations of entrant attributes such as case number, name,
 * birthday, gender, and rating score. This facilitates testing of functionalities dependent on
 * the `Entrant` entity.
 * <p>
 * The singleton pattern is implemented using an enum (`INSTANCE`) to ensure thread safety
 * and prevent multiple instantiations.
 *
 * @author Inessa Repeshko CS-222a
 */


public enum EntrantList {
    INSTANCE;

    private final List<Entrant> entrants;

    EntrantList() {
        this.entrants = new ArrayList<>();

        List<String[]> entrantData = Arrays.asList(
                new String[]{"КН22-4814", "Бабічєв", "Олексій", "Вікторович", "2005-05-22", "TRUE", "168.111"},
                new String[]{"КН22-5835", "Волков", "Дмитро", "Олександрович", "2005-07-16", "TRUE", "151.202"},
                new String[]{"КН22-5511", "Гєєнко", "Анна", "Олексіївна", "2005-12-03", "FALSE", "150.013"},
                new String[]{"КН22-9299", "Голопьоров", "Микита", "Сергійович", "2005-11-07", "TRUE", "161.484"},
                new String[]{"КН22-9012", "Гриценко", "Максим", "Георгійович", "2005-12-04", "TRUE", "191.945"},
                new String[]{"КН22-6636", "Жарий", "Вадим", "Віталійович", "2005-10-25", "TRUE", "128.346"},
                new String[]{"КН22-5140", "Жупанов", "Андрій", "Володимирович", "2005-06-22", "TRUE", "183.627"},
                new String[]{"КН22-4896", "Кльоз", "Михайло", "Романович", "2005-01-15", "TRUE", "127.288"},
                new String[]{"КН22-4694", "Коваль", "Микита", "Артемович", "2005-10-11", "TRUE", "139.739"},
                new String[]{"КН22-5529", "Ковтун", "Єгор", "Вячеславович", "2005-04-10", "TRUE", "187.790"},
                new String[]{"КН22-6874", "Колесниченко", "Денис", "Євгенович", "2005-03-26", "TRUE", "165.641"},
                new String[]{"КН22-2947", "Ласкевич", "Андрій", "Сергійович", "2005-09-05", "TRUE", "175.392"},
                new String[]{"КН22-3856", "Лукаш", "Антон", "Олександрович", "2005-11-24", "TRUE", "184.473"},
                new String[]{"КН22-7534", "Палій", "Нікіта", "Андрійович", "2005-05-05", "TRUE", "168.004"},
                new String[]{"КН22-3843", "Прокопов", "Владислав", "Сергійович", "2004-12-12", "TRUE", "164.665"},
                new String[]{"КН22-7819", "Репешко", "Інесса", "Віталіївна", "2005-05-06", "FALSE", "185.306"},
                new String[]{"КН22-9115", "Скиба", "Андрій", "Григорович", "2004-02-26", "TRUE", "125.907"},
                new String[]{"КН22-3812", "Цибань", "Ольга", "Юріївна", "2004-05-19", "FALSE", "190.298"},
                new String[]{"КН22-1828", "Шевченко", "Данило", "Валерійович", "2004-10-25", "TRUE", "166.849"},
                new String[]{"КН22-9514", "Антипенко", "Катерина", "Віталіївна", "2005-01-06", "FALSE", "168.460"},
                new String[]{"КН22-2929", "Багрянцева", "Марина", "Русланівна", "2005-10-01", "FALSE", "157.631"},
                new String[]{"КН22-1637", "Бондаренко", "Ярослав", "Юрійович", "2005-04-24", "TRUE", "199.532"},
                new String[]{"КН22-6681", "Булгаков", "Віктор", "Олександрович", "2005-05-17", "TRUE", "153.683"},
                new String[]{"КН22-7557", "Васильєв", "Михайло", "Олександрович", "2005-01-25", "TRUE", "170.864"},
                new String[]{"КН22-8759", "Деркач", "Дарина", "Євгенівна", "2005-03-03", "FALSE", "171.715"},
                new String[]{"КН22-7309", "Ілюхін", "Тимур", "Ігорович", "2005-01-24", "TRUE", "125.776"},
                new String[]{"КН22-8254", "Криженко", "Аліна", "Степанівна", "2005-08-29", "FALSE", "138.747"},
                new String[]{"КН22-3825", "Посмашний", "Ярослав", "Дмитрович", "2000-02-25", "TRUE", "160.718"},
                new String[]{"КН22-8153", "Рожкова", "Вікторія", "Андріївна", "1993-10-04", "FALSE", "188.309"},
                new String[]{"КН22-4788", "Сизоненко", "Єлизавета", "Олександрівна", "1997-02-17", "FALSE", "124.240"},
                new String[]{"КН22-8255", "Криженко", "Аліна", "Степанівна", "2005-08-29", "FALSE", "138.747"},
                new String[]{"КН22-3826", "Посмашний", "Ярослав", "Дмитрович", "2000-02-25", "TRUE", "160.718"},
                new String[]{"КН22-8154", "Рожкова", "Вікторія", "Андріївна", "1993-10-04", "FALSE", "188.309"},
                new String[]{"КН22-6682", "Булгаков", "Віктор", "Олександрович", "2005-05-17", "TRUE", "153.683"}
        );

        entrantData.forEach(
                data -> addEntrant(new Entrant(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5],
                        data[6])));

        addEntrant(new Entrant(
                "КН22-9998",
                "Марчук",
                "Регіна",
                "Віталіївна",
                "1996-06-06",
                "FALSE",
                "200"));

        /*addEntrant(new Entrant(
                "АБВГҐІЇ99-0000",
                "Микол-Іванович Петренко-ОʼБраєн",
                "Іван Петро",
                "Петрович Іванович-ОʼБраєн",
                "1996-06-06",
                "TRUE",
                "200"));*/
    }

    public List<Entrant> getEntrants() {
        return new ArrayList<>(entrants);
    }

    public void addEntrant(Entrant entrant) {
        entrants.add(entrant);
    }

    public void removeEntrant(Entrant entrant) {
        entrants.remove(entrant);
    }

    public void clearEntrants() {
        entrants.clear();
    }
}