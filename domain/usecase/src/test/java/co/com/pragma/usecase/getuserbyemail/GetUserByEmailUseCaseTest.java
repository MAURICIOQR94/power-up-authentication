package co.com.pragma.usecase.getuserbyemail;

import co.com.pragma.model.common.exception.BusinessException;
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

class GetUserByEmailUseCaseTest {

    private User user;
    private UserRepository userRepository;
    private GetUserByEmailUseCase getUserByEmailUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        getUserByEmailUseCase = new GetUserByEmailUseCase(userRepository);

        user = User.builder()
                .userId(UUID.randomUUID())
                .firstName("Mauricio")
                .lastName("Quintero")
                .birthDate(LocalDate.of(1994,1,30))
                .email("mauricio@email.com")
                .baseSalary(new BigDecimal(5000))
                .build();
    }

    @Test
    void shouldReturnUserWhenEmailExists() {

        when(userRepository.findByEmail("mauricio@email.com"))
                .thenReturn(Mono.just(user));

        StepVerifier.create(getUserByEmailUseCase.getUserByEmail("mauricio@email.com"))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository, times(1)).findByEmail("mauricio@email.com");
    }

    @Test
    void shouldReturnEmptyWhenEmailNotFound() {
        when(userRepository.findByEmail("no@existe.com"))
                .thenReturn(Mono.empty());

        StepVerifier.create(getUserByEmailUseCase.getUserByEmail("no@existe.com"))
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException && throwable.getMessage().equals("User not found")
                ).verify();

        verify(userRepository, times(1)).findByEmail("no@existe.com");
    }


}