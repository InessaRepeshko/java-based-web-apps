package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.testDataList.user;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DTO.user.UserTestData;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Singleton class representing a list of predefined user test data.
 * This class provides a collection of `UserTestData` objects for testing and demonstration purposes.
 * The list is initialized with sample data, which can be manipulated (added, removed, or cleared) as needed.
 * <p>
 * Key functionalities:
 * - Provides access to a predefined list of `UserTestData` objects.
 * - Supports dynamic operations to add, remove, or clear users.
 * - Ensures a single instance of the list is shared across the application using the singleton pattern.
 * <p>
 * Sample data includes users with attributes such as username, password, role, email, and personal details (surname, name, patronymic).
 * This facilitates testing functionalities dependent on the `UserTestData` structure and user-related logic.
 * <p>
 * The singleton pattern is implemented using an enum (`INSTANCE`) to ensure thread safety and prevent multiple instantiations.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserTestData
 * @see Role
 * @see java.util.List
 * @see java.util.ArrayList
 * @see Enum
 */

public enum UserList {
    INSTANCE;

    private final List<UserTestData> users;

    UserList() {
        this.users = new ArrayList<>();

        List<String[]> userData = Arrays.asList(
                new String[]{"shavlii_oleksii", "Admin123!", Role.ADMIN.name(), "shavlii.oleksii@ntu.khpi.edu.ua", "Шавлій", "Олексій", "Олександрович"},
                new String[]{"burulka-liudmyla", "$Luda1990", Role.ENTRANT_MANAGER.name(), "burulka.liudmyla@ntu.khpi.edu.ua", "Бурулька", "Людмила", "Ярославівна"},
                new String[]{"voloshkov-tymur", "Tymur9876*", Role.STUDENT_MANAGER.name(), "voloshkov.tymur@ntu.khpi.edu.ua", "Волошков", "Тимур", "Сергійович"},
                new String[]{"kulish.volodymyr", "12Vova_+A", Role.ENTRANT_VIEWER.name(), "kulish.volodymyr@cs.khpi.edu.ua", "Куліш", "Володимир", "Анатолійович"},
                new String[]{"stepova.alina", "Alina0987!", Role.STUDENT_VIEWER.name(), "stepova.alina@cs.khpi.edu.ua", "Степова", "Аліна", "Трофимівна"},
                new String[]{"ivanenko_anna", "Anna2023@", Role.ADMIN.name(), "ivanenko.anna@ntu.khpi.edu.ua", "Іваненко", "Анна", "Петрівна"},
                new String[]{"tarasenko-oleh", "Oleh9876*!", Role.ENTRANT_MANAGER.name(), "tarasenko.oleh@ntu.khpi.edu.ua", "Тарасенко", "Олег", "Іванович"},
                new String[]{"melnyk_daryna", "Daryna!555", Role.STUDENT_MANAGER.name(), "melnyk.daryna@ntu.khpi.edu.ua", "Мельник", "Дарина", "Анатоліївна"},
                new String[]{"horbunov.vladyslav", "Vladyslav_2022", Role.ENTRANT_VIEWER.name(), "horbunov.vladyslav@cs.khpi.edu.ua", "Горбунов", "Владислав", "Михайлович"},
                new String[]{"romanova-olena", "Olena123#@", Role.STUDENT_VIEWER.name(), "romanova.olena@cs.khpi.edu.ua", "Романова", "Олена", "Олексіївна"},
                new String[]{"vasylchenko.mykhailo", "Mykhailo_1998!", Role.ADMIN.name(), "vasylchenko.mykhailo@ntu.khpi.edu.ua", "Васильченко", "Михайло", "Олександрович"},
                new String[]{"shapoval-kseniia", "Kseniia789#", Role.ENTRANT_MANAGER.name(), "shapoval.kseniia@ntu.khpi.edu.ua", "Шаповал", "Ксенія", "Василівна"},
                new String[]{"kozak.yevhenii", "Yevhenii@2021", Role.STUDENT_MANAGER.name(), "kozak.yevhenii@ntu.khpi.edu.ua", "Козак", "Євгеній", "Олегович"},
                new String[]{"bondar.inna", "Inna999+*", Role.ENTRANT_VIEWER.name(), "bondar.inna@cs.khpi.edu.ua", "Бондар", "Інна", "Ігорівна"},
                new String[]{"kravchenko-artem", "Artem456*#", Role.STUDENT_VIEWER.name(), "kravchenko.artem@cs.khpi.edu.ua", "Кравченко", "Артем", "Богданович"}
        );

        userData.forEach(user -> addUser(new UserTestData(user[0], user[1], Role.fromName(user[2]), user[3], user[4], user[5], user[6])));
    }

    public List<UserTestData> getUsers() {
        return new ArrayList<>(users);
    }

    public void addUser(UserTestData user) {
        users.add(user);
    }

    public void removeUser(UserTestData user) {
        users.remove(user);
    }

    public void clearUsers() {
        users.clear();
    }
}
