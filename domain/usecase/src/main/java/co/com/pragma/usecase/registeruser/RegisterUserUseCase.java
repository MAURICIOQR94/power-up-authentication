package co.com.pragma.usecase.registeruser;

import co.com.pragma.model.common.exception.BusinessException;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.pragma.model.common.enums.BusinessExceptionMessage.EMAIL_ALREADY_EXISTS;

@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;

    public Mono<User> execute(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existingUser -> Mono.error(new BusinessException(EMAIL_ALREADY_EXISTS)))
                .switchIfEmpty(userRepository.save(user))
                .cast(User.class);
    }

}
