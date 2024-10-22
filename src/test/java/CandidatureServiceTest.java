import jakarta.validation.Validator;
import ma.yc.entity.Candidature;
import ma.yc.enums.ApplicationStatus;
import ma.yc.exception.EntityNotFoundException;
import ma.yc.repository.CandidatureRepository;
import ma.yc.repository.JobOfferRepository;
import ma.yc.service.impl.CandidatureServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Candidature service test")
public class CandidatureServiceTest {

    @Mock
    private CandidatureRepository candidatureRepository;

    @Mock
    private JobOfferRepository jobOfferRepository;

    @Spy
    private Validator validator;

    @InjectMocks
    private CandidatureServiceImpl candidatureService;

    private Candidature createTestCandidature(Long id) {
        Candidature candidature = new Candidature();
        candidature.setId(id);
        candidature.setName("John Doe");
        candidature.setEmail("john.doe@example.com");
        candidature.setSkills("Java, Spring");
        candidature.setApplicationStatus(ApplicationStatus.PENDING);
        candidature.setSubmissionDate(LocalDateTime.now());
        candidature.setResume("resume.pdf");
        return candidature;
    }

    @Nested
    @DisplayName("findAll() method tests")
    class FindAll {
        @Test
        @DisplayName("should return all candidatures if they exist")
        void shouldReturn_AllCandidatures() {
            List<Candidature> expected = List.of(createTestCandidature(1L), createTestCandidature(2L));
            when(candidatureRepository.findAll()).thenReturn(expected);
            List<Candidature> actual = candidatureService.findAll();

            assertEquals(expected.size(), actual.size());
            assertIterableEquals(expected, actual);
            verify(candidatureRepository).findAll();
        }

        @Test
        @DisplayName("should return no candidature if none exist")
        void shouldReturn_NoCandidatures() {
            List<Candidature> expected = List.of();
            when(candidatureRepository.findAll()).thenReturn(expected);
            List<Candidature> actual = candidatureService.findAll();

            assertEquals(0, actual.size());
            assertIterableEquals(expected, actual);
            verify(candidatureRepository).findAll();
        }
    }

    @Nested
    @DisplayName("findById() method tests")
    class FindById {
        @Test
        @DisplayName("should return candidature")
        void shouldReturn_Candidature() {
            Long id = 1L;
            Candidature candidature = createTestCandidature(id);

            when(candidatureRepository.findById(id)).thenReturn(Optional.of(candidature));
            Candidature actual = candidatureService.findById(id);

            assertEquals(candidature, actual, "The returned candidature should match the expected candidature");
            verify(candidatureRepository).findById(id);
        }

        @Test
        @DisplayName("should throw exception when candidature not found")
        void shouldThrowException_whenCandidatureNotFound() {
            Long id = 1L;
            when(candidatureRepository.findById(id)).thenReturn(Optional.empty());
            var exception = assertThrows(EntityNotFoundException.class, () -> candidatureService.findById(id));

            assertEquals("candidature with id " + id + " not found", exception.getMessage());
            verify(candidatureRepository).findById(id);
        }
    }

    @Nested
    @DisplayName("create() method tests")
    class Create {
        @Test
        @DisplayName("should create candidature when given valid data")
        void shouldCreate_Candidature() {
            Candidature candidature = createTestCandidature(null);

            when(validator.validate(candidature)).thenReturn(Set.of());
            when(candidatureRepository.create(candidature)).thenReturn(true);

            boolean actual = candidatureService.create(candidature);

            assertTrue(actual, "The candidature should be created successfully");
            verify(candidatureRepository).create(candidature);
            verify(validator).validate(candidature);
        }

        @Test
        @DisplayName("should not create candidature when given invalid data")
        void shouldNotCreate_Candidature() {
            Candidature candidature = createTestCandidature(null);
            Set<String> violations = Set.of("Invalid email", "Missing skills");

            doReturn(violations).when(validator).validate(candidature);
            boolean actual = candidatureService.create(candidature);

            assertFalse(actual, "The candidature should not be created");
            verify(candidatureRepository, never()).create(candidature);
            verify(validator).validate(candidature);
        }
    }

    @Nested
    @DisplayName("delete() method tests")
    class Delete {
        @Test
        @DisplayName("should delete candidature when exists")
        void shouldDelete_Candidature() {
            Long id = 1L;
            Candidature candidature = createTestCandidature(id);
            when(candidatureRepository.findById(id)).thenReturn(Optional.of(candidature));
            when(candidatureRepository.delete(candidature)).thenReturn(true);

            boolean result = candidatureService.delete(candidature);

            assertTrue(result);
            verify(candidatureRepository).delete(candidature);
            verify(candidatureRepository).findById(id);
        }

        @Test
        @DisplayName("should throw exception when candidature to delete is not found")
        void shouldThrowException_whenCandidatureNotFound() {
            Long id = 1L;
            Candidature candidature = createTestCandidature(id);
            when(candidatureRepository.findById(id)).thenReturn(Optional.empty());

            var exception = assertThrows(EntityNotFoundException.class, () -> candidatureService.delete(candidature));

            assertEquals("candidature with id " + id + " not found", exception.getMessage());
            verify(candidatureRepository, never()).delete(any(Candidature.class));
            verify(candidatureRepository).findById(id);
        }
    }
}