import jakarta.validation.Validator;
import ma.yc.entity.Employee;
import ma.yc.entity.Vacation;
import ma.yc.enums.VacationStatus;
import ma.yc.exception.EntityNotFoundException;
import ma.yc.repository.EmployeeRepository;
import ma.yc.repository.VacationRepository;
import ma.yc.service.impl.VacationServiceImpl;
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
@DisplayName("Vacation service test")

public class VacationServiceTest {
    @Mock
    private VacationRepository vacationRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private VacationServiceImpl vacationService;

    private Vacation createTestVacation ( Long id ) {
        Vacation vacation = new Vacation();
        vacation.setId(id);
        vacation.setStartDate(LocalDate.now());
        vacation.setEndDate(LocalDate.now().plusDays(10));
        vacation.setReason("REASON");
        vacation.setVacationStatus(VacationStatus.PENDING);
        return vacation;
    }

    @Nested
    @DisplayName("findAll() method tests")
    class FindAll {
        @Test
        @DisplayName("should return all vacations if exist")
        void shouldReturn_AllVacations () {
            List<Vacation> expected = List.of(createTestVacation(1L), createTestVacation(2L));
            when(vacationRepository.findAll()).thenReturn(expected);
            List<Vacation> actual = vacationService.findAll();

            assertEquals(expected.size(), actual.size());
            assertIterableEquals(expected, actual);
            verify(vacationRepository).findAll();
        }

        @Test
        @DisplayName("should return no vacation if none exist")
        void shouldReturn_NoVacation () {
            List<Vacation> expected = List.of();
            when(vacationRepository.findAll()).thenReturn(expected);
            List<Vacation> actual = vacationService.findAll();

            assertEquals(0, actual.size());
            assertIterableEquals(expected, actual);
            verify(vacationRepository).findAll();
        }
    }

    @Nested
    @DisplayName("findById() method tests")
    class FindById {
        @Test
        @DisplayName("should return vacation")
        void shouldReturn_Vacation () {
            Long id = 155L;
            Vacation vacation = createTestVacation(id);

            when(vacationRepository.findById(id)).thenReturn(Optional.of(vacation));
            Vacation actual = vacationService.findById(id);

            assertEquals(vacation, actual, "The returned vacation should match the expected vacation");
            verify(vacationRepository).findById(id);
        }

        @Test
        @DisplayName("should throw exception when vacation not found")
        void shouldThrowException_whenVacationNotFound () {
            Long id = 1L;
            when(vacationRepository.findById(id)).thenReturn(Optional.empty());
            var exception = assertThrows(EntityNotFoundException.class, () -> vacationService.findById(id));

            assertEquals("vacation with id " + id + " not found", exception.getMessage());
            verify(vacationRepository).findById(id);
        }
    }

    @Nested
    @DisplayName("create() method tests")
    class Create {
        @Test
        @DisplayName("should create vacation when giving valid data and sufficient days")
        void shouldCreate_Vacation () {
            Vacation vacation = createTestVacation(null);
            Employee employee = new Employee();
            employee.setSoldVacation(15);
            vacation.setEmployee(employee);

            lenient().when(validator.validate(vacation)).thenReturn(Set.of());
            when(vacationRepository.create(vacation)).thenReturn(true);
            lenient().when(employeeRepository.decrementVacationSold(employee, employee.getSoldVacation())).thenReturn(true);

            boolean actual = vacationService.create(vacation);

            assertTrue(actual, "The vacation should be created successfully");
            verify(vacationRepository).create(vacation);
        }

        @Test
        @DisplayName("should throw exception when giving not enough days")
        void shouldThrowException_whenGivingNotEnoughDays () {
            Vacation vacation = createTestVacation(null);
            Employee employee = new Employee();
            employee.setSoldVacation(1);
            vacation.setEmployee(employee);

            lenient().when(vacationRepository.create(vacation)).thenReturn(false);
            lenient().when(employeeRepository.decrementVacationSold(employee, employee.getSoldVacation())).thenReturn(false);

            var exception = assertThrows(RuntimeException.class, () -> vacationService.create(vacation));

            assertEquals("not enough days left in vacation", exception.getMessage());

        }
    }

    @Nested
    @DisplayName("delete() method tests")
    class Delete {

        @Test
        @DisplayName("should delete vacancy when exists")
        void shouldDelete_Employee () {
            Long id = 1L;
            Vacation vacation = createTestVacation(id);
            lenient().when(vacationRepository.findById(id)).thenReturn(Optional.of(vacation));
            lenient().when(vacationRepository.delete(vacation)).thenReturn(true);
            boolean result = vacationService.delete(vacation);
            assertTrue(result);
            verify(vacationRepository).delete(vacation);
        }

        @Test
        @DisplayName("should throw exception when vacancy to delete is not found")
        void shouldThrowException_whenEmployeeNotFound () {
            Long id = 1L;

            when(vacationRepository.findById(id)).thenReturn(Optional.empty());
            var exception = assertThrows(EntityNotFoundException.class, () -> vacationService.findById(id));

            assertEquals("vacation" + " with id " + id + " not found", exception.getMessage());

            verify(vacationRepository, never()).delete(any(Vacation.class));
            verify(vacationRepository).findById(id);

        }
    }
}