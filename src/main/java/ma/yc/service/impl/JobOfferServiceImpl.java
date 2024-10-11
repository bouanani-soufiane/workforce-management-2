package ma.yc.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ma.yc.entity.JobOffer;
import ma.yc.repository.JobOfferRepository;
import ma.yc.service.JobOfferService;

import java.util.List;

@ApplicationScoped
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository repository;

    @Inject
    public JobOfferServiceImpl ( JobOfferRepository repository ) {
        this.repository = repository;
    }

    @Override
    public boolean create ( JobOffer employee ) {
        return repository.create(employee);
    }

    @Override
    public boolean update ( JobOffer employee ) {
        return repository.update(employee);
    }

    @Override
    public boolean delete ( JobOffer employee ) {
        return repository.delete(employee);
    }

    @Override
    public JobOffer findById ( int id ) {
        return repository.findById(id);
    }

    @Override
    public List<JobOffer> findAll () {
        return repository.findAll();
    }
}
