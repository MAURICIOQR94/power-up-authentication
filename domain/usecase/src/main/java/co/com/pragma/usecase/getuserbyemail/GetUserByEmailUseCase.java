package co.com.pragma.usecase.getuserbyemail;

import co.com.pragma.model.common.enums.BusinessExceptionMessage;
import co.com.pragma.model.common.exception.BusinessException;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetUserByEmailUseCase {

    private final UserRepository userRepository;

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessExceptionMessage.USER_NOT_FOUND)));
    }


}
