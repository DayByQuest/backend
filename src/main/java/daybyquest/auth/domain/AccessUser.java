package daybyquest.auth.domain;

import lombok.Getter;

public class AccessUser {

    @Getter
    private final Long id;

    private final Authority authority;

    private AccessUser(final Long id, final Authority authority) {
        this.id = id;
        this.authority = authority;
    }

    public static AccessUser ofGuest() {
        return new AccessUser(-1L, Authority.GUEST);
    }

    public static AccessUser ofUser(final Long id) {
        return new AccessUser(id, Authority.USER);
    }

    public static AccessUser ofAdmin(final Long id) {
        return new AccessUser(id, Authority.ADMIN);
    }

    public boolean isUser() {
        return authority == Authority.USER;
    }

    public boolean isAdmin() {
        return authority == Authority.ADMIN;
    }

    public boolean isGuest() {
        return authority == Authority.GUEST;
    }
}
