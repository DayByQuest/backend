package daybyquest.global.error;

import static daybyquest.global.error.ExceptionCode.INTERNAL_SERVER;
import static daybyquest.global.error.ExceptionCode.INVALID_INPUT;
import static daybyquest.global.error.ExceptionCode.INVALID_REQUEST;

import daybyquest.global.error.exception.CustomException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.MissingRequestValueException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        MissingServletRequestPartException.class,
        MissingRequestValueException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class,
        HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<ExceptionResponse> handleHttpRequestException(Exception e) {
        log.warn(e.getMessage(), e);
        final ExceptionResponse response = ExceptionResponse.of(INVALID_REQUEST);
        return new ResponseEntity<>(response, INVALID_REQUEST.getHttpStatus());
    }

    @ExceptionHandler
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        log.debug(e.getMessage(), e);
        final ExceptionResponse response = ExceptionResponse.of(INVALID_INPUT,
            convertBindingResult(e.getBindingResult())
        );
        return new ResponseEntity<>(response, INVALID_INPUT.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        log.debug(e.getMessage(), e);
        final ExceptionResponse response = ExceptionResponse.of(e);
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        final ExceptionResponse response = ExceptionResponse.of(INTERNAL_SERVER);
        return new ResponseEntity<>(response, INTERNAL_SERVER.getHttpStatus());
    }

    private List<String> convertBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
            .map(FieldError::getField)
            .toList();
    }
}