package daybyquest.post.domain;

import daybyquest.global.error.exception.NotExistPostException;
import org.springframework.stereotype.Component;

@Component
public class Posts {

    private final PostRepository postRepository;

    public Posts(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long save(final Post post) {
        return postRepository.save(post).getId();
    }

    public Post getById(final Long id) {
        return postRepository.findById(id).orElseThrow(NotExistPostException::new);
    }
}
