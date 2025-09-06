package co.com.pragma.r2dbc.adapter;

import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
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

    @Mock
    private R2dbcEntityTemplate r2dbcEntityTemplate;

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
        when(mapper.toData(any(User.class))).thenReturn(userEntity);
        when(r2dbcEntityTemplate.insert(any(UserEntity.class))).thenReturn(Mono.just(userEntity));
        when(mapper.toEntity(any(UserEntity.class))).thenReturn(user);

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void findByIdShouldReturnUser() {
        when(repository.findByEmail(user.getEmail())).thenReturn(Mono.just(userEntity));
        when(mapper.toEntity(userEntity)).thenReturn(user);

        Mono<User> result = repositoryAdapter.findByEmail(user.getEmail());

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();


    }
}
