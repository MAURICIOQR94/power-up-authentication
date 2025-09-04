package co.com.pragma.usecase.registeruser;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;

class RegisterUserUseCaseTest {

    private UserRepository userRepository;
    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        registerUserUseCase = new RegisterUserUseCase(userRepository);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        User user = User.builder()
                .userId(UUID.randomUUID())
                .firstName("Mauricio")
                .lastName("Quintero")
                .birthDate(LocalDate.of(1994,1,30))
                .email("mauricio@email.com")
                .baseSalary(new BigDecimal(5000))
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.empty());

        when(userRepository.save(user)).thenReturn(Mono.just(user));

        StepVerifier.create(registerUserUseCase.execute(user))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldFailToRegisterUserIfRepositoryFails() {
        User user = User.builder()
                .userId(UUID.randomUUID())
                .firstName("Ana")
                .lastName("Trazo")
                .birthDate(LocalDate.of(1999,1,20))
                .email("Ana@email.com")
                .baseSalary(new BigDecimal(5000))
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.empty());

        when(userRepository.save(user)).thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(registerUserUseCase.execute(user))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && throwable.getMessage().equals("DB error"))
                .verify();

        verify(userRepository, times(1)).save(user);
    }
}