package daybyquest.global.error.exception;

import daybyquest.global.error.ExceptionCode;

public class NotExistQuestException extends CustomException {

    public NotExistQuestException() {
        super(ExceptionCode.NOT_EXIST_QUEST);
    }
}
