package co.com.pragma.usecase.registeruser;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;

    public Mono<User> execute(User user) {
        return userRepository.findByEmail(user.getEmail())
                .switchIfEmpty(userRepository.save(user))
                .cast(User.class);
    }

}
