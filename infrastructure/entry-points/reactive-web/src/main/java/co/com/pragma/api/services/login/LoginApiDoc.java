package co.com.pragma.api.services.login;

import co.com.pragma.api.dto.*;
import org.springdoc.core.fn.builders.operation.Builder;

import java.util.function.Consumer;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.exampleobject.Builder.exampleOjectBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class LoginApiDoc {

    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String FORBIDDEN = "Forbidden";
    public static final String BUSINESS_ERROR = "Business Error";
    public static final String TECHNICAL_ERROR = "Technical Error";
    public static final String SUCCESS = "Success";
    public static final String ACCEPT_HEADER = "Accept Header";
    public static final String ACCEPT = "Accept";

    protected Consumer<Builder> login() {
        return ops -> ops.tag("authentication")
                .operationId("login").summary("Login user")
                .description("Login user").tags(new String[]{"users"})
                .parameter(createHeader(
                        String.class, ACCEPT, ACCEPT_HEADER, APPLICATION_JSON_VALUE
                ))
                .requestBody(requestBodyBuilder().implementation(LoginRequestDTO.class))
                .response(responseBuilder().responseCode("200").description(SUCCESS)
                        .implementation(ResponseDTO.class)
                        .content(
                                contentBuilder()
                                        .schema(schemaBuilder().implementation(LoginResponseDTO.class))
                                        .example(exampleResponse())
                        )
                )
                .response(responseBuilder().responseCode("401").description(UNAUTHORIZED)
                        .implementation(ErrorDTO.class))
                .response(responseBuilder().responseCode("403").description(FORBIDDEN)
                        .implementation(ErrorDTO.class))
                .response(responseBuilder().responseCode("409").description(BUSINESS_ERROR)
                        .implementation(ErrorDTO.class))
                .response(responseBuilder().responseCode("500").description(TECHNICAL_ERROR)
                        .implementation(ErrorDTO.class));
    }

    private <T> org.springdoc.core.fn.builders.parameter.Builder createHeader(Class<T> clazz,
                                                                              String name,
                                                                              String description,
                                                                              String example) {
        return parameterBuilder().in(HEADER).implementation(clazz).required(true).name(name).description(description)
                .example(example);
    }

    private <T> org.springdoc.core.fn.builders.parameter.Builder createQuery(Class<T> clazz,
                                                                             String name,
                                                                             String description) {
        return parameterBuilder().in(QUERY).implementation(clazz).required(true).name(name).description(description);
    }

    private org.springdoc.core.fn.builders.exampleobject.Builder exampleResponse() {
        return exampleOjectBuilder().value("""
                {
                    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmMGU1ZDIzNC1hNjcyLTRiNjYtODQ4OS0wMGMwZDM4NTgzODMiLCJyb2xlIjoiQ0xJRU5URSIsImRvY3VtZW50TnVtYmVyIjoiMTA1MzgzMjI4NiIsImlhdCI6MTc1NzAyNTIwMCwiZXhwIjoxNzU3MDI4ODAwfQ.aXslZqo3UvyxfhTuewGnvUHl4PDAcoH3Y3TkwfdCjt4"
                }
                """);
    }

}
