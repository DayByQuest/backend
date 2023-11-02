package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class NotExistGroupException extends CustomException {

    public NotExistGroupException() {
        super(ExceptionCode.NOT_EXIST_GROUP);
    }
}
