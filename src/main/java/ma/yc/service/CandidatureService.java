package ma.yc.service;

import ma.yc.entity.Candidature;
import ma.yc.entity.Candidature;
import ma.yc.util.genericRepository.DefaultRepository;

import java.util.List;

public interface CandidatureService {
    boolean create ( Candidature employee );

    boolean update ( Candidature employee );

    boolean delete ( Candidature employee );

    Candidature findById ( Long id );

    List<Candidature> findAll ();
}
