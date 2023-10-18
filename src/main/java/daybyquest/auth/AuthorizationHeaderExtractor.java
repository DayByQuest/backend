package daybyquest.auth;

public class AuthorizationHeaderExtractor {

    private final static String AUTHORIZATION_HEADER_PREFIX = "UserId ";

    public static String extract(String header) {
        if (header == null || !header.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            return null;
        }
        return header.substring(AUTHORIZATION_HEADER_PREFIX.length());
    }
}
