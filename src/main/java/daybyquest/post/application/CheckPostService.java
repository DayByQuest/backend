package daybyquest.post.application;

import daybyquest.group.domain.GroupUsers;
import daybyquest.post.domain.Post;
import daybyquest.post.domain.Posts;
import daybyquest.post.domain.SuccessfullyPostLinkedEvent;
import daybyquest.post.dto.request.CheckPostRequest;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckPostService {

    private final Quests quests;

    private final GroupUsers groupUsers;

    private final Posts posts;

    private final ApplicationEventPublisher publisher;

    public CheckPostService(final Quests quests, final GroupUsers groupUsers, final Posts posts,
            final ApplicationEventPublisher publisher) {
        this.quests = quests;
        this.groupUsers = groupUsers;
        this.posts = posts;
        this.publisher = publisher;
    }

    @Transactional
    public void invoke(final Long loginId, final Long postId, final CheckPostRequest request) {
        final Post post = posts.getById(postId);
        final Long questId = post.getQuestId();
        final Quest quest = quests.getById(questId);
        groupUsers.validateGroupManager(loginId, quest.getGroupId());
        final boolean approval = request.isApproval();
        if (approval) {
            post.success();
            publisher.publishEvent(new SuccessfullyPostLinkedEvent(post.getUserId(), post.getQuestId()));
            return;
        }
        post.fail();
    }
}
