package ma.yc.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import ma.yc.entity.Vacation;
import ma.yc.enums.VacationStatus;
import ma.yc.repository.VacationRepository;
import ma.yc.util.EntityManagerProvider;
import ma.yc.util.genericRepository.DefaultRepositoryImpl;

@ApplicationScoped
public class VacationRepositoryImpl extends DefaultRepositoryImpl<Vacation> implements VacationRepository {
    public VacationRepositoryImpl (  ) {
        super(Vacation.class);
    }

    @Override
    public boolean setStatusRejected ( Vacation vacation ) {
        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            vacation.setVacationStatus(VacationStatus.REJECTED);
            entityManager.merge(vacation);
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
    public boolean setStatusAccepted ( Vacation vacation ) {
        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            vacation.setVacationStatus(VacationStatus.ACCEPTED);
            entityManager.merge(vacation);
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
}
