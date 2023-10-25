package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class NotExistPostException extends CustomException {

    public NotExistPostException() {
        super(ExceptionCode.NOT_EXIST_POST);
    }
}
