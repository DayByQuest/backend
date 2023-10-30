package daybyquest.support.test;

import daybyquest.comment.domain.Comments;
import daybyquest.global.config.JpaConfig;
import daybyquest.post.domain.Posts;
import daybyquest.user.domain.Users;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({JpaConfig.class, Users.class, Posts.class, Comments.class})
public abstract class QuerydslTest {

}
