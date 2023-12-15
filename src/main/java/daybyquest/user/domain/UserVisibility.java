package daybyquest.user.domain;

import static daybyquest.global.error.ExceptionCode.INVALID_VISIBILITY;

import daybyquest.global.error.exception.InvalidDomainException;

public enum UserVisibility {

    PUBLIC,
    PRIVATE;

    public static UserVisibility fromString(final String name) {
        try {
            return valueOf(name);
        } catch (final IllegalArgumentException e) {
            throw new InvalidDomainException(INVALID_VISIBILITY);
        }
    }
}
