package co.com.pragma.usecase.registeruser;

import co.com.pragma.model.role.Role;
import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.model.security.gateways.PasswordService;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
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
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    private static final String ROLE_NAME = "CLIENTE";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .id(1L)
                .name(ROLE_NAME)
                .description("admin")
                .build();

        user = User.builder()
                .userId(UUID.randomUUID())
                .firstName("Mauricio")
                .lastName("Quintero")
                .birthDate(LocalDate.of(1994, 1, 30))
                .email("mauricio@email.com")
                .password(("Password12345"))
                .baseSalary(new BigDecimal(5000))
                .role(role)
                .build();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        when(passwordService.encode("Password12345")).thenReturn("Password12345");
        when(roleRepository.findByName(ROLE_NAME)).thenReturn(Mono.just(role));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.empty());
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        StepVerifier.create(registerUserUseCase.execute(user))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldFailToRegisterUserIfRepositoryFails() {
        when(passwordService.encode("Password12345")).thenReturn("Password12345");
        when(roleRepository.findByName(ROLE_NAME)).thenReturn(Mono.just(role));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.empty());
        when(userRepository.save(user)).thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(registerUserUseCase.execute(user))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && throwable.getMessage().equals("DB error"))
                .verify();

        verify(userRepository, times(1)).save(user);
    }
}