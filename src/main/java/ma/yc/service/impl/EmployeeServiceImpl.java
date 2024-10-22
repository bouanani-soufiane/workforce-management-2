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
import java.util.function.Function;
import java.util.stream.Collectors;

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
        return validate(employee, repository::create);
    }

    @Override
    public boolean update ( Employee employee ) {
        return validate(employee, repository::update);
    }

    @Override
    public boolean delete ( Employee employee ) {
        return repository.findById(employee.getId())
                .map(found -> repository.delete(found))
                .orElseThrow(() -> new EntityNotFoundException("employee", employee.getId()));
    }

    @Override
    public Employee findById ( Long id ) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("employee", id));
    }

    @Override
    public List<Employee> findAll () {
        return repository.findAll();
    }

    @Override
    public List<Employee> filterByDepartment ( String[] departmentValues ) {
        return findAll().stream()
                .filter(e -> matchesAnyDepartment(e, departmentValues))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> search ( String value ) {
        return findAll().stream()
                .filter(e -> matchesSearchValue(e, value))
                .collect(Collectors.toList());
    }

    private <T> boolean validate ( T entity, Function<T, Boolean> operation ) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (violations.isEmpty()) {
            return operation.apply(entity);
        } else {
            String errorMessage = violations.stream()
                    .map(v -> v.getPropertyPath() + " -> " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new InvalidedRequestException("Error in employee validation: " + errorMessage);
        }
    }

    private boolean matchesAnyDepartment ( Employee employee, String[] departmentValues ) {
        return departmentValues.length == 0 || List.of(departmentValues).contains(employee.getDepartement());
    }

    private boolean matchesSearchValue ( Employee employee, String value ) {
        return employee.getName().toLowerCase().contains(value.toLowerCase())
                || employee.getDepartement().toLowerCase().contains(value.toLowerCase());
    }
}