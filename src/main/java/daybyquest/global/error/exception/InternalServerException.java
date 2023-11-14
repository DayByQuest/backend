package daybyquest.global.error.exception;

import static daybyquest.global.error.ExceptionCode.INTERNAL_SERVER;

public class InternalServerException extends CustomException {

    public InternalServerException() {
        super(INTERNAL_SERVER);
    }
}
