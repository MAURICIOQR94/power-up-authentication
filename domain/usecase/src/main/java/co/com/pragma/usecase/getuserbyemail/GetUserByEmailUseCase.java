package co.com.pragma.usecase.getuserbyemail;

import co.com.pragma.model.common.exception.BusinessException;
import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.pragma.model.common.enums.BusinessExceptionMessage.USER_NOT_FOUND;

@RequiredArgsConstructor
public class GetUserByEmailUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new BusinessException(USER_NOT_FOUND)))
                .flatMap(user -> roleRepository.findById(user.getRole().getId())
                        .map(role -> {
                            user.setRole(role);
                            return user;
                        }));
    }


}
