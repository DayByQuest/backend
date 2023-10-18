package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class InvalidDomainException extends CustomException {

    public InvalidDomainException() {
        super(ExceptionCode.INVALID_REQUEST);
    }

    public InvalidDomainException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
