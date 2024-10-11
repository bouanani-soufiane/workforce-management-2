package ma.yc;

import ma.yc.entity.Employee;
import ma.yc.entity.valueObject.SocialSecurityNumber;
import ma.yc.service.EmployeeService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.time.LocalDate;

public class Main {
    public static void main ( String[] args ) {

        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            EmployeeService employeeService = container.select(EmployeeService.class).get();

            // Create a new employee and set the necessary fields
            Employee employee = new Employee();
            employee.setName("John Doe"); // Set employee name
            employee.setEmail("john.doe@example.com"); // Set email
            employee.setPassword("securePassword123"); // Set password
            employee.setAddress("123 Main St, Anytown, USA"); // Set address
            employee.setPhone("123-456-7890"); // Set phone number
            employee.setDepartement("Engineering"); // Set department
            employee.setJobTitle("Software Engineer"); // Set job title
            employee.setBirthDate(LocalDate.of(1990, 5, 20)); // Set birth date (example: May 20, 1990)

            // Set social security number (assuming you have a constructor or setters in this class)
            SocialSecurityNumber ssn = new SocialSecurityNumber("123-45-6789");
            employee.setSecurityNumber(ssn);

            employee.setHireDate(LocalDate.of(2022, 1, 15)); // Set hire date (example: January 15, 2022)
            employee.setSoldVacation(10); // Set number of sold vacation days
            employee.setRole(true); // Set role (true for admin, false for regular user)

            // Optionally, you can save the employee to the database
            employeeService.create(employee); // Ensure you have a save method in your EmployeeService

            // For testing: retrieve the employee by ID
            System.out.println("here: " + employeeService.findById(employee.getId())); // Ensure employee is persisted to get a valid ID
        }
    }
}
