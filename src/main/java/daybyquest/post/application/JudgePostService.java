package daybyquest.post.application;

import daybyquest.post.domain.Judgement;
import daybyquest.post.domain.Post;
import daybyquest.post.domain.Posts;
import daybyquest.post.domain.SuccessfullyPostLinkedEvent;
import daybyquest.post.dto.request.JudgePostRequest;
import daybyquest.quest.domain.Quests;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JudgePostService {

    private final Posts posts;

    private final Quests quests;

    private final ApplicationEventPublisher publisher;

    public JudgePostService(final Posts posts, final Quests quests,
            final ApplicationEventPublisher publisher) {
        this.posts = posts;
        this.quests = quests;
        this.publisher = publisher;
    }

    @Transactional
    public void invoke(final Long postId, final JudgePostRequest request) {
        final Post post = posts.getById(postId);
        final Judgement judgement = Judgement.valueOf(request.getJudgement());
        if (judgement == Judgement.SUCCESS) {
            post.success();
            publisher.publishEvent(new SuccessfullyPostLinkedEvent(post.getUserId(), post.getQuestId()));
            return;
        }
        
        if (quests.getById(post.getQuestId()).isGroupQuest()) {
            post.needCheck();
            return;
        }
        post.fail();
    }
}
