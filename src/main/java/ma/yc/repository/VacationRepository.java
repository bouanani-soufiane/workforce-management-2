package ma.yc.repository;

import ma.yc.entity.Vacation;
import ma.yc.util.genericRepository.DefaultRepository;

public interface VacationRepository extends DefaultRepository<Vacation> {
    boolean setStatusRejected ( Vacation employee );
    boolean setStatusAccepted ( Vacation employee );

}
