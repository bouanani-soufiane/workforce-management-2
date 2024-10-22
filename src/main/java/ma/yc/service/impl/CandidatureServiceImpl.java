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
import java.util.function.Function;
import java.util.stream.Collectors;

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
        return validate(candidature, repository::create);
    }

    @Override
    public boolean update ( Candidature candidature ) {
        return validate(candidature, repository::update);
    }

    @Override
    public boolean delete ( Candidature candidature ) {
        return repository.findById(candidature.getId()).map(found -> repository.delete(found)).orElseThrow(() -> new EntityNotFoundException("candidature", candidature.getId()));
    }

    @Override
    public Candidature findById ( Long id ) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("candidature", id));
    }

    @Override
    public List<Candidature> findAll () {
        return repository.findAll();
    }

    private <T> boolean validate ( T entity, Function<T, Boolean> operation ) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (violations.isEmpty()) {
            return operation.apply(entity);
        } else {
            String errorMessage = violations.stream().map(v -> v.getPropertyPath() + " -> " + v.getMessage()).collect(Collectors.joining(", "));
            throw new InvalidedRequestException("Error in candidature validation: " + errorMessage);
        }
    }
}