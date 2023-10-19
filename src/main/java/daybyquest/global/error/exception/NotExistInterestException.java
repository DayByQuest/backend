package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class NotExistInterestException extends CustomException {

    public NotExistInterestException() {
        super(ExceptionCode.NOT_EXIST_INTEREST);
    }
}
