package co.com.pragma.api.services.login;

import co.com.pragma.api.config.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@RequiredArgsConstructor
public class LoginRouterRest  extends LoginApiDoc{

    private static final String LOGIN = "/login";

    private final ApiProperties apiProperties;

    @Bean
    public RouterFunction<ServerResponse> loginRouterFunction(LoginHandler handler) {
        return SpringdocRouteBuilder.route()
                .POST(apiProperties.basePath().concat(LOGIN),
                        accept(MediaType.APPLICATION_JSON),
                        handler::login,
                        login())
                .build();
    }
}
