package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class NotExistBadgeException extends CustomException {

    public NotExistBadgeException() {
        super(ExceptionCode.NOT_EXIST_BADGE);
    }
}
