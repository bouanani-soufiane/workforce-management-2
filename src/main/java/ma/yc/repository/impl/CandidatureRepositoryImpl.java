package ma.yc.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ma.yc.entity.Candidature;
import ma.yc.repository.CandidatureRepository;
import ma.yc.util.genericRepository.DefaultRepositoryImpl;

@ApplicationScoped
public class CandidatureRepositoryImpl extends DefaultRepositoryImpl<Candidature> implements CandidatureRepository {
    public CandidatureRepositoryImpl () {
        super(Candidature.class);
    }
}
