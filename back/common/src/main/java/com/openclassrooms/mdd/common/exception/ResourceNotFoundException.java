package com.openclassrooms.mdd.common.exception;

/**
 * Exception thrown when a dynamic resource can't be found
 */
public class ResourceNotFoundException extends RuntimeException {

    public static final String DEFAUL_ERR_MSG = "No such resource";

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public ResourceNotFoundException() {
        this(DEFAUL_ERR_MSG);
    }
    
}
