package daybyquest.user.domain;

public enum UserState {
    USER,
    MODERATOR,
    ADMIN,
    DELETED;

    public boolean canUpdate() {
        return this == USER || this == MODERATOR;
    }
}
