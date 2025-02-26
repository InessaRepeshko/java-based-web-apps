package ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * EntrantList is a singleton class that extends {@link ArrayList} to store a predefined list of {@link Entrant} objects.
 * It provides a method for initializing the list with sample data and retrieving the list of entrants.
 *
 * Key features:
 * - Singleton pattern implementation to ensure only one instance of EntrantList exists.
 * - Predefined list of entrants that can be used for testing or demo purposes.
 * - The list includes both valid and logically deleted entrants, as well as examples with complex name formats.
 *
 * This class is primarily used to simulate a database or repository of entrants.
 *
 * @see Entrant
 * @see ArrayList
 * @author Inessa Repeshko CS-222a
 */

@NoArgsConstructor
public class EntrantList extends ArrayList<Entrant> {
    private static final long serialVersionUID = 1L;
    private static EntrantList instance;

    public static EntrantList getInstance() {
        if (instance == null) {
            instance = new EntrantList();

            List<String[]> entrantData = Arrays.asList(
                    new String[]{"КН22-4814", "Бабічєв", "Олексій", "Вікторович", "22.05.2005", "TRUE", "168.119"},
                    new String[]{"КН22-5835", "Волков", "Дмитро", "Олександрович", "16.07.2005", "TRUE", "151.200"},
                    new String[]{"КН22-5511", "Гєєнко", "Анна", "Олексіївна", "03.12.2005", "FALSE", "150.010"},
                    new String[]{"КН22-9299", "Голопьоров", "Микита", "Сергійович", "07.11.2005", "TRUE", "161.480"},
                    new String[]{"КН22-9012", "Гриценко", "Максим", "Георгійович", "04.12.2005", "TRUE", "191.941"},
                    new String[]{"КН22-6636", "Жарий", "Вадим", "Віталійович", "25.10.2005", "TRUE", "128.349"},
                    new String[]{"КН22-5140", "Жупанов", "Андрій", "Володимирович", "22.06.2005", "TRUE", "183.626"},
                    new String[]{"КН22-4896", "Кльоз", "Михайло", "Романович", "15.01.2005", "TRUE", "127.286"},
                    new String[]{"КН22-4694", "Коваль", "Микита", "Артемович", "11.10.2005", "TRUE", "139.736"},
                    new String[]{"КН22-5529", "Ковтун", "Єгор", "Вячеславович", "10.04.2005", "TRUE", "187.794"},
                    new String[]{"КН22-6874", "Колесниченко", "Денис", "Євгенович", "26.03.2005", "TRUE", "165.604"},
                    new String[]{"КН22-2947", "Ласкевич", "Андрій", "Сергійович", "05.09.2005", "TRUE", "175.399"},
                    new String[]{"КН22-3856", "Лукаш", "Антон", "Олександрович", "24.11.2005", "TRUE", "184.473"},
                    new String[]{"КН22-7534", "Палій", "Нікіта", "Андрійович", "05.05.2005", "TRUE", "168.000"},
                    new String[]{"КН22-3843", "Прокопов", "Владислав", "Сергійович", "12.12.2004", "TRUE", "164.660"},
                    new String[]{"КН22-7819", "Репешко", "Інесса", "Віталіївна", "06.05.2005", "FALSE", "185.300"},
                    new String[]{"КН22-9115", "Скиба", "Андрій", "Григорович", "26.02.2004", "TRUE", "125.909"},
                    new String[]{"КН22-3812", "Цибань", "Ольга", "Юріївна", "19.05.2004", "FALSE", "190.298"},
                    new String[]{"КН22-1828", "Шевченко", "Данило", "Валерійович", "25.10.2004", "TRUE", "166.847"},
                    new String[]{"КН22-9514", "Антипенко", "Катерина", "Віталіївна", "06.01.2005", "FALSE", "168.467"},
                    new String[]{"КН22-2929", "Багрянцева", "Марина", "Русланівна", "01.10.2005", "FALSE", "157.631"},
                    new String[]{"КН22-1637", "Бондаренко", "Ярослав", "Юрійович", "24.04.2005", "TRUE", "199.530"},
                    new String[]{"КН22-6681", "Булгаков", "Віктор", "Олександрович", "17.05.2005", "TRUE", "153.680"},
                    new String[]{"КН22-7557", "Васильєв", "Михайло", "Олександрович", "25.01.2005", "TRUE", "170.867"},
                    new String[]{"КН22-8759", "Деркач", "Дарина", "Євгенівна", "03.03.2005", "FALSE", "171.711"},
                    new String[]{"КН22-7309", "Ілюхін", "Тимур", "Ігорович", "24.01.2005", "TRUE", "125.771"},
                    new String[]{"КН22-8254", "Криженко", "Аліна", "Степанівна", "29.08.2005", "FALSE", "138.746"},
                    new String[]{"КН22-3825", "Посмашний", "Ярослав", "Дмитрович", "25.02.2000", "TRUE", "160.715"},
                    new String[]{"КН22-8153", "Рожкова", "Вікторія", "Андріївна", "04.10.1993", "FALSE", "188.304"},
                    new String[]{"КН22-4788", "Сизоненко", "Єлизавета", "Олександрівна", "17.02.1997", "FALSE", "124.241"}
            );

            entrantData.forEach(data -> instance.add(new Entrant(data[0], data[1], data[2], data[3], data[4], Boolean.parseBoolean(data[5]), Double.parseDouble(data[6]))));
//            entrantData.forEach(data -> instance.add(new Entrant(data[0], data[1], data[2], data[3], data[4], data[5], data[6])));

            Entrant entrant1 = new Entrant("КН22-9998", "Марчук", "Регіна", "Віталіївна", "06.06.1996", Boolean.parseBoolean("FALSE"), Double.parseDouble("200.000"));
            entrant1.setIsDeleted(true);
            instance.add(entrant1);

            Entrant entrant2 = new Entrant("АБВГҐІЇ99-0000", "Микол-Іванович Петренко-ОʼБраєн", "Іван Петро", "Петрович Іванович-ОʼБраєн", "06.06.1996", Boolean.parseBoolean("FALSE"), Double.parseDouble("200.000"));
            instance.add(entrant2);
        }

        return instance;
    }

    public List<Entrant> getEntrants() {
        return new ArrayList<>(getInstance());
    }
}
