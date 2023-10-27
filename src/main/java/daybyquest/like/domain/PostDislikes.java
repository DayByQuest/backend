package daybyquest.like.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_DISLIKED_POST;
import static daybyquest.global.error.ExceptionCode.NOT_EXIST_DISLIKE;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.post.domain.Posts;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class PostDislikes {

    private final PostDislikeRepository postDislikeRepository;

    private final Users users;

    private final Posts posts;

    PostDislikes(final PostDislikeRepository postDislikeRepository, final Users users, final Posts posts) {
        this.postDislikeRepository = postDislikeRepository;
        this.users = users;
        this.posts = posts;
    }

    public void save(final PostDislike postDislike) {
        users.validateExistentById(postDislike.getUserId());
        posts.validateExistentById(postDislike.getPostId());
        validateNotExistent(postDislike.getUserId(), postDislike.getPostId());
        postDislikeRepository.save(postDislike);
    }

    public void deleteByUserIdAndPostId(final Long userId, final Long postId) {
        validateExistent(userId, postId);
        postDislikeRepository.deleteByUserIdAndPostId(userId, postId);
    }

    private void validateNotExistent(final Long userId, final Long postId) {
        if (postDislikeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new InvalidDomainException(ALREADY_DISLIKED_POST);
        }
    }

    private void validateExistent(final Long userId, final Long postId) {
        if (!postDislikeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new InvalidDomainException(NOT_EXIST_DISLIKE);
        }
    }
}
