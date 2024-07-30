package com.lastfarewells.backend.exception;

import com.lastfarewells.backend.dto.ErrorResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ExceptionHandler({UserException.class})
    public ResponseEntity<ErrorResponse> handleCoreException(
        Exception e
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
            new ErrorResponse(
                status,
                e.getMessage()
            ),
            status
        );
    }

    @ExceptionHandler({IAMException.class, UserAuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleUserAuthenticationException(
        Exception e
    ) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(
            new ErrorResponse(
                status,
                e.getMessage()
            ),
            status
        );
    }

}
