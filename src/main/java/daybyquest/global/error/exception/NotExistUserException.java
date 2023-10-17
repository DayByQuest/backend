package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class NotExistUserException extends CustomException {

    public NotExistUserException() {
        super(ExceptionCode.NOT_EXIST_USER);
    }
}
