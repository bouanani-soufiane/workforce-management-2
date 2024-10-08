package ma.yc.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ma.yc.entity.Employee;
import ma.yc.repository.impl.EmployeeRepositoryImpl;
import ma.yc.service.interfaces.EmployeeService;

import java.util.List;

@ApplicationScoped
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EmployeeRepositoryImpl employeeRepository;


    @Override
    public boolean create ( Employee employee ) {
        return employeeRepository.create(employee);
    }

    @Override
    public boolean update ( Employee employee ) {
        return false;
    }

    @Override
    public boolean delete ( Employee employee ) {
        return false;
    }

    @Override
    public Employee findById ( int id ) {
        return null;
    }

    @Override
    public List<Employee> findAll () {
        return List.of();
    }
}
