package ma.yc;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.yc.util.EntityManagerProvider;

public class Main {
    public static void main ( String[] args ) {

        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
//
//        Weld weld = new Weld();
//        try (WeldContainer container = weld.initialize()) {
//            EmployeeService employeeService = container.select(EmployeeService.class).get();
//
//                Employee em = employeeService.findById(1);
//
//
//        }
    }
}
