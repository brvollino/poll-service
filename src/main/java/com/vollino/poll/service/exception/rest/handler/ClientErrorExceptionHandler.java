package com.vollino.poll.service.exception.rest.handler;

import com.vollino.poll.service.exception.business.ClientErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Bruno Vollino
 */
@ControllerAdvice
public class ClientErrorExceptionHandler {

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ErrorResponseBody> handleException(ClientErrorException ex) {
        ErrorResponseBody body = new ErrorResponseBody().withError(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(body);
    }
}
