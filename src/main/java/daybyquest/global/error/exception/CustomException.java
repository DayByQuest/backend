package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    @Getter
    private final List<String> fields;

    public CustomException(ExceptionCode exceptionCode, List<String> fields) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
        this.fields = fields;
    }

    public CustomException(ExceptionCode exceptionCode) {
        this(exceptionCode, Collections.emptyList());
    }

    public String getCode() {
        return this.exceptionCode.getCode();
    }

    public HttpStatus getHttpStatus() {
        return this.exceptionCode.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return this.exceptionCode.getMessage();
    }
}
