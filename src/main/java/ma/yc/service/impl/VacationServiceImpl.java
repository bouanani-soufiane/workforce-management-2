package ma.yc.service.impl;

import jakarta.inject.Inject;
import ma.yc.entity.Vacation;
import ma.yc.repository.VacationRepository;
import ma.yc.service.VacationService;

import java.util.List;

public class VacationServiceImpl implements VacationService {

    private VacationRepository repository;

    @Inject
    public VacationServiceImpl ( VacationRepository repository ) {
        this.repository = repository;
    }

    @Override
    public boolean create ( Vacation vacation ) {
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
    public Vacation findById ( int id ) {
        return repository.findById(id);
    }

    @Override
    public List<Vacation> findAll () {
        return repository.findAll();
    }
}
