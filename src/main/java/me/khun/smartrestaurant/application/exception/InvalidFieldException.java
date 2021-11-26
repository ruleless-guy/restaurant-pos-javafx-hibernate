package me.khun.smartrestaurant.application.exception;

import lombok.Getter;
import lombok.Setter;

public class InvalidFieldException extends ApplicationException{

    @Getter
    @Setter
    private String fieldName;

    @Getter
    @Setter
    private Object source;

    public InvalidFieldException() {
        this(null);
    }

    public InvalidFieldException(String message) {
        this(message, null, null);
    }

    public InvalidFieldException(String message, String fieldName) {
        this(message, null, fieldName);
    }

    public InvalidFieldException(String message, Object source) {
        this(message, source, null);
    }

    public InvalidFieldException(String message, Object source, String fieldName) {
        super(message);
        this.source = source;
        this.fieldName = fieldName;
    }

}
