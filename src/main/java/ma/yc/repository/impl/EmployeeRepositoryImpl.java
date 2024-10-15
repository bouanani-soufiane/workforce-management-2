package ma.yc.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import ma.yc.entity.Employee;
import ma.yc.repository.EmployeeRepository;
import ma.yc.util.EntityManagerProvider;
import ma.yc.util.genericRepository.DefaultRepositoryImpl;

@ApplicationScoped
public class EmployeeRepositoryImpl extends DefaultRepositoryImpl<Employee> implements EmployeeRepository {

    public EmployeeRepositoryImpl () {
        super(Employee.class);
    }

    @Override
    public boolean decrementVacationSold ( Employee employee ,long sold ) {
        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            employee.setSoldVacation((int) (employee.getSoldVacation() - sold));
            entityManager.merge(employee);
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
