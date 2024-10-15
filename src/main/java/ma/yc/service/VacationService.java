package ma.yc.service;

import ma.yc.entity.Vacation;

import java.util.List;

public interface VacationService {
    boolean create ( Vacation employee );

    boolean update ( Vacation employee );

    boolean delete ( Vacation employee );

    Vacation findById ( Long id );

    List<Vacation> findAll ();

    boolean setStatusRejected ( Vacation vacation );

    boolean setStatusAccepted ( Vacation vacation );

}
