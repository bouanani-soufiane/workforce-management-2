package ma.yc.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import ma.yc.persistance.CustomPresistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;

/**
 * When EntityManagerFactory.getInstance() is called,
 * the SingletonHelper class gets loaded by the class loader,
 * which in turn creates the EntityManagerFactory instance.
 * This ensures that the instance is lazily initialized.
 * The class loading mechanism in Java ensures that this process
 * is thread-safe and the Singleton instance is created only once.
 */

public class EntityManagerProvider {

    private static EntityManagerProvider INSTANCE;
    private EntityManagerFactory managerFactory;

    private EntityManagerProvider () {

        this.managerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(new CustomPresistenceUnitInfo(), new HashMap<>());
    }

    public static EntityManagerProvider getInstance () {
        return SingletonHelper.INSTANCE;
    }

    public EntityManager getEntityManager () {
        return managerFactory.createEntityManager();
    }

    private static class SingletonHelper {
        private static final EntityManagerProvider INSTANCE = new EntityManagerProvider();
    }
}
