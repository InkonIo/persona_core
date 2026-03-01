package kz.persona.core.exception;

import kz.persona.core.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(value = PersonaCoreException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public MessageDto handleException(PersonaCoreException shopBotException) {
        return new MessageDto(shopBotException.getMessage());
    }

}
