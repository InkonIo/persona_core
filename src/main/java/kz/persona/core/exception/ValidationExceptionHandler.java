package kz.persona.core.exception;

import kz.persona.core.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    private static final String EMPTY_REQUEST_BODY = "Тело запроса пустое";

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public MessageDto handleFieldViolation(MethodArgumentNotValidException ex) {
        String clientMessage = Optional.of(ex.getBindingResult())
                .map(Errors::getFieldError)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Ошибка валидации");
        return new MessageDto(clientMessage);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public MessageDto handleInvalidJsonBody(HttpMessageNotReadableException ex) {
        log.error("Invalid json body: {}", ex.getMessage(), ex);
        return new MessageDto(EMPTY_REQUEST_BODY);
    }

}
