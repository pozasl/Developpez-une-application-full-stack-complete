package com.openclassrooms.mdd.common.model;

import com.openclassrooms.mdd.api.model.ResponseMessage;

public interface ResponseMessageFactory {
    ResponseMessage makeResponseMessage(String message);
}
