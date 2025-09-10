package co.com.pragma.usecase.getuserbyemail;

import co.com.pragma.model.common.exception.BusinessException;
import co.com.pragma.model.role.Role;
import co.com.pragma.model.role.gateways.RoleRepository;
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

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserByEmailUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private GetUserByEmailUseCase getUserByEmailUseCase;

    private User user;
    private Role role;


    @BeforeEach
    void setUp() {
        role =Role.builder().id(1L).name("ADMIN").description("admin").build();

        user = User.builder()
                .userId(UUID.randomUUID())
                .firstName("Mauricio")
                .lastName("Quintero")
                .birthDate(LocalDate.of(1994,1,30))
                .email("mauricio@email.com")
                .baseSalary(5000.0)
                .role(role)
                .build();
    }

    @Test
    void shouldReturnUserWhenEmailExists() {

        when(userRepository.findByEmail("mauricio@email.com")).thenReturn(Mono.just(user));
        when(roleRepository.findById(1L)).thenReturn(Mono.just(role));

        StepVerifier.create(getUserByEmailUseCase.getUserByEmail("mauricio@email.com"))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository, times(1)).findByEmail("mauricio@email.com");
    }

    @Test
    void shouldReturnEmptyWhenEmailNotFound() {
        when(userRepository.findByEmail("no@existe.com")).thenReturn(Mono.empty());

        StepVerifier.create(getUserByEmailUseCase.getUserByEmail("no@existe.com"))
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException && throwable.getMessage().equals("User not found")
                ).verify();

        verify(userRepository, times(1)).findByEmail("no@existe.com");
    }

}