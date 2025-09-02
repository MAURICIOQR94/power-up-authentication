package co.com.pragma.r2dbc.user;

import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.user.entity.UserEntity;
import co.com.pragma.r2dbc.user.entity.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @InjectMocks
    private UserRepositoryAdapter repositoryAdapter;

    @Mock
    private IUserRepository repository;

    @Mock
    private UserMapper mapper;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .firstName("Mauricio")
                .lastName("Quintero")
                .birthDate(LocalDate.of(1994, 1, 30))
                .address("Cra 10 # 5 -55")
                .phone("3113958043")
                .email("mauroqr94@gmail.com")
                .baseSalary(BigDecimal.valueOf(50000.0))
                .build();

        userEntity = UserEntity.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .phone(user.getPhone())
                .email(user.getEmail())
                .baseSalary(user.getBaseSalary())
                .build();

    }

    @Test
    void mustSaveValue() {
        when(repository.save(any())).thenReturn(Mono.just(userEntity));
        when(mapper.toEntity(any())).thenReturn(user);

        StepVerifier.create(repositoryAdapter.save(user))
                .expectNext(user)
                .verifyComplete();
    }
}
