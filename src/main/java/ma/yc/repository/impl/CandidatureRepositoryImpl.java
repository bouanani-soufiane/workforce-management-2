package ma.yc.repository.impl;

import ma.yc.entity.Candidature;
import ma.yc.repository.CandidatureRepository;
import ma.yc.util.genericRepository.DefaultRepositoryImpl;

public class CandidatureRepositoryImpl extends DefaultRepositoryImpl<Candidature> implements CandidatureRepository {
    public CandidatureRepositoryImpl () {
        super(Candidature.class);
    }
}
