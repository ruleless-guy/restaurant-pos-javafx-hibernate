package me.khun.smartrestaurant.application.exception;

import lombok.Getter;
import lombok.Setter;

public class DuplicateEntryInsertionException extends ApplicationException{

    @Getter
    @Setter
    private String entryValue;

    public DuplicateEntryInsertionException(String message) {
        super(message);
    }
}
