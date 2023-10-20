package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class InvalidFileException extends CustomException {

    public InvalidFileException() {
        super(ExceptionCode.INVALID_FILE);
    }
}
