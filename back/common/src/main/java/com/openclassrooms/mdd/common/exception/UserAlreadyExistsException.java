package com.openclassrooms.mdd.common.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public static final String DEFAUL_ERR_MSG = "A user with the same e-mail already exists";

    UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }


    UserAlreadyExistsException() {
        this(DEFAUL_ERR_MSG);
    }

}
