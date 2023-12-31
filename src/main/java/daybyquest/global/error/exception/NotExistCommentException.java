package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class NotExistCommentException extends CustomException {

    public NotExistCommentException() {
        super(ExceptionCode.NOT_EXIST_COMMENT);
    }
}
