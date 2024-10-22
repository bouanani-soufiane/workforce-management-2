package ma.yc.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import ma.yc.entity.Employee;
import ma.yc.entity.Vacation;
import ma.yc.exception.EntityNotFoundException;
import ma.yc.exception.InvalidedRequestException;
import ma.yc.repository.EmployeeRepository;
import ma.yc.repository.VacationRepository;
import ma.yc.service.VacationService;
import ma.yc.util.Dates;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class VacationServiceImpl implements VacationService {

    private final VacationRepository repository;
    private final EmployeeRepository employeeRepository;
    private final Validator validator;

    @Inject
    public VacationServiceImpl ( VacationRepository repository, EmployeeRepository employeeRepository, Validator validator ) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.validator = validator;
    }

    @Override
    public boolean create ( Vacation vacation ) {
        return validate(vacation, this::createVacation);
    }

    @Override
    public boolean update ( Vacation vacation ) {
        return validate(vacation, repository::update);
    }

    @Override
    public boolean delete ( Vacation vacation ) {
        return repository.findById(vacation.getId())
                .map(found -> repository.delete(found))
                .orElseThrow(() -> new EntityNotFoundException("vacation", vacation.getId()));
    }

    @Override
    public Vacation findById ( Long id ) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("vacation", id));
    }

    @Override
    public List<Vacation> findAll () {
        return repository.findAll();
    }

    @Override
    public boolean setStatusRejected ( Vacation vacation ) {
        return repository.update(vacation);
    }

    @Override
    public boolean setStatusAccepted ( Vacation vacation ) {
        return repository.update(vacation);
    }

    private <T> boolean validate ( T entity, Function<T, Boolean> operation ) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (violations.isEmpty()) {
            return operation.apply(entity);
        } else {
            String errorMessage = violations.stream()
                    .map(v -> v.getPropertyPath() + " -> " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new InvalidedRequestException("Error in vacation validation: " + errorMessage);
        }
    }

    private boolean createVacation ( Vacation vacation ) {
        Employee employee = vacation.getEmployee();
        long totalDays = Dates.getDaysLeft(vacation.getStartDate(), vacation.getEndDate());

        if (totalDays > employee.getSoldVacation()) {
            throw new RuntimeException("Not enough days left in vacation");
        }

        employeeRepository.decrementVacationSold(employee, totalDays);

        if (isVacationOverlapping(vacation)) {
            repository.update(vacation);
        }

        return repository.create(vacation);
    }

    private boolean isVacationOverlapping ( Vacation vacation ) {
        return findAll().stream()
                .anyMatch(v -> v.getStartDate().isEqual(vacation.getStartDate())
                        && v.getEndDate().isEqual(vacation.getEndDate())
                        && v.getEmployee().getDepartement().equals(vacation.getEmployee().getDepartement()));
    }
}