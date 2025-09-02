package co.com.pragma.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalExceptionMessage {

    INTERNAL_SERVER_ERROR("TE001", "Internal server error"),
    JSON_PROCESSING("TE002", "Error processing request for logs"),

    USER_FIND_BY_EMAIL("TE003", "Error getting user by email"),
    USER_SAVE("TE004", "Error saving user"),
    USER_UPDATE("TE005", "Error updating user"),
    USER_DELETE("TE006", "Error deleting user");

    private final String code;
    private final String message;

}
