package co.com.pragma.api.services.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler handler) {
        return route(POST("/api/v1/usuarios"), handler::save)
                .andRoute(GET("/api/v1/usuarios"), handler::getByEmail);
    }
}
