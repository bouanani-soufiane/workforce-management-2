import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import ma.yc.entity.Employee;
import ma.yc.entity.FamillyAllowance;
import ma.yc.entity.valueObject.SocialSecurityNumber;
import ma.yc.exception.EntityNotFoundException;
import ma.yc.exception.InvalidedRequestException;
import ma.yc.repository.EmployeeRepository;
import ma.yc.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Default Employee service test")
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee createTestEmployee ( Long id ) {
        FamillyAllowance allowance = new FamillyAllowance();
        allowance.setSalary(8000);
        allowance.setChildrenCount(4);

        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("soufiane");
        employee.setEmail("soufiane@gmail.com");
        employee.setPhone("063934453");
        employee.setDepartement(null);
        employee.setPassword("1234");
        employee.setAddress("address1234");
        employee.setJobTitle("JAVA developer");
        employee.setBirthDate(LocalDate.parse("1990-05-15"));
        employee.setSecurityNumber(new SocialSecurityNumber("123-45-6789"));
        employee.setHireDate(LocalDate.now());
        employee.setSoldVacation(18);
        employee.setRole(false);
        employee.setFamillyAllowance(allowance);

        return employee;
    }


    @Nested
    @DisplayName("findAll() method tests")
    class FindAll {
        @Test
        @DisplayName("should return all test if exists")
        void shouldReturn_AllEmployees () {
            List<Employee> expected = List.of(createTestEmployee(1L), createTestEmployee(2L));
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
        @DisplayName("should return employee ")
        void shouldReturn_Employee () {
            Long id = 155L;
            Employee employee = createTestEmployee(id);

            when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
            Employee actual = employeeService.findById(id);
            assertEquals(employee, actual, "The returned employee should match the expected employee");
            verify(employeeRepository).findById(id);
        }

        @Test
        @DisplayName("should throw exception when employee not found")
        void shouldThrowException_whenEmployeeNotFound () {
            Long id = 1L;
            when(employeeRepository.findById(id)).thenReturn(Optional.empty());
            var exception = assertThrows(EntityNotFoundException.class, () -> employeeService.findById(id));
            assertEquals("employee" + " with id " + id + " not found", exception.getMessage());

            verify(employeeRepository).findById(id);
        }
    }


    @Nested
    @DisplayName("create() method tests")
    class Create {
        @Test
        @DisplayName("should create employee when giving valid data")
        void shouldCreate_Employee () {
            Employee expected = createTestEmployee(null);

            when(validator.validate(expected)).thenReturn(Set.of());
            when(employeeRepository.create(expected)).thenReturn(true);

            boolean actual = employeeService.create(expected);

            assertTrue(actual, "The returned employee should match the expected employee");
            verify(employeeRepository).create(expected);

        }

        @Test
        @DisplayName("should throw exception create employee when giving invalid data")
        void shouldThrow_ValidationError () {
            Employee invalidEmployee = new Employee();

            var mockPath = mock(Path.class);
            var mockViolation = mock(ConstraintViolation.class);

            when(mockPath.toString()).thenReturn("email");
            when(mockViolation.getPropertyPath()).thenReturn(mockPath);
            when(mockViolation.getMessage()).thenReturn("Email cannot be null");

            Set<ConstraintViolation<Employee>> violations = Set.of(mockViolation);
            when(validator.validate(invalidEmployee)).thenReturn(violations);

            var exception = assertThrows(InvalidedRequestException.class, () -> employeeService.create(invalidEmployee));
            assertTrue(exception.getMessage().contains("error employee validation"));
            verify(validator).validate(invalidEmployee);
        }
    }

    @Nested
    @DisplayName("update() method tests")
    class Update {
        @Test
        @DisplayName("should update employee when given valid data")
        void shouldUpdate_Employee () {
            Long id = 1L;
            Employee existingEmployee = createTestEmployee(id);
            Employee updatedEmployee = createTestEmployee(id);
            updatedEmployee.setJobTitle("Senior Developer");
            lenient().when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
            when(validator.validate(updatedEmployee)).thenReturn(Set.of());
            when(employeeRepository.update(updatedEmployee)).thenReturn(true);
            boolean actual = employeeService.update(updatedEmployee);
            assertTrue(actual, "The returned employee should match the expected employee");
            assertEquals("Senior Developer", updatedEmployee.getJobTitle(), "The job title should be updated");

            verify(validator).validate(updatedEmployee);
            verify(employeeRepository).update(updatedEmployee);
        }

        @Test
        @DisplayName("should throw validation error when invalid data is provided")
        void shouldThrow_ValidationError () {
            Long id = 1L;
            Employee invalidEmployee = createTestEmployee(id);
            invalidEmployee.setEmail(null);

            var mockPath = mock(Path.class);
            var mockViolation = mock(ConstraintViolation.class);

            when(mockPath.toString()).thenReturn("email");
            when(mockViolation.getPropertyPath()).thenReturn(mockPath);
            when(mockViolation.getMessage()).thenReturn("Email cannot be null");

            Set<ConstraintViolation<Employee>> violations = Set.of(mockViolation);
            when(validator.validate(invalidEmployee)).thenReturn(violations);

            var exception = assertThrows(InvalidedRequestException.class, () -> employeeService.update(invalidEmployee));
            assertTrue(exception.getMessage().contains("error employee validation"));

            verify(validator).validate(invalidEmployee);
            verify(employeeRepository, never()).update(invalidEmployee);
        }
    }

    @Nested
    @DisplayName("delete() method tests")
    class Delete {

        @Test
        @DisplayName("should delete employee when employee exists")
        void shouldDelete_Employee () {
            Long id = 1L;
            Employee existingEmployee = createTestEmployee(id);

            lenient().when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
            when(employeeRepository.delete(existingEmployee)).thenReturn(true);

            boolean actual = employeeService.delete(existingEmployee);

            assertTrue(actual, "The employee should be successfully deleted");
            verify(employeeRepository).delete(existingEmployee);
        }

        @Test
        @DisplayName("should throw exception when employee to delete is not found")
        void shouldThrowException_whenEmployeeNotFound () {
            Long id = 1L;
            when(employeeRepository.findById(id)).thenReturn(Optional.empty());

            var exception = assertThrows(EntityNotFoundException.class, () -> employeeService.findById(id));

            assertEquals("employee" + " with id " + id + " not found", exception.getMessage());

            verify(employeeRepository, never()).delete(any(Employee.class));
            verify(employeeRepository).findById(id);
        }
    }

}