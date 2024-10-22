import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import ma.yc.entity.JobOffer;
import ma.yc.exception.EntityNotFoundException;
import ma.yc.exception.InvalidedRequestException;
import ma.yc.repository.JobOfferRepository;
import ma.yc.service.impl.JobOfferServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Job Offer service test")
public class JobOfferServiceTest {

    @Mock
    private JobOfferRepository jobOfferRepository;

    @Spy
    private Validator validator;

    @InjectMocks
    private JobOfferServiceImpl jobOfferService;

    private JobOffer createTestJobOffer ( Long id ) {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(id);
        jobOffer.setTitle("Software Engineer");
        jobOffer.setDescription("Job Description");
        return jobOffer;
    }

    @Nested
    @DisplayName("findAll() method tests")
    class FindAll {
        @Test
        @DisplayName("should return all job offers if they exist")
        void shouldReturn_AllJobOffers () {
            List<JobOffer> expected = List.of(createTestJobOffer(1L), createTestJobOffer(2L));
            when(jobOfferRepository.findAll()).thenReturn(expected);
            List<JobOffer> actual = jobOfferService.findAll();

            assertEquals(expected.size(), actual.size());
            assertIterableEquals(expected, actual);
            verify(jobOfferRepository).findAll();
        }

        @Test
        @DisplayName("should return no job offers if none exist")
        void shouldReturn_NoJobOffers () {
            List<JobOffer> expected = List.of();
            when(jobOfferRepository.findAll()).thenReturn(expected);
            List<JobOffer> actual = jobOfferService.findAll();

            assertEquals(0, actual.size());
            assertIterableEquals(expected, actual);
            verify(jobOfferRepository).findAll();
        }
    }

    @Nested
    @DisplayName("findById() method tests")
    class FindById {
        @Test
        @DisplayName("should return job offer when found")
        void shouldReturn_JobOffer () {
            Long id = 155L;
            JobOffer jobOffer = createTestJobOffer(id);

            when(jobOfferRepository.findById(id)).thenReturn(Optional.of(jobOffer));
            JobOffer actual = jobOfferService.findById(id);

            assertEquals(jobOffer, actual);
            verify(jobOfferRepository).findById(id);
        }

        @Test
        @DisplayName("should throw exception when job offer not found")
        void shouldThrowException_whenJobOfferNotFound () {
            Long id = 1L;
            when(jobOfferRepository.findById(id)).thenReturn(Optional.empty());
            var exception = assertThrows(EntityNotFoundException.class, () -> jobOfferService.findById(id));

            assertEquals("job offer" + " with id " + id + " not found", exception.getMessage());
            verify(jobOfferRepository).findById(id);
        }
    }

    @Nested
    @DisplayName("create() method tests")
    class Create {
        @Test
        @DisplayName("should create job offer with valid data")
        void shouldCreate_JobOffer () {
            JobOffer jobOffer = createTestJobOffer(null);
            when(validator.validate(jobOffer)).thenReturn(Set.of());
            when(jobOfferRepository.create(jobOffer)).thenReturn(true);

            boolean actual = jobOfferService.create(jobOffer);

            assertTrue(actual);
            verify(jobOfferRepository).create(jobOffer);
        }

        @Test
        @DisplayName("should throw exception when job offer is invalid")
        void shouldThrowException_whenJobOfferIsInvalid () {
            JobOffer jobOffer = createTestJobOffer(null);

            ConstraintViolation<JobOffer> violation = Mockito.mock(ConstraintViolation.class);
            Path path = Mockito.mock(Path.class);

            when(violation.getPropertyPath()).thenReturn(path);
            when(path.toString()).thenReturn("title"); // or any property you expect to validate
            when(violation.getMessage()).thenReturn("Job offer title must not be null");

            lenient().when(validator.validate(jobOffer)).thenReturn(Set.of(violation));

            var exception = assertThrows(InvalidedRequestException.class, () -> jobOfferService.create(jobOffer));

            assertEquals("error jobOffer validation", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("update() method tests")
    class Update {
        @Test
        @DisplayName("should update job offer with valid data")
        void shouldUpdate_JobOffer () {
            JobOffer jobOffer = createTestJobOffer(1L);
            lenient().when(validator.validate(jobOffer)).thenReturn(Set.of());
            when(jobOfferRepository.update(jobOffer)).thenReturn(true);

            boolean actual = jobOfferService.update(jobOffer);

            assertTrue(actual);
            verify(jobOfferRepository).update(jobOffer);
        }

        @Test
        @DisplayName("should throw exception when updating invalid job offer")
        void shouldThrowException_whenUpdatingInvalidJobOffer () {
            JobOffer jobOffer = createTestJobOffer(null); // create an invalid JobOffer

            ConstraintViolation<JobOffer> violation = Mockito.mock(ConstraintViolation.class);
            Path path = Mockito.mock(Path.class);

            when(violation.getPropertyPath()).thenReturn(path);
            when(path.toString()).thenReturn("title"); // Adjust to the property you're validating
            when(violation.getMessage()).thenReturn("Job offer title must not be null");

            lenient().when(validator.validate(jobOffer)).thenReturn(Set.of(violation));

            var exception = assertThrows(InvalidedRequestException.class, () -> jobOfferService.update(jobOffer));

            assertEquals("error jobOffer validation", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("delete() method tests")
    class Delete {
        @Test
        @DisplayName("should delete job offer when it exists")
        void shouldDelete_JobOffer () {
            Long id = 1L;
            JobOffer jobOffer = createTestJobOffer(id);
            lenient().when(jobOfferRepository.findById(id)).thenReturn(Optional.of(jobOffer));
            lenient().when(jobOfferRepository.delete(jobOffer)).thenReturn(true);

            boolean result = jobOfferService.delete(jobOffer);

            assertTrue(result);
            verify(jobOfferRepository).delete(jobOffer);
        }

        @Test
        @DisplayName("should throw exception when job offer to delete is not found")
        void shouldThrowException_whenJobOfferNotFound () {
            Long id = 1L;

            when(jobOfferRepository.findById(id)).thenReturn(Optional.empty());

            JobOffer jobOffer = new JobOffer();
            jobOffer.setId(id);

            var exception = assertThrows(EntityNotFoundException.class, () -> jobOfferService.delete(jobOffer));

            assertEquals("Job offer with id " + id + " not found", exception.getMessage());

            // Verify that delete is never called on the repository
            verify(jobOfferRepository, never()).delete(any(JobOffer.class));
            verify(jobOfferRepository).findById(id);
        }
    }

    @Nested
    @DisplayName("Spy and Dummy tests")
    class SpyAndDummy {
        @Test
        @DisplayName("should use spy to override a method")
        void shouldUseSpy_toOverrideMethod () {
            JobOffer jobOffer = createTestJobOffer(1L);
            doReturn(true).when(jobOfferService).create(jobOffer);

            boolean result = jobOfferService.create(jobOffer);

            assertTrue(result);
            verify(jobOfferService).create(jobOffer);
        }

        @Test
        @DisplayName("should use dummy object for method argument")
        void shouldUseDummy_forMethodArgument () {
            JobOffer dummy = new JobOffer();
            dummy.setId(1L);
            dummy.setTitle("Dummy Title");
            dummy.setDescription("Dummy Description");

            boolean result = jobOfferService.delete(dummy);

            assertFalse(result);
            verify(jobOfferRepository, never()).delete(any(JobOffer.class));
        }
    }

    @Nested
    @DisplayName("Double tests")
    class DoubleTests {
        @Test
        @DisplayName("should use mock to verify method calls")
        void shouldUseMock_toVerifyMethodCalls () {
            JobOfferRepository mockRepository = Mockito.mock(JobOfferRepository.class);
            Validator mockValidator = Mockito.mock(Validator.class);

            JobOfferServiceImpl service = new JobOfferServiceImpl(mockRepository, mockValidator);

            JobOffer jobOffer = createTestJobOffer(1L);
            when(mockRepository.findById(1L)).thenReturn(Optional.of(jobOffer));
            when(mockRepository.update(jobOffer)).thenReturn(true);

            service.update(jobOffer);

            verify(mockRepository).findById(1L);
            verify(mockRepository).update(jobOffer);
        }
    }
}