package ma.yc.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.yc.entity.Employee;
import ma.yc.repository.interfaces.EmployeeRepository;
import ma.yc.util.EntityManagerProvider;

import java.util.List;

@ApplicationScoped
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
    private final EntityTransaction transaction = entityManager.getTransaction();

    @Override
    public boolean create ( Employee employee ) {

        try {
            transaction.begin();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
        }
        return false;
    }

    @Override
    public boolean update ( Employee employee ) {
        return false;
    }

    @Override
    public boolean delete ( Employee employee ) {
        return false;
    }

    @Override
    public Employee findById ( int id ) {
        return null;
    }

    @Override
    public List<Employee> findAll () {
        return List.of();
    }
}
