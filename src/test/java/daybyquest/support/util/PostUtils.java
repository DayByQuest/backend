package daybyquest.support.util;

import daybyquest.post.domain.Post;
import java.time.LocalDateTime;
import org.springframework.test.util.ReflectionTestUtils;

public class PostUtils {

    public static void n일_전에_업로드(final Post post, final long day) {
        final LocalDateTime dayBefore = LocalDateTime.now().minusDays(day);
        ReflectionTestUtils.setField(post, "uploadedAt", dayBefore);
    }
}
