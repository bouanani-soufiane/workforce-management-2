package ma.yc;

import ma.yc.entity.Employee;
import ma.yc.service.interfaces.EmployeeService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;


public class Main {
    public static void main ( String[] args ) {
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            EmployeeService employeeService = container.select(EmployeeService.class).get();

            Employee employee = new Employee();
            employee.setName("soufiane");
            employee.setEmail("email@mail.com");
            employee.setPhone("0630067322");

            // Use the service to create the employee
            boolean success = employeeService.create(employee);

            // Output the result
            if (success) {
                System.out.println("Employee created successfully!");
            } else {
                System.out.println("Failed to create employee.");
            }
        }
    }
}
