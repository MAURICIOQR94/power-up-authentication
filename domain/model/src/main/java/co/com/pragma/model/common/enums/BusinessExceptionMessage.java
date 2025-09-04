package co.com.pragma.model.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionMessage {

    USER_NOT_FOUND("BE001", "User not found"),
    EMAIL_ALREADY_EXISTS("BE002", "Email already exists"),

    INCORRECT_PASSWORD("BE003", "The password is incorrect"),
    ROLE_NOT_FOUND("BE004", "User not found");

    private final String code;
    private final String message;
}
