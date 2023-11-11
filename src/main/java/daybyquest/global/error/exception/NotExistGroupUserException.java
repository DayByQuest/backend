package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class NotExistGroupUserException extends CustomException {

    public NotExistGroupUserException() {
        super(ExceptionCode.NOT_EXIST_GROUP_USER);
    }
}
