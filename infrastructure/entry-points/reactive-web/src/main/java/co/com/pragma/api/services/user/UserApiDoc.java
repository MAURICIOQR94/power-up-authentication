package co.com.pragma.api.services.user;

import co.com.pragma.api.dto.common.ErrorDTO;
import co.com.pragma.api.dto.UserRequestDTO;
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

public sealed class UserApiDoc permits UserRouterRest {

    public static final String BUSINESS_ERROR = "Business Error";
    public static final String TECHNICAL_ERROR = "Technical Error";
    public static final String SUCCESS = "Success";
    public static final String ACCEPT_HEADER = "Accept Header";
    public static final String ACCEPT = "Accept";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String ACCESS_DENIED = "Not Authenticated";

    protected Consumer<Builder> getByEmail() {
        return ops -> ops.tag("authentication")
                .operationId("getByEmail").summary("Get user by email")
                .description("Get user by email").tags(new String[]{"users"})
                .parameter(createHeader(
                        String.class, ACCEPT, ACCEPT_HEADER, APPLICATION_JSON_VALUE
                ))
                .parameter(createQuery(String.class, "email", "email"))
                .response(responseBuilder().responseCode("200").description(SUCCESS)
                        .content(
                                contentBuilder()
                                        .schema(schemaBuilder())
                                        .example(exampleResponse())
                        )
                )
                .response(responseBuilder().responseCode("401").description(ACCESS_DENIED)
                        .implementation(ErrorDTO.class))
                .response(responseBuilder().responseCode("404").description(USER_NOT_FOUND)
                        .implementation(ErrorDTO.class))
                .response(responseBuilder().responseCode("500").description(TECHNICAL_ERROR)
                        .implementation(ErrorDTO.class));
    }

    protected Consumer<Builder> save() {
        return ops -> ops.tag("authentication")
                .operationId("save").summary("Create user")
                .description("Create user").tags(new String[]{"users"})
                .parameter(createHeader(
                        String.class, ACCEPT, ACCEPT_HEADER, APPLICATION_JSON_VALUE
                ))
                .requestBody(requestBodyBuilder().implementation(UserRequestDTO.class))
                .response(responseBuilder().responseCode("200").description(SUCCESS)
                        .content(
                                contentBuilder()
                                        .schema(schemaBuilder())
                                        .example(exampleResponse())
                        )
                )
                .response(responseBuilder().responseCode("401").description(ACCESS_DENIED)
                        .implementation(ErrorDTO.class))
                .response(responseBuilder().responseCode("404").description(USER_NOT_FOUND)
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
                    "email": "admin1@gmail.com",
                    "firstName": "Arturo",
                    "lastName": "Sanchez"
                }
                """);
    }
}
