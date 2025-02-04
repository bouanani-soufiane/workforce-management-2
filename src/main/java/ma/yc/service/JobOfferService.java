package ma.yc.service;

import ma.yc.entity.JobOffer;

import java.util.List;

public interface JobOfferService {
    boolean create ( JobOffer employee );

    boolean update ( JobOffer employee );

    boolean delete ( JobOffer employee );

    JobOffer findById ( Long id );

    List<JobOffer> findAll ();
}
