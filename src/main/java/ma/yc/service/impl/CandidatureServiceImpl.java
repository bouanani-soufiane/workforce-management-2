package ma.yc.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import ma.yc.entity.Candidature;
import ma.yc.exception.EntityNotFoundException;
import ma.yc.exception.InvalidedRequestException;
import ma.yc.repository.CandidatureRepository;
import ma.yc.service.CandidatureService;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class CandidatureServiceImpl implements CandidatureService {

    private final CandidatureRepository repository;
    private final Validator validator;

    @Inject
    public CandidatureServiceImpl ( CandidatureRepository repository, Validator validator ) {
        this.repository = repository;
        this.validator = validator;

    }

    @Override
    public boolean create ( Candidature candidature ) {
        validate(candidature);
        return repository.create(candidature);
    }

    @Override
    public boolean update ( Candidature candidature ) {
        validate(candidature);
        return repository.update(candidature);
    }

    @Override
    public boolean delete ( Candidature candidature ) {
        if (repository.findById(candidature.getId()).isEmpty()) {
            throw new EntityNotFoundException("candidature", candidature.getId());
        }
        return repository.delete(candidature);
    }

    @Override
    public Candidature findById ( Long id ) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("candidature", id));
    }

    @Override
    public List<Candidature> findAll () {
        return repository.findAll();
    }

    private void validate ( Candidature candidature ) {
        Set<ConstraintViolation<Candidature>> violations = validator.validate(candidature);
        if (!violations.isEmpty()) {
            violations.forEach(c -> System.out.println(c.getPropertyPath().toString() + " -> " + c.getMessage()));
            throw new InvalidedRequestException("error candidature validation");
        }
    }

}
