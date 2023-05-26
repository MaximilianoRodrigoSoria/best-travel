package ar.com.laboratory.besttravel.api.error_handler;

import ar.com.laboratory.besttravel.api.models.responses.BaseErrorResponse;
import ar.com.laboratory.besttravel.api.models.responses.ErrorResponse;
import ar.com.laboratory.besttravel.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse idNotFound(IdNotFoundException exception){
        return  ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .build();
    }
}
