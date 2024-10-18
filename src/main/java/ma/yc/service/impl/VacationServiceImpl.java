package ma.yc.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ma.yc.entity.Employee;
import ma.yc.entity.Vacation;
import ma.yc.exception.EntityNotFoundException;
import ma.yc.repository.EmployeeRepository;
import ma.yc.repository.VacationRepository;
import ma.yc.service.VacationService;

import java.util.List;

import static ma.yc.util.Dates.getDaysLeft;

@ApplicationScoped
public class VacationServiceImpl implements VacationService {

    private VacationRepository repository;
    private EmployeeRepository employeeRepository;

    @Inject
    public VacationServiceImpl ( VacationRepository repository, EmployeeRepository employeeRepository ) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean create ( Vacation vacation ) {

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
        return repository.update(vacation);
    }

    @Override
    public boolean delete ( Vacation vacation ) {
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

}
