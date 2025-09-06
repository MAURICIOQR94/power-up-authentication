package co.com.pragma.usecase.login;

import co.com.pragma.model.common.exception.BusinessException;
import co.com.pragma.model.security.gateways.JwtUtilService;
import co.com.pragma.model.security.gateways.PasswordService;
import co.com.pragma.model.user.User;
import co.com.pragma.usecase.getuserbyemail.GetUserByEmailUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static co.com.pragma.model.common.enums.BusinessExceptionMessage.INCORRECT_PASSWORD;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private PasswordService passwordService;

    @Mock
    private JwtUtilService jwtUtilService;

    @Mock
    private GetUserByEmailUseCase getUserByEmailUseCase;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private User user;
    private final String email = "test@example.com";
    private final String rawPassword = "rawPassword123";
    private final String encodedPassword = "encodedPasswordHash";
    private final String token = "generatedJwtToken";

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email(email)
                .password(encodedPassword)
                .build();
    }

    @Test
    void givenCorrectCredentials_whenLogin_thenReturnsToken() {
        when(getUserByEmailUseCase.getUserByEmail(email)).thenReturn(Mono.just(user));
        when(passwordService.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtUtilService.generateToken(user)).thenReturn(token);

        StepVerifier.create(loginUseCase.login(email, rawPassword))
                .expectNext(token)
                .verifyComplete();
    }

    @Test
    void givenIncorrectPassword_whenLogin_thenThrowsBusinessException() {
        when(getUserByEmailUseCase.getUserByEmail(email)).thenReturn(Mono.just(user));
        when(passwordService.matches(rawPassword, encodedPassword)).thenReturn(false);

        StepVerifier.create(loginUseCase.login(email, rawPassword))
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                (throwable).getMessage().equals(INCORRECT_PASSWORD.getMessage()))
                .verify();
    }

    @Test
    void givenUserNotFound_whenLogin_thenHandlesEmptyMonoGracefully() {
        when(getUserByEmailUseCase.getUserByEmail(email)).thenReturn(Mono.empty());

        StepVerifier.create(loginUseCase.login(email, rawPassword))
                .verifyComplete();
    }
}