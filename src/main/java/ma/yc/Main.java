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

                Employee em = employeeService.findById(1);

                em.setName("anass");

            System.out.println("here");

                employeeService.update(em);




//            if (success) {
//                System.out.println("Employee created successfully!");
//            } else {
//                System.out.println("Failed to create employee.");
//            }
        }
    }
}
