package co.com.pragma.api.services.user;

import co.com.pragma.api.dto.UserRequestDTO;
import co.com.pragma.api.mapper.UserDTOMapper;
import co.com.pragma.usecase.getuserbyemail.GetUserByEmailUseCase;
import co.com.pragma.usecase.registeruser.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final UserDTOMapper mapper;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequestDTO.class)
                .map(mapper::toEntity)
                .flatMap(user -> registerUserUseCase.execute(user)
                )
                .map(mapper::toDto)
                .flatMap(dto -> ServerResponse
                        .ok()
                        .header("X-Custom-Header", "UserCreated")
                        .bodyValue(dto)
                );
    }

    public Mono<ServerResponse> getByEmail(ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.queryParam("email"))
                .flatMap(getUserByEmailUseCase::getUserByEmail) // recibe el email
                .map(mapper::toDto) // si necesitas mapear a DTO
                .flatMap(dto -> ServerResponse.ok()
                        .header("X-Custom-Header", "UserFound")
                        .bodyValue(dto)
                )
                .switchIfEmpty(ServerResponse.notFound().build()); // si no hay email o no existe el usuario
    }
}
