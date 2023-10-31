package daybyquest.global.error.exception;

import static daybyquest.global.error.ExceptionCode.BAD_AUTHORIZATION;

public class BadAuthorizationException extends CustomException {

    public BadAuthorizationException() {
        super(BAD_AUTHORIZATION);
    }
}
