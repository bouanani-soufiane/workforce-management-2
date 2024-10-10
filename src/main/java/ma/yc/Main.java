package ma.yc;

import ma.yc.entity.Employee;
import ma.yc.entity.valueObject.SocialSecurityNumber;
import ma.yc.service.interfaces.EmployeeService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.time.LocalDateTime;

public class Main {
    public static void main ( String[] args ) {

//        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();

        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            EmployeeService employeeService = container.select(EmployeeService.class).get();

            Employee employee = new Employee();
            employee.setName("aaaaaaaaaaaaaaaaaaaaaaa");
            employee.setEmail("aaaaaaaaaaaaaaa@gmail.com");
            employee.setPassword("&é&é&é");
            employee.setBirthDate(LocalDateTime.of(2022, 2, 2, 1, 2));
            employee.setDepartement("IT");
            employee.setAddress("addess");
            employee.setJobTitle("Software Developer");
            employee.setHireDate(LocalDateTime.now());
            employee.setSoldVacation(10);

            SocialSecurityNumber ssn = new SocialSecurityNumber("123-45-6789");
            employee.setSecurityNumber(ssn);

            employeeService.create(employee);

            System.out.println("here : "+employeeService.delete(employeeService.findById(402)));


        }
    }
}
