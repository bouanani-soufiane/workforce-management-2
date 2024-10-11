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

            Employee employee = employeeService.findById(953L);

            System.out.println(employeeService.delete(employee));

        }
    }
}
