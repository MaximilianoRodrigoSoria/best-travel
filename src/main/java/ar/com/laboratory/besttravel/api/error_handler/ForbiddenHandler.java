package ar.com.laboratory.besttravel.api.error_handler;

import ar.com.laboratory.besttravel.api.models.responses.BaseErrorResponse;
import ar.com.laboratory.besttravel.api.models.responses.ErrorResponse;
import ar.com.laboratory.besttravel.util.exceptions.ForbiddenCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenHandler {

    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse customerBlocked(ForbiddenCustomerException exception){
        return  ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN.name())
                .build();
    }
}
