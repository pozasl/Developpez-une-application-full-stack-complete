package com.openclassrooms.mdd.common.exception;

import com.openclassrooms.mdd.api.model.ResponseMessage;
import com.openclassrooms.mdd.common.model.ResponseMessageFactory;
import com.openclassrooms.mdd.common.model.ResponseMessageFactoryImpl;

import java.nio.file.AccessDeniedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;

@RestControllerAdvice
public class ApiErrorHandler {

   private static final Logger LOGGER = LoggerFactory.getLogger(ApiErrorHandler.class);
   private final ResponseMessageFactory responseMessageFactory;

   public ApiErrorHandler() {
      this.responseMessageFactory = ResponseMessageFactoryImpl.create();
   }

   // 500
   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public ResponseMessage handleException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   // 400
   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResponseMessage handleMethodArgumentNotValidException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   // 401
   @ExceptionHandler(AccessDeniedException.class)
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   public ResponseMessage handleAccessDeniedException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   // 401
   @ExceptionHandler(BadCredentialsException.class)
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   public ResponseMessage handleBadCredentialsException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   // 404 Dynamic resource not found
   @ExceptionHandler(ResourceNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseMessage handleResourceNotFoundException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   // 404 Static resource not found
   @ExceptionHandler(NoResourceFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseMessage handleNoResourceFoundException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   // 409
   @ExceptionHandler(UserAlreadyExistsException.class)
   @ResponseStatus(HttpStatus.CONFLICT)
   public ResponseMessage handleUserAlreadyExistsException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   private ResponseMessage createResponseMessage(String message) {
      return this.responseMessageFactory.makeResponseMessage(message);
   }

}
