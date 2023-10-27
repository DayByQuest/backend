package daybyquest.post.domain;

import daybyquest.global.error.exception.NotExistPostException;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class Posts {

    private final PostRepository postRepository;

    private final Users users;

    Posts(final PostRepository postRepository, final Users users) {
        this.postRepository = postRepository;
        this.users = users;
    }

    public Long save(final Post post) {
        users.validateExistentById(post.getUserId());
        return postRepository.save(post).getId();
    }

    public Post getById(final Long id) {
        return postRepository.findById(id).orElseThrow(NotExistPostException::new);
    }

    public void validateExistentById(final Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new NotExistPostException();
        }
    }
}
