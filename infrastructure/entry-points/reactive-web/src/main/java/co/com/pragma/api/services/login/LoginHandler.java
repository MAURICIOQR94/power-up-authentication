package co.com.pragma.api.services.login;

import co.com.pragma.api.dto.LoginRequestDTO;
import co.com.pragma.api.dto.LoginResponseDTO;
import co.com.pragma.api.dto.ResponseDTO;
import co.com.pragma.usecase.login.LoginUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class LoginHandler {

    private final LoginUseCase loginUseCase;

    public Mono<ServerResponse> login(ServerRequest request) {
        log.info("Processing user login request");
        return request.bodyToMono(LoginRequestDTO.class)
                .flatMap(req -> loginUseCase.login(req.getEmail(), req.getPassword()))
                .map(token -> LoginResponseDTO.builder().token(token).build())
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto)
                );
    }





}
