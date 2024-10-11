package ma.yc.service.impl;

import jakarta.inject.Inject;
import ma.yc.entity.Candidature;
import ma.yc.repository.CandidatureRepository;
import ma.yc.service.CandidatureService;

import java.util.List;

public class CandidatureServiceImpl implements CandidatureService {

    private final CandidatureRepository repository;

    @Inject
    public CandidatureServiceImpl ( CandidatureRepository repository ) {
        this.repository = repository;
    }

    @Override
    public boolean create ( Candidature candidature ) {
        return repository.create(candidature);
    }

    @Override
    public boolean update ( Candidature candidature ) {
        return repository.update(candidature);
    }

    @Override
    public boolean delete ( Candidature candidature ) {
        return repository.delete(candidature);
    }

    @Override
    public Candidature findById ( Long id ) {
        return repository.findById(id);
    }

    @Override
    public List<Candidature> findAll () {
        return repository.findAll();
    }
}
