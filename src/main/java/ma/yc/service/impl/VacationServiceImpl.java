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

import java.util.List;
import java.util.Set;

import static ma.yc.util.Dates.getDaysLeft;

@ApplicationScoped
public class VacationServiceImpl implements VacationService {

    private VacationRepository repository;
    private EmployeeRepository employeeRepository;
    private final Validator validator;


    @Inject
    public VacationServiceImpl ( VacationRepository repository, EmployeeRepository employeeRepository, Validator validator ) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.validator = validator;
    }

    @Override
    public boolean create ( Vacation vacation ) {
        validate(vacation);
        Employee employee = vacation.getEmployee();
        int sold = employee.getSoldVacation();

        long totlaDays = getDaysLeft(vacation.getStartDate(), vacation.getEndDate());

        if (totlaDays > sold) {
            throw new RuntimeException("not enough days left in vacation");
        }
        employeeRepository.decrementVacationSold(employee,totlaDays);

        boolean isOverlapping = this.findAll().stream().anyMatch(( v ) -> v.getStartDate().isEqual(vacation.getStartDate()) && v.getEndDate().isEqual(vacation.getEndDate()) && v.getEmployee().getDepartement().equals(vacation.getEmployee().getDepartement()));
        if (isOverlapping) {
            repository.setStatusRejected(vacation);
        }
        return repository.create(vacation);
    }

    @Override
    public boolean update ( Vacation vacation ) {
        validate(vacation);
        return repository.update(vacation);
    }

    @Override
    public boolean delete ( Vacation vacation ) {
        if (repository.findById(vacation.getId()).isEmpty()) {
            throw new EntityNotFoundException("vacation", vacation.getId());
        }
        return repository.delete(vacation);
    }

    @Override
    public Vacation findById ( Long id ) {
        return repository.findById(id).orElseThrow(()-> new EntityNotFoundException("vacation" , id));
    }

    @Override
    public List<Vacation> findAll () {
        return repository.findAll();
    }

    @Override
    public boolean setStatusRejected ( Vacation vacation ) {
        return repository.setStatusRejected(vacation);
    }

    @Override
    public boolean setStatusAccepted ( Vacation vacation ) {
        return repository.setStatusAccepted(vacation);

    }

    private void validate ( Vacation vacation ) {
        Set<ConstraintViolation<Vacation>> violations = validator.validate(vacation);
        if (!violations.isEmpty()) {
            violations.forEach(c -> System.out.println(c.getPropertyPath().toString() + " -> " + c.getMessage()));
            throw new InvalidedRequestException("error vacation validation");
        }
    }

}
