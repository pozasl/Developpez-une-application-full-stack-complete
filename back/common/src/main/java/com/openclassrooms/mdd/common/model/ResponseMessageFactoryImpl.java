package com.openclassrooms.mdd.common.model;

import com.openclassrooms.mdd.api.model.ResponseMessage;

/**
 * Response message model factory implementation
 */
public class ResponseMessageFactoryImpl implements ResponseMessageFactory {

    ResponseMessageFactoryImpl() {}

    public static ResponseMessageFactory create() {
        return new ResponseMessageFactoryImpl();
      }

    @Override
    public ResponseMessage makeResponseMessage(String message) {
        return new ResponseMessage().message(message);
    }
    
}
