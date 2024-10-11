package ma.yc.service;

import ma.yc.entity.JobOffer;
import ma.yc.util.genericRepository.DefaultRepository;

import java.util.List;

public interface JobOfferService extends DefaultRepository<JobOffer> {
    boolean create ( JobOffer employee );

    boolean update ( JobOffer employee );

    boolean delete ( JobOffer employee );

    JobOffer findById ( Long id );

    List<JobOffer> findAll ();
}
