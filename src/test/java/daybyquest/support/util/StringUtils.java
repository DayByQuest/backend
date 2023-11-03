package daybyquest.support.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class StringUtils {

    public static String 문자열을_만든다(final int length) {
        return Stream.generate(() -> "x").limit(length).collect(Collectors.joining());
    }

    private StringUtils() {
    }
}
