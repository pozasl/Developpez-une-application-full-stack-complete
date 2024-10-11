package com.openclassrooms.mdd.common.exception;

public class ResourceNotFoundException extends RuntimeException {

    public static final String DEFAUL_ERR_MSG = "No such resource";

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public ResourceNotFoundException() {
        this(DEFAUL_ERR_MSG);
    }
    
}
