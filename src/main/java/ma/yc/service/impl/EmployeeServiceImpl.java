package ma.yc.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ma.yc.entity.Employee;
import ma.yc.repository.EmployeeRepository;
import ma.yc.service.EmployeeService;

import java.util.List;

@ApplicationScoped
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Inject
    public EmployeeServiceImpl ( EmployeeRepository repository ) {
        this.repository = repository;
    }

    @Override
    public boolean create ( Employee employee ) {
        return repository.create(employee);
    }

    @Override
    public boolean update ( Employee employee ) {
        return repository.update(employee);
    }

    @Override
    public boolean delete ( Employee employee ) {
        return repository.delete(employee);
    }

    @Override
    public Employee findById ( Long id ) {
        return repository.findById(id);
    }

    @Override
    public List<Employee> findAll () {
        return repository.findAll();
    }
}
