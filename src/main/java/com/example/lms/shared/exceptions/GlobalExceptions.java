package com.example.lms.shared.exceptions;

import com.example.lms.shared.models.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptions {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodRequestException(HttpRequestMethodNotSupportedException ex) {
        List<String> error = List.of(ex.getMessage());
        return ResponseEntity.status(400).body(
                ErrorResponse.builder()
                        .message(error.get(0))
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadException(
            IllegalArgumentException ex) {
        List<String> error = List.of(ex.getMessage());
        return ResponseEntity.status(400).body(ErrorResponse.builder()
                .errors(error)
                .message(error.get(0))
                .build());


    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex) {

        return ResponseEntity.status(400).body(
                ErrorResponse.builder()
                        .errors(Collections.EMPTY_LIST)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
            BadCredentialsException ex) {

        return ResponseEntity.status(401).body(
                ErrorResponse.builder()
                        .errors(Collections.EMPTY_LIST)
                        .message("email or password not correct")
                        .build()
        );
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();


        for (ObjectError er : ex.getAllErrors()) {
            var field = ((FieldError) er).getField();
            String err = er.getDefaultMessage();
            errors.put(field, err);

        }
        return ResponseEntity.status(422).body(
                ErrorResponse.builder()
                        .errors(errors)
                        .message("validation error")
                        .build()
        );
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(ConstraintViolationException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(
                violation -> {
                    String fieldName = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
                    String errorMessage = violation.getMessage();
                    errors.put(fieldName, errorMessage);
                });
        return ResponseEntity.status(422).body(
                ErrorResponse.builder()
                        .errors(errors)
                        .message("validation error")
                        .build()
        );

    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnAuthorizeException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizeException(
            UnAuthorizeException ex) {

        return ResponseEntity.status(403).body(
                ErrorResponse.builder()
                        .errors(Collections.EMPTY_LIST)
                        .message(ex.getMessage())
                        .build()
        );
    }
}