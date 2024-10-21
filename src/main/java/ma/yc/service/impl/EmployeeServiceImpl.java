package ma.yc.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import ma.yc.entity.Employee;
import ma.yc.exception.EntityNotFoundException;
import ma.yc.exception.InvalidedRequestException;
import ma.yc.repository.EmployeeRepository;
import ma.yc.service.EmployeeService;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final Validator validator;

    @Inject
    public EmployeeServiceImpl ( EmployeeRepository repository, Validator validator ) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public boolean create ( Employee employee ) {
        validate(employee);
        return repository.create(employee);
    }

    @Override
    public boolean update ( Employee employee ) {
        validate(employee);
        return repository.update(employee);
    }

    @Override
    public boolean delete ( Employee employee ) {
        if (repository.findById(employee.getId()).isEmpty()) {
            throw new EntityNotFoundException("employee", employee.getId());
        }
        return repository.delete(employee);
    }

    @Override
    public Employee findById ( Long id ) {
        return repository.findById(id).orElseThrow(()-> new EntityNotFoundException("employee" , id));
    }

    @Override
    public List<Employee> findAll () {
        return repository.findAll();
    }

    @Override
    public List<Employee> filterByDepartment ( String[] value ) {
        return List.of();
    }

    @Override
    public List<Employee> search ( String value ) {
        return List.of();
    }

    private void validate ( Employee employee ) {
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (!violations.isEmpty()) {
            violations.forEach(c -> System.out.println(c.getPropertyPath().toString() + " -> " + c.getMessage()));
            throw new InvalidedRequestException("error employee validation");
        }
    }
}
