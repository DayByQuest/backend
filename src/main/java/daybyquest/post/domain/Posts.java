package daybyquest.post.domain;

import static daybyquest.post.domain.PostState.NEED_CHECK;

import daybyquest.global.error.exception.NotExistPostException;
import daybyquest.participant.domain.Participants;
import daybyquest.user.domain.Users;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Posts {

    private final PostRepository postRepository;

    private final Users users;

    private final Participants participants;

    Posts(final PostRepository postRepository, final Users users, final Participants participants) {
        this.postRepository = postRepository;
        this.users = users;
        this.participants = participants;
    }

    public Post save(final Post post) {
        users.validateExistentById(post.getUserId());
        if (post.isQuestLinked()) {
            participants.validateExistent(post.getUserId(), post.getQuestId());
        }
        return postRepository.save(post);
    }

    public Post getById(final Long id) {
        return postRepository.findById(id).orElseThrow(NotExistPostException::new);
    }

    public void validateExistentById(final Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new NotExistPostException();
        }
    }

    public List<Post> findTop10NeedCheckPostByQuestId(final Long questId) {
        return postRepository.findTop10ByQuestIdAndState(questId, NEED_CHECK);
    }
}
