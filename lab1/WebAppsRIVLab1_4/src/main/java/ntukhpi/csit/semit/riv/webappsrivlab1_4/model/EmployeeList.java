package ntukhpi.csit.semit.riv.webappsrivlab1_4.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The EmployeeList class is a singleton that extends the ArrayList of Employee objects. It contains
 * a static method to retrieve the single instance of the class and initializes the list with
 * predefined employee data when first accessed. This class ensures that only one instance of
 * the employee list exists throughout the application.
 *
 * <p>It provides a default set of employees with various attributes such as name, birthday, gender,
 * salary, program language, and importance. This list can be accessed and modified by the user.
 *
 * @author Inessa Repeshko CS-222a
 */
public class EmployeeList extends ArrayList<Employee> {
    private static final long serialVersionUID = 1L;
    private static EmployeeList instance;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * This ensures that the EmployeeList follows the singleton design pattern.
     */
    private EmployeeList() {
    }

    /**
     * Provides access to the single instance of EmployeeList. If the instance does not
     * exist, it is created and populated with a predefined list of employees.
     *
     * <p>This method initializes the employee list with various employee records, including
     * different attributes such as name, birthday, gender, salary, program language,
     * and importance. If an instance already exists, it simply returns the existing one.
     *
     * @return the singleton instance of EmployeeList.
     */
    public static EmployeeList getInstance() {
        if (instance == null) {
            instance = new EmployeeList();

            instance.add(new Employee(-1, "Alice Johnson", "12.03.1985", false, 120000, "JavaScript", 2));
            instance.add(new Employee(-1, "Bob Smith", "18.06.1990", true, 95000, "TypeScript", 1));
            instance.add(new Employee(-1, "Catherine Brown", "25.11.1978", false, 145000, "Python", 1));
            instance.add(new Employee(-1, "David Wilson", "04.02.1995", true, 85000, "Java", 0));
            instance.add(new Employee(-1, "Emma Davis", "30.07.1983", false, 100000, "C#", 1));
            instance.add(new Employee(-1, "Frank Miller", "20.01.2000", true, 70000, "PHP", 0));
            instance.add(new Employee(-1, "Grace Lee", "15.05.1988", false, 110000, "JavaScript", 2));
            instance.add(new Employee(-1, "Henry White", "22.09.1993", true, 90000, "TypeScript", 0));
            instance.add(new Employee(-1, "Isabella Green", "07.08.1997", false, 80000, "Python", 0));
            instance.add(new Employee(-1, "James Black", "13.12.1981", true, 115000, "Java", 2));
            instance.add(new Employee(-1, "Olivia Thompson", "03.04.1999", false, 95000, "C#", 1));
            instance.add(new Employee(-1, "william garcia", "29.10.1975", true, 130000, "PHP", 2));
            instance.add(new Employee(-1, "test", "birthday", true, -1, "proglang", -1));
        }

        return instance;
    }

    /**
     * Retrieves a list of all employees currently stored in the EmployeeList.
     *
     * <p>This method returns a new copy of the current EmployeeList to prevent
     * external modifications to the original list. The returned list contains
     * all employee objects that have been added to the EmployeeList.
     *
     * @return a new ArrayList containing all employees in the EmployeeList.
     */
    public List<Employee> getEmployees() {
        return new ArrayList<>(this);
    }
}

