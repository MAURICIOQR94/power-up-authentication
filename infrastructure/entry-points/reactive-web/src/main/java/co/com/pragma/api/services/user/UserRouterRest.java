package co.com.pragma.api.services.user;

import co.com.pragma.api.config.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@RequiredArgsConstructor
public non-sealed class UserRouterRest extends UserApiDoc {

    private static final String USERS = "/usuarios";

    private final ApiProperties apiProperties;

    @Bean
    public RouterFunction<ServerResponse> userRouterFunction(UserHandler handler) {
        return SpringdocRouteBuilder.route()
                .GET(apiProperties.basePath().concat(USERS),
                        accept(MediaType.APPLICATION_JSON),
                        handler::getByEmail,
                        getByEmail())
                .build()
                .and(SpringdocRouteBuilder.route()
                        .POST(apiProperties.basePath().concat(USERS),
                                accept(MediaType.APPLICATION_JSON),
                                handler::save,
                                save()
                        )
                        .build()
                );
    }
}
