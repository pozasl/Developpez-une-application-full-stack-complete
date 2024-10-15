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

/**
 * API error handling
 */
@RestControllerAdvice
public class ApiErrorHandler {

   private static final Logger LOGGER = LoggerFactory.getLogger(ApiErrorHandler.class);
   private final ResponseMessageFactory responseMessageFactory;

   public ApiErrorHandler() {
      this.responseMessageFactory = ResponseMessageFactoryImpl.create();
   }

   /**
    * 500 - Handle most Exception
    * @param ex the exception
    * @return Reponse message with error message
    */
   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public ResponseMessage handleException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   /**
    * 400 - BAD REQUEST
    * @param ex the exception
    * @return Reponse message with error message
    */
   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResponseMessage handleMethodArgumentNotValidException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   /**
    * 401 - ACCESS DENIED
    * @param ex the exception
    * @return Reponse message with error message
    */
   @ExceptionHandler(AccessDeniedException.class)
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   public ResponseMessage handleAccessDeniedException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   /**
    * 401 - BAD CREDENTIAL
    * @param ex the exception
    * @return Reponse message with error message
    */
   @ExceptionHandler(BadCredentialsException.class)
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   public ResponseMessage handleBadCredentialsException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   /**
    * 404 Dynamic resource NOT FOUND
    * @param ex the exception
    * @return Reponse message with error message
    */
   @ExceptionHandler(ResourceNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseMessage handleResourceNotFoundException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   /**
    * 404 Static resource NOT FOUND
    * @param ex the exception
    * @return Reponse message with error message
    */
   @ExceptionHandler(NoResourceFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseMessage handleNoResourceFoundException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   /**
    * 404 CONFLICT
    * @param ex the exception
    * @return Reponse message with error message
    */
   @ExceptionHandler(UserAlreadyExistsException.class)
   @ResponseStatus(HttpStatus.CONFLICT)
   public ResponseMessage handleUserAlreadyExistsException(final Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return this.createResponseMessage(ex.getMessage());
   }

   /**
    * Create a Response message
    * @param message The message
    * @return The response message
    */
   private ResponseMessage createResponseMessage(String message) {
      return this.responseMessageFactory.makeResponseMessage(message);
   }

}
