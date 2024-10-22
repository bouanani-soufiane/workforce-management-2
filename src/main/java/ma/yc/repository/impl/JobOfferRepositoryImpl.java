package ma.yc.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ma.yc.entity.JobOffer;
import ma.yc.repository.JobOfferRepository;
import ma.yc.util.persistence.DefaultRepositoryImpl;

@ApplicationScoped
public class JobOfferRepositoryImpl extends DefaultRepositoryImpl<JobOffer> implements JobOfferRepository {
    public JobOfferRepositoryImpl () {
        super(JobOffer.class);
    }
}
