package co.com.pragma.api.services.login;

import co.com.pragma.api.dto.LoginRequestDTO;
import co.com.pragma.api.dto.LoginResponseDTO;
import co.com.pragma.api.handlers.ValidatorHandler;
import co.com.pragma.common.exception.GeneralException;
import co.com.pragma.usecase.login.LoginUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.pragma.common.enums.GeneralExceptionMessage.INVALID_BODY_PARAMETER;

@Log4j2
@Component
@RequiredArgsConstructor
public class LoginHandler {

    private final LoginUseCase loginUseCase;
    private final ValidatorHandler validatorHandler;

    public Mono<ServerResponse> login(ServerRequest request) {
        log.info("Processing user login request");
        return request.bodyToMono(LoginRequestDTO.class)
                .switchIfEmpty(Mono.error(new GeneralException(INVALID_BODY_PARAMETER)))
                .doOnNext(validatorHandler::validateObject)
                .flatMap(req -> loginUseCase.login(req.getEmail(), req.getPassword()))
                .map(token -> LoginResponseDTO.builder().token(token).build())
                .flatMap(loginResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(loginResponse)
                );
    }

}
