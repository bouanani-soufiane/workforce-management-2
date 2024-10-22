package ma.yc.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import ma.yc.entity.JobOffer;
import ma.yc.exception.EntityNotFoundException;
import ma.yc.exception.InvalidedRequestException;
import ma.yc.repository.JobOfferRepository;
import ma.yc.service.JobOfferService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository repository;
    private final Validator validator;

    @Inject
    public JobOfferServiceImpl(JobOfferRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public boolean create(JobOffer jobOffer) {
        return validate(jobOffer, repository::create);
    }

    @Override
    public boolean update(JobOffer jobOffer) {
        return validate(jobOffer, repository::update);
    }

    @Override
    public boolean delete(JobOffer jobOffer) {
        return repository.findById(jobOffer.getId())
                .map(found -> repository.delete(found))
                .orElseThrow(() -> new EntityNotFoundException("Job offer", jobOffer.getId()));
    }

    @Override
    public JobOffer findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("job offer", id));
    }

    @Override
    public List<JobOffer> findAll() {
        return repository.findAll();
    }

    private <T> boolean validate(T entity, Function<T, Boolean> operation) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (violations.isEmpty()) {
            return operation.apply(entity);
        } else {
            String errorMessage = violations.stream()
                    .map(v -> v.getPropertyPath() + " -> " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new InvalidedRequestException("Error in job offer validation: " + errorMessage);
        }
    }
}