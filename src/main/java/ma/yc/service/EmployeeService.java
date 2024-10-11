package ma.yc.service;

import ma.yc.entity.Employee;

import java.util.List;

public interface EmployeeService {
    boolean create ( Employee employee );

    boolean update ( Employee employee );

    boolean delete ( Employee employee );

    Employee findById ( Long id );

    List<Employee> findAll ();
}
