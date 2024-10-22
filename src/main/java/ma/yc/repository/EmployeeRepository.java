package ma.yc.repository;

import ma.yc.entity.Employee;
import ma.yc.util.persistence.DefaultRepository;

public interface EmployeeRepository extends DefaultRepository<Employee> {
    boolean decrementVacationSold ( Employee employee, long sold );

}
