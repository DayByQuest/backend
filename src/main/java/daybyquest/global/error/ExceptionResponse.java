package daybyquest.global.error;

import daybyquest.global.error.exception.CustomException;
import java.util.Collections;
import java.util.List;

public record ExceptionResponse(String code, String message, List<String> fields) {

    public static ExceptionResponse of(CustomException e) {
        return new ExceptionResponse(e.getCode(), e.getMessage(), e.getFields());
    }

    public static ExceptionResponse of(ExceptionCode errorCode, String field) {
        return new ExceptionResponse(errorCode.getCode(), errorCode.getMessage(), List.of(field));
    }

    public static ExceptionResponse of(ExceptionCode errorCode, List<String> fields) {
        return new ExceptionResponse(errorCode.getCode(), errorCode.getMessage(), fields);
    }

    public static ExceptionResponse of(ExceptionCode errorCode) {
        return new ExceptionResponse(errorCode.getCode(), errorCode.getMessage(), Collections.emptyList());
    }
}
