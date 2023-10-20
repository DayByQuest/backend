package daybyquest.global.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    INTERNAL_SERVER("GLO-00", INTERNAL_SERVER_ERROR, "내부 서버 에러입니다"),

    INVALID_REQUEST("GLO-01", BAD_REQUEST, "올바르지 않은 요청입니다"),

    INVALID_INPUT("GLO-02", BAD_REQUEST, "올바르지 않은 입력입니다"),

    BAD_AUTHORIZATION("GLO-03", FORBIDDEN, "권한이 없습니다"),

    INVALID_FILE("GLO-04", FORBIDDEN, "파일 오류 입니다"),

    // User
    NOT_EXIST_USER("USR-00", BAD_REQUEST, "사용자가 존재하지 않습니다"),
    DUPLICATED_USERNAME("USR-01", BAD_REQUEST, "이미 존재하는 사용자 이름 입니다"),
    DUPLICATED_EMAIL("USR-02", BAD_REQUEST, "이미 존재하는 이메일 입니다"),

    // Post
    NOT_EXIST_POST("POT-00", BAD_REQUEST, "게시물이 존재하지 않습니다"),
    ALREADY_LIKED_POST("POT-01", BAD_REQUEST, "이미 좋아요한 게시물입니다"),
    ALREADY_DISLIKED_POST("POT-02", BAD_REQUEST, "이미 관심없음한 게시물입니다"),
    NOT_EXIST_LIKE("POT-03", BAD_REQUEST, "좋아요하지 않은 게시물은 취소할 수 없습니다"),
    NOT_EXIST_DISLIKE("POT-04", BAD_REQUEST, "관심없음하지 않은 게시물은 취소할 수 없습니다"),

    // Comment
    NOT_EXIST_COMMENT("COM-00", BAD_REQUEST, "존재하지 않는 댓글입니다"),

    // Quest
    NOT_EXIST_QUEST("QUE-00", BAD_REQUEST, "존재하지 않는 퀘스트입니다"),
    NOT_FINISHABLE_QUEST("QUE-01", BAD_REQUEST, "완료할 수 없는 퀘스트입니다"),
    ALREADY_REWARDED_QUEST("QUE-02", BAD_REQUEST, "이미 보상을 받은 퀘스트입니다"),
    ALREADY_ACCEPTED_QUEST("QUE-03", BAD_REQUEST, "이미 수행중인 퀘스트 입니다"),
    NOT_CONTINUABLE_QUEST("QUE-04", BAD_REQUEST, "재개할 수 없는 퀘스트 입니다"),
    EXCEED_MAX_QUEST("QUE-05", BAD_REQUEST, "최대 수행 가능 퀘스트를 초과하였습니다"),
    NOT_ACCEPTED_QUEST("QUE-06", BAD_REQUEST, "수행 중이지 않은 퀘스트입니다"),
    NO_RECOMMENDED_QUEST("QUE-07", BAD_REQUEST, "추천 할 퀘스트가 없습니다"),

    // Group
    NOT_EXIST_GROUP("GRP-00", BAD_REQUEST, "존재하지 않는 그룹입니다"),
    DUPLICATED_GROUP_NAME("GRP-01", BAD_REQUEST, "이미 존재하는 그룹 이름 입니다"),
    EXCEED_MAX_GROUP("GRP-02", BAD_REQUEST, "최대 그룹 가입 횟수를 초과하였습니다"),
    NOT_GROUP_QUEST("GRP-03", BAD_REQUEST, "그룹 퀘스트와 관련없는 게시물입니다"),
    ALREADY_MEMBER("GRP-04", BAD_REQUEST, "이미 가입한 그룹입니다"),
    NOT_MEMBER("GRP-05", BAD_REQUEST, "가입하지 않은 그룹은 탈퇴할 수 없습니다"),
    NO_RECOMMENDED_GROUP("GRP-06", BAD_REQUEST, "추천 할 그룹이 없습니다"),

    // Badge
    NOT_EXIST_BADGE("BDE-00", BAD_REQUEST, "존재하지 않는 뱃지입니다"),
    NOT_OWNING_BADGE("BDE-01", BAD_REQUEST, "보유하지 않은 뱃지는 프로필에 배치할 수 없습니다"),
    EXCEED_BADGE("BDE-02", BAD_REQUEST, "뱃지는 최대 15개만 지정가능합니다"),

    // Interest
    NOT_EXIST_INTEREST("INT-00", BAD_REQUEST, "존재하지 않는 관심사 입니다"),

    // Relationship
    NOT_FOLLOWING_USER("REL-00", BAD_REQUEST, "팔로우하지 않은 사용자는 취소할 수 없습니다"),
    NOT_BLOCKING_USER("REL-01", BAD_REQUEST, "차단하지 않은 사용자는 취소할 수 없습니다"),
    ALREADY_FOLLOWING_USER("REL-02", BAD_REQUEST, "이미 팔로우한 사용자입니다"),
    ALREADY_BLOCKING_USER("REL-03", BAD_REQUEST, "이미 차단한 사용자입니다"),

    ;


    private final String code;

    private final HttpStatus httpStatus;

    private final String message;

    ExceptionCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
