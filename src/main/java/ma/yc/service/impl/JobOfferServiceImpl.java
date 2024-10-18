package ma.yc.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import ma.yc.entity.JobOffer;
import ma.yc.entity.Vacation;
import ma.yc.exception.EntityNotFoundException;
import ma.yc.exception.InvalidedRequestException;
import ma.yc.repository.JobOfferRepository;
import ma.yc.service.JobOfferService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository repository;
    private final Validator validator;


    @Inject
    public JobOfferServiceImpl ( JobOfferRepository repository, Validator validator ) {
        this.repository = repository;
        this.validator = validator;

    }

    @Override
    public boolean create ( JobOffer jobOffer ) {
        validate(jobOffer);
        return repository.create(jobOffer);
    }

    @Override
    public boolean update ( JobOffer jobOffer ) {
        validate(jobOffer);
        return repository.update(jobOffer);
    }

    @Override
    public boolean delete ( JobOffer jobOffer ) {
        if (repository.findById(jobOffer.getId()).isEmpty()) {
            throw new EntityNotFoundException("Job offer", jobOffer.getId());
        }
        return repository.delete(jobOffer);
    }

    @Override
    public JobOffer findById ( Long id ) {
        return repository.findById(id).orElseThrow(()-> new EntityNotFoundException("job offer" , id));
    }

    @Override
    public List<JobOffer> findAll () {
        return repository.findAll();
    }
    private void validate ( JobOffer jobOffer ) {
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        if (!violations.isEmpty()) {
            violations.forEach(c -> System.out.println(c.getPropertyPath().toString() + " -> " + c.getMessage()));
            throw new InvalidedRequestException("error jobOffer validation");
        }
    }
}
