import ma.yc.repository.VacationRepository;
import ma.yc.service.VacationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VacationServiceTest {
    @Mock
    private VacationRepository vacationRepository;

    @InjectMocks
    private VacationService vacationService;


}
