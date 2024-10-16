package com.openclassrooms.mdd.common.model;

import com.openclassrooms.mdd.api.model.ResponseMessage;

/**
 * Response message model factory
 */
public interface ResponseMessageFactory {
    /**
     * Instantiate a ResponseMessage with the provided message
     *
     * @param message The message
     * @return A ResponseMessage
     */
    ResponseMessage makeResponseMessage(String message);
}
