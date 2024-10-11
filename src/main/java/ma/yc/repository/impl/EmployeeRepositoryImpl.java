package ma.yc.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ma.yc.entity.Employee;
import ma.yc.repository.EmployeeRepository;
import ma.yc.util.genericRepository.DefaultRepositoryImpl;

@ApplicationScoped
public class EmployeeRepositoryImpl extends DefaultRepositoryImpl<Employee> implements EmployeeRepository {

    public EmployeeRepositoryImpl () {
        super(Employee.class);
    }

}
