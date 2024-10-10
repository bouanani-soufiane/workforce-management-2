package ma.yc.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ma.yc.entity.Employee;
import ma.yc.repository.interfaces.EmployeeRepository;

@ApplicationScoped
public class EmployeeRepositoryImpl extends DefaultRepositoryImpl<Employee> implements EmployeeRepository {

    public EmployeeRepositoryImpl () {
        super(Employee.class);
    }

}
