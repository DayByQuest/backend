package daybyquest.comment.domain;

import daybyquest.post.domain.Posts;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class Comments {

    private final CommentRepository commentRepository;

    private final Users users;

    private final Posts posts;

    Comments(final CommentRepository commentRepository, final Users users, final Posts posts) {
        this.commentRepository = commentRepository;
        this.users = users;
        this.posts = posts;
    }

    public void save(final Comment comment) {
        users.validateExistentById(comment.getUserId());
        posts.validateExistentById(comment.getPostId());
        commentRepository.save(comment);
    }
}
