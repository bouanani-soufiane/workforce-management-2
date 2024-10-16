import ma.yc.entity.Employee;
import ma.yc.repository.EmployeeRepository;
import ma.yc.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Default Employee service test")
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Nested
    @DisplayName("findAll() method tests")
    class FindAll {
        @Test
        @DisplayName("sould return all test if exists")
        void shouldReturn_AllEmployees () {
            List<Employee> expected = List.of(new Employee().setJobTitle(""), new Employee().setJobTitle(""));
            when(employeeRepository.findAll()).thenReturn(expected);
            List<Employee> actual = employeeService.findAll();

            assertEquals(expected.size(), actual.size());
            assertIterableEquals(expected, actual);

            verify(employeeRepository).findAll();
        }

        @Test
        @DisplayName("should return no employee exist")
        void shouldReturn_NoEmployee () {
            List<Employee> expected = List.of();
            when(employeeRepository.findAll()).thenReturn(expected);
            List<Employee> actual = employeeService.findAll();
            assertEquals(0, actual.size());
            assertIterableEquals(expected, actual);
            verify(employeeRepository).findAll();
        }

    }
    @DisplayName("FindById() method tests")
    @Nested
    class FindById {
        @Test
        @DisplayName("should return e ")
        void shouldReturn_Employee () {
            Long id = 155L;
            Employee employee = new Employee();
            employee.setId(id);
            employee.setJobTitle("Developer");

            when(employeeRepository.findById(id)).thenReturn(employee);
            Employee actual = employeeService.findById(id);
            assertEquals(employee, actual, "The returned employee should match the expected employee");
            verify(employeeRepository).findById(id);
        }

        @Test
        @DisplayName("")
        void shouldThrowException_whenEmployeeNotFound () {
            Long id = 155L;
            when(employeeRepository.findById(id)).thenReturn(null);
            Employee actual = employeeService.findById(id);
            assertNull(actual, "The returned employee should be null when no employee exists with the given ID.");
            verify(employeeRepository).findById(id);
        }
    }

}
