package co.com.pragma.usecase.registeruser;

import co.com.pragma.model.common.exception.BusinessException;
import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.model.security.gateways.PasswordService;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static co.com.pragma.model.common.enums.BusinessExceptionMessage.EMAIL_ALREADY_EXISTS;

@RequiredArgsConstructor
public class RegisterUserUseCase {

    private static final String DEFAULT_ROLE = "CLIENTE";

    private final PasswordService passwordService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Mono<User> execute(User user) {
        user.setPassword(passwordService.encode(user.getPassword()));
        user.setUserId(UUID.randomUUID());

        return roleRepository.findByName(DEFAULT_ROLE)
                .map(role -> {
                    user.setRole(role);
                    return user;
                })
                .flatMap(roleSetUser ->
                        userRepository.findByEmail(user.getEmail())
                                .flatMap(existingUser -> Mono.<User>error(new BusinessException(EMAIL_ALREADY_EXISTS)))
                                .switchIfEmpty(userRepository.save(roleSetUser))
                                .cast(User.class)
                );
    }

}
