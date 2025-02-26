package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.testDataList.student;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.student.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Singleton class representing a list of predefined student test data.
 * This class provides a collection of `Student` objects for testing and demonstration purposes.
 * The list is initialized with sample data, which can be dynamically manipulated (added, removed, or cleared).
 * <p>
 * Key functionalities:
 * - Provides access to a predefined list of `Student` objects with associated `Entrant` data.
 * - Allows adding, removing, and clearing students dynamically.
 * - Ensures a single instance of the list is shared across the application using the singleton pattern.
 * <p>
 * Sample data includes various combinations of student attributes such as funding type, scholarship status,
 * corporate email, and linked entrant details. This facilitates testing functionalities that depend on the
 * `Student` entity.
 * <p>
 * The singleton pattern is implemented using an enum (`INSTANCE`) to ensure thread safety
 * and prevent multiple instantiations.
 *
 * @author Inessa Repeshko CS-222a
 * @see Student
 * @see Entrant
 * @see ArrayList
 * @see List
 * @see Enum
 */

public enum StudentList {
    INSTANCE;
    private final List<Student> students;

    StudentList() {
        this.students = new ArrayList<>();

        List<String[]> studentData = Arrays.asList(
                new String[]{"КН22-4814", "Бабічєв", "Олексій", "Вікторович", "2005-05-22", "TRUE", "168.111", "бюджет", "звичайна", "oleksii.babichiev@cs.khpi.edu.ua"},
                new String[]{"КН22-5835", "Волков", "Дмитро", "Олександрович", "2005-07-16", "TRUE", "151.202", "бюджет", "відсутня", "dmytro.o.volkov@cs.khpi.edu.ua"},
                new String[]{"КН22-5511", "Гєєнко", "Анна", "Олексіївна", "2005-12-03", "FALSE", "150.013", "бюджет", "відсутня", "anna.hieienko@cs.khpi.edu.ua"},
                new String[]{"КН22-9299", "Голопьоров", "Микита", "Сергійович", "2005-11-07", "TRUE", "161.484", "бюджет", "звичайна", "mykyta.holoporov@cs.khpi.edu.ua"},
                new String[]{"КН22-9012", "Гриценко", "Максим", "Георгійович", "2005-12-04", "TRUE", "191.945", "бюджет", "підвищена", "maksym.hrytsenko@cs.khpi.edu.ua"},
                new String[]{"КН22-6636", "Жарий", "Вадим", "Віталійович", "2005-10-25", "TRUE", "128.346", "контракт", "відсутня", "vadym.zharyi@cs.khpi.edu.ua"},
                new String[]{"КН22-5140", "Жупанов", "Андрій", "Володимирович", "2005-06-22", "TRUE", "183.627", "бюджет", "звичайна", "andrii.zhupanov@cs.khpi.edu.ua"},
                new String[]{"КН22-4896", "Кльоз", "Михайло", "Романович", "2005-01-15", "TRUE", "127.288", "контракт", "відсутня", "mykhailo.kloz@cs.khpi.edu.ua"},
                new String[]{"КН22-4694", "Коваль", "Микита", "Артемович", "2005-10-11", "TRUE", "139.739", "бюджет", "відсутня", "mykyta.koval@cs.khpi.edu.ua"},
                new String[]{"КН22-5529", "Ковтун", "Єгор", "Вячеславович", "2005-04-10", "TRUE", "187.790", "бюджет", "підвищена", "yehor.kovtun@cs.khpi.edu.ua"},
                new String[]{"КН22-6874", "Колесниченко", "Денис", "Євгенович", "2005-03-26", "TRUE", "165.641", "бюджет", "звичайна", "denys.kolesnychenko@cs.khpi.edu.ua"},
                new String[]{"КН22-2947", "Ласкевич", "Андрій", "Сергійович", "2005-09-05", "TRUE", "175.392", "бюджет", "звичайна", "andrii.laskevych@cs.khpi.edu.ua"},
                new String[]{"КН22-3856", "Лукаш", "Антон", "Олександрович", "2005-11-24", "TRUE", "184.473", "бюджет", "звичайна", "anton.lukash@cs.khpi.edu.ua"},
                new String[]{"КН22-7534", "Палій", "Нікіта", "Андрійович", "2005-05-05", "TRUE", "168.004", "бюджет", "відсутня", "nikita.palii@cs.khpi.edu.ua"},
                new String[]{"КН22-3843", "Прокопов", "Владислав", "Сергійович", "2004-12-12", "TRUE", "164.665", "бюджет", "звичайна", "vladyslav.prokopov@cs.khpi.edu.ua"},
                new String[]{"КН22-7819", "Репешко", "Інесса", "Віталіївна", "2005-05-06", "FALSE", "185.306", "бюджет", "підвищена", "inessa.repeshko@cs.khpi.edu.ua"},
                new String[]{"КН22-9115", "Скиба", "Андрій", "Григорович", "2004-02-26", "TRUE", "125.907", "контракт", "відсутня", "andrii.h.skyba@cit.khpi.edu.ua"},
                new String[]{"КН22-3812", "Цибань", "Ольга", "Юріївна", "2004-05-19", "FALSE", "190.298", "бюджет", "відсутня", "olha.tsyban@cs.khpi.edu.ua"},
                new String[]{"КН22-1828", "Шевченко", "Данило", "Валерійович", "2004-10-25", "TRUE", "166.849", "бюджет", "звичайна", "danylo.v.shevchenko@cs.khpi.edu.ua"},
                new String[]{"КН22-9514", "Антипенко", "Катерина", "Віталіївна", "2005-01-06", "FALSE", "168.460", "бюджет", "звичайна", "kateryna.antypenko@cs.khpi.edu.ua"},
                new String[]{"КН22-2929", "Багрянцева", "Марина", "Русланівна", "2005-10-01", "FALSE", "157.631", "бюджет", "звичайна", "marina.bahriantseva@cs.khpi.edu.ua"},
                new String[]{"КН22-1637", "Бондаренко", "Ярослав", "Юрійович", "2005-04-24", "TRUE", "199.532", "бюджет", "підвищена", "yaroslav.y.bondarenko@cs.khpi.edu.ua"},
                new String[]{"КН22-6681", "Булгаков", "Віктор", "Олександрович", "2005-05-17", "TRUE", "153.683", "бюджет", "відсутня", "viktor.bulhakov@cs.khpi.edu.ua"},
                new String[]{"КН22-7557", "Васильєв", "Михайло", "Олександрович", "2005-01-25", "TRUE", "170.864", "бюджет", "відсутня", "mykhailo.vasyliev@cs.khpi.edu.ua"},
                new String[]{"КН22-8759", "Деркач", "Дарина", "Євгенівна", "2005-03-03", "FALSE", "171.715", "бюджет", "звичайна", "daryna.derkach@cs.khpi.edu.ua"},
                new String[]{"КН22-7309", "Ілюхін", "Тимур", "Ігорович", "2005-01-24", "TRUE", "125.776", "контракт", "відсутня", "tymur.iliukhin@cs.khpi.edu.ua"},
                new String[]{"КН22-8254", "Криженко", "Аліна", "Степанівна", "2005-08-29", "FALSE", "138.747", "бюджет", "відсутня", "alina.kryzhenko@cs.khpi.edu.ua"},
                new String[]{"КН22-3825", "Посмашний", "Ярослав", "Дмитрович", "2000-02-25", "TRUE", "160.718", "бюджет", "звичайна", "yaroslav.posmashnyi@cs.khpi.edu.ua"},
                new String[]{"КН22-8153", "Рожкова", "Вікторія", "Андріївна", "1993-10-04", "FALSE", "188.309", "бюджет", "підвищена", "viktoriia.rozhkova@cs.khpi.edu.ua"},
                new String[]{"КН22-4788", "Сизоненко", "Єлизавета", "Олександрівна", "1997-02-17", "FALSE", "124.240", "контракт", "відсутня", "yelyzaveta.syzonenko@cs.khpi.edu.ua"}
        );

        studentData.forEach(
                data -> addStudent(new Student(
                        new Entrant(data[0], data[1], data[2], data[3], data[4], data[5], data[6]),
                        data[7], data[8], data[9])));
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void clearStudents() {
        students.clear();
    }
}
