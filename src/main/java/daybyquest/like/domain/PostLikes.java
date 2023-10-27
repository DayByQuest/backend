package daybyquest.like.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_LIKED_POST;
import static daybyquest.global.error.ExceptionCode.NOT_EXIST_LIKE;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.post.domain.Posts;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class PostLikes {

    private final PostLikeRepository postLikeRepository;

    private final Users users;

    private final Posts posts;

    PostLikes(final PostLikeRepository postLikeRepository, final Users users, final Posts posts) {
        this.postLikeRepository = postLikeRepository;
        this.users = users;
        this.posts = posts;
    }

    public void save(final PostLike postLike) {
        users.validateExistentById(postLike.getUserId());
        posts.validateExistentById(postLike.getPostId());
        validateNotExistent(postLike.getUserId(), postLike.getPostId());
        postLikeRepository.save(postLike);
    }

    public void deleteByUserIdAndPostId(final Long userId, final Long postId) {
        validateExistent(userId, postId);
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }

    private void validateNotExistent(final Long userId, final Long postId) {
        if (postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new InvalidDomainException(ALREADY_LIKED_POST);
        }
    }

    private void validateExistent(final Long userId, final Long postId) {
        if (!postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new InvalidDomainException(NOT_EXIST_LIKE);
        }
    }
}
