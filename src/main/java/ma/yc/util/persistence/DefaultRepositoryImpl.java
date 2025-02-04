package ma.yc.util.persistence;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class DefaultRepositoryImpl<Entity> implements DefaultRepository<Entity> {
    private Class<Entity> entityType;

    public DefaultRepositoryImpl ( Class<Entity> entityType ) {
        this.entityType = entityType;
    }


    @Override
    public boolean create ( Entity entity ) {
        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public boolean update ( Entity entity ) {
        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public boolean delete ( Entity entity ) {
        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Entity managedEntity = entityManager.contains(entity) ? entity : entityManager.merge(entity);
            entityManager.remove(managedEntity);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override

    public Optional<Entity> findById ( Long id ) {
        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(entityType, id));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return Optional.empty();
    }

    @Override
    public List<Entity> findAll () {
        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
        try {
            return entityManager.createQuery("SELECT e FROM " + entityType.getSimpleName() + " e", entityType).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

}
