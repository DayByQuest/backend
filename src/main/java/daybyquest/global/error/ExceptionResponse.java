package daybyquest.global.error;

import daybyquest.global.error.exception.CustomException;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class ExceptionResponse {

    private final String code;

    private final String message;

    private final List<String> fields;

    public ExceptionResponse(String code, String message, List<String> fields) {
        this.code = code;
        this.message = message;
        this.fields = fields;
    }

    public static ExceptionResponse of(CustomException e) {
        return new ExceptionResponse(e.getCode(), e.getMessage(), e.getFields());
    }

    public static ExceptionResponse of(ExceptionCode errorCode, List<String> fields) {
        return new ExceptionResponse(errorCode.getCode(), errorCode.getMessage(), fields);
    }

    public static ExceptionResponse of(ExceptionCode errorCode) {
        return new ExceptionResponse(errorCode.getCode(), errorCode.getMessage(), Collections.emptyList());
    }
}
