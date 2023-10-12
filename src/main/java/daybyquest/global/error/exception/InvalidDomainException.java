package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class InvalidDomainException extends CustomException{

    public InvalidDomainException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
