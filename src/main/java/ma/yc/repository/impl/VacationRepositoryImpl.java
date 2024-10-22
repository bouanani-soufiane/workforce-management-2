package ma.yc.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ma.yc.entity.Vacation;
import ma.yc.repository.VacationRepository;
import ma.yc.util.persistence.DefaultRepositoryImpl;

@ApplicationScoped
public class VacationRepositoryImpl extends DefaultRepositoryImpl<Vacation> implements VacationRepository {
    public VacationRepositoryImpl (  ) {
        super(Vacation.class);
    }

}
