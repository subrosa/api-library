package com.subrosa.api.notification;

/**
 * Enumeration of general purpose API notification codes. All int values should start with 10.
 */
public enum GeneralCode implements Code {
    // CHECKSTYLE-OFF: JavadocVariable
    NOT_FOUND                   (1000000001, "Not found"),
    FORBIDDEN                   (1000000002, "Forbidden"),
    NOT_ACCEPTABLE              (1000000003, "Media type in Accept header not supported"),
    UNAUTHORIZED_FIELD_ACCESS   (1000000004, "Unauthorized field access"),
    MISSING_REQUIRED_FIELD      (1000010001, "Missing required field"),
    INVALID_FIELD_VALUE         (1000010002, "Invalid value for field"),
    INVALID_FIELD_LENGTH        (1000010004, "Invalid length for field"),
    INVALID_FIELD_FORMAT        (1000010005, "Invalid format for field"),
    INVALID_FIELD_CHARACTERS    (1000010006, "Invalid characters in field"),
    READ_ONLY_FIELD             (1000010007, "Cannot set read-only field"),
    INVALID_REQUEST_ENTITY      (1000010008, "Invalid request entity"),
    INTERNAL_ERROR              (1000020001, "Internal error"),
    DOMAIN_OBJECT_NOT_FOUND     (1000020002, "Domain object not found"),
    DESERIALIZATION_ERROR       (1000020003, "Error deserializing request"),
    FILE_CORRUPT                (1000020004, "File is corrupt"),
    FILE_TOO_LARGE              (1000020005, "File is too large"),
    FILE_ENCRYPTED              (1000020006, "File is encrypted"),
    FILE_NOT_FOUND              (1000020007, "File not found");
    // CHECKSTYLE-ON: JavadocVariable

    private int code;
    private String defaultMessage;

    /**
     * Constructs a GeneralNotificationCode.
     *
     * @param code the integer code
     * @param defaultMessage the default text message related to this code
     */
    GeneralCode(int code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }

}
