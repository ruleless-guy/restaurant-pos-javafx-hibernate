package me.khun.smartrestaurant.application.exception;

public class LoginException extends ApplicationException{
    public LoginException(String message) {
        this(message, null);
    }

    public LoginException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
