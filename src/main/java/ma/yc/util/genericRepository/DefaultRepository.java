package ma.yc.util.genericRepository;

import java.util.List;
import java.util.Optional;

public interface DefaultRepository<Entity> {
    boolean create ( Entity entity );

    boolean update ( Entity entity );

    boolean delete ( Entity entity );

    Optional<Entity> findById ( Long id );

    List<Entity> findAll ();


}

