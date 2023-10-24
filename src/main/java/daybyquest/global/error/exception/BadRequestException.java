package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;
import java.util.List;

public class BadRequestException extends CustomException {

    public BadRequestException() {
        super(ExceptionCode.INVALID_REQUEST);
    }

    public BadRequestException(ExceptionCode exceptionCode, List<String> fields) {
        super(exceptionCode, fields);
    }

    public BadRequestException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
