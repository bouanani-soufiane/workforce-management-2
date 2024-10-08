package ma.yc.repository.interfaces;

import ma.yc.entity.Employee;

import java.util.List;

public interface EmployeeRepository {
    boolean create ( Employee employee );

    boolean update ( Employee employee );

    boolean delete ( Employee employee );

    Employee findById ( int id );

    List<Employee> findAll ();

}
