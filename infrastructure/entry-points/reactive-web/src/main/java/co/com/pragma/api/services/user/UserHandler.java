package co.com.pragma.api.services.user;

import co.com.pragma.api.dto.UserRequestDTO;
import co.com.pragma.api.handlers.ValidatorHandler;
import co.com.pragma.api.dto.ResponseDTO;
import co.com.pragma.api.mapper.UserDTOMapper;
import co.com.pragma.api.util.ParamsUtil;
import co.com.pragma.common.exception.GeneralException;
import co.com.pragma.usecase.getuserbyemail.GetUserByEmailUseCase;
import co.com.pragma.usecase.registeruser.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.pragma.common.enums.GeneralExceptionMessage.INVALID_BODY_PARAMETER;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final TransactionalOperator transactionalOperator;
    private final ValidatorHandler validatorHandler;
    private final UserDTOMapper mapper;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        log.info("Processing user registration request");
        return serverRequest.bodyToMono(UserRequestDTO.class)
                .switchIfEmpty(Mono.error(new GeneralException(INVALID_BODY_PARAMETER)))
                .doOnNext(validatorHandler::validateObject)
                .map(mapper::toEntity)
                .flatMap(user -> registerUserUseCase.execute(user)
                        .doOnSuccess(u ->log.info("User successfully registered: {}", u.getEmail()))
                )
                .map(mapper::toDto)
                .flatMap(ResponseDTO::success)
                .as(transactionalOperator::transactional);
    }

    public Mono<ServerResponse> getByEmail(ServerRequest serverRequest) {
        return ParamsUtil.getEmailQueryParam(serverRequest)
                .doOnNext(email -> log.info("Processing get user by email request for: {}", email))
                .flatMap(getUserByEmailUseCase::getUserByEmail)
                .doOnSuccess(user -> log.info("User found for email: {}", user.getEmail()))
                .flatMap(ResponseDTO::success);
    }

}
