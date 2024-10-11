package ma.yc.util.genericRepository;

import java.util.List;

public interface DefaultRepository<Entity> {
    boolean create ( Entity entity );

    boolean update ( Entity entity );

    boolean delete ( Entity entity );

    Entity findById ( int id );

    List<Entity> findAll ();


}

