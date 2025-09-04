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
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public non-sealed class UserRouterRest extends UserApiDoc {

    private final ApiProperties apiProperties;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler handler) {
        return SpringdocRouteBuilder.route()
                .GET(apiProperties.basePath(),
                        accept(MediaType.APPLICATION_JSON),
                        handler::getByEmail,
                        getByEmail())
                .build()
                .and(SpringdocRouteBuilder.route()
                        .POST(apiProperties.basePath(),
                                accept(MediaType.APPLICATION_JSON),
                                handler::save,
                                save()
                        )
                        .build()
                );
    }
}
