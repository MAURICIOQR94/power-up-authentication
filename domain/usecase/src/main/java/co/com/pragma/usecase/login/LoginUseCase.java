package co.com.pragma.usecase.login;

import co.com.pragma.model.common.exception.BusinessException;
import co.com.pragma.model.security.gateways.JwtUtilService;
import co.com.pragma.model.security.gateways.PasswordService;
import co.com.pragma.model.user.User;
import co.com.pragma.usecase.getuserbyemail.GetUserByEmailUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.pragma.model.common.enums.BusinessExceptionMessage.INCORRECT_PASSWORD;

@RequiredArgsConstructor
public class LoginUseCase {

    private final PasswordService passwordEncoder;
    private final JwtUtilService jwtUtilService;
    private final GetUserByEmailUseCase getUserByEmailUseCase;

    public Mono<String> login(String email, String password) {
        return getUserByEmailUseCase.getUserByEmail(email)
                .flatMap(user -> validateCredentials(user, password));
    }

    private Mono<String> validateCredentials(User user, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return Mono.error(new BusinessException(INCORRECT_PASSWORD));
        }
        String token = jwtUtilService.generateToken(user);
        return Mono.just(token);
    }

}
