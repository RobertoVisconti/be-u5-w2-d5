package robertovisconti.be_u5_w2_d5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import robertovisconti.be_u5_w2_d5.DTO.ErrorsDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorsHandler {


    // LISTA GESTIONE ERRORI
    @ExceptionHandler(ValidationExceptions.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(ValidationExceptions ex) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("errors", ex.getListaErrori());
        payload.put("timestamp", LocalDateTime.now());
        payload.put("status", HttpStatus.BAD_REQUEST.value());
        ex.printStackTrace();
        return payload;
    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(BadRequestException ex) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", ex.getMessage());
        payload.put("timestamp", LocalDateTime.now());
        payload.put("status", HttpStatus.BAD_REQUEST.value());
        ex.printStackTrace();
        return payload;
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsDTO handleUnauthorized(UnauthorizedException ex) {
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(NotFoundException ex) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", ex.getMessage());
        payload.put("timestamp", LocalDateTime.now());
        payload.put("status", HttpStatus.NOT_FOUND.value());
        ex.printStackTrace();
        return payload;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGenericError(Exception ex) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", "Errore interno del server: " + ex.getMessage());
        payload.put("timestamp", LocalDateTime.now());
        payload.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        ex.printStackTrace();
        return payload;
    }

}
