package com.openclassrooms.mdd.common.exception;

/**
 * Exception thrown when a user already exists
 */
public class UserAlreadyExistsException extends RuntimeException {

    public static final String DEFAUL_ERR_MSG = "A user with the same e-mail already exists";

    public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

    public UserAlreadyExistsException() {
        this(DEFAUL_ERR_MSG);
    }

}
