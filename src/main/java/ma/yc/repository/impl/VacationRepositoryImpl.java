package ma.yc.repository.impl;

import ma.yc.entity.Vacation;
import ma.yc.repository.VacationRepository;
import ma.yc.util.genericRepository.DefaultRepositoryImpl;

public class VacationRepositoryImpl extends DefaultRepositoryImpl<Vacation> implements VacationRepository {
    public VacationRepositoryImpl (  ) {
        super(Vacation.class);
    }
}
