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
        transaction.begin();
        try {
            entityManager.merge(employee);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
        }
        return false;
    }

    @Override
    public boolean delete ( Employee employee ) {
        transaction.begin();
        try {
            entityManager.remove(entityManager.find(Employee.class, employee.id()));
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
        }
        return false;
    }

    @Override
    public Employee findById ( int id ) {
        transaction.begin();
        try {
            Employee employee = entityManager.find(Employee.class, id);
            transaction.commit();
            return employee;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employee> findAll () {
        transaction.begin();
        try {
            List<Employee> employees = entityManager.createQuery("select e from Employee e").getResultList();
            transaction.commit();
            return employees;
        }catch (Exception e) {
            transaction.rollback();
        }
        return null;

    }
}
