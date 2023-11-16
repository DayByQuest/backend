package daybyquest.post.application;

import daybyquest.group.domain.GroupUsers;
import daybyquest.image.domain.Image;
import daybyquest.post.domain.Post;
import daybyquest.post.domain.Posts;
import daybyquest.post.dto.response.NeedCheckPostsResponse;
import daybyquest.post.dto.response.SimplePostResponse;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetNeedCheckPostsService {

    private final Quests quests;

    private final GroupUsers groupUsers;

    private final Posts posts;

    public GetNeedCheckPostsService(final Quests quests, final GroupUsers groupUsers, final Posts posts) {
        this.quests = quests;
        this.groupUsers = groupUsers;
        this.posts = posts;
    }

    @Transactional(readOnly = true)
    public NeedCheckPostsResponse invoke(final Long loginId, final Long questId) {
        final Quest quest = quests.getById(questId);
        groupUsers.validateGroupManager(loginId, quest.getGroupId());
        final List<Post> posts = this.posts.findTop10NeedCheckPostByQuestId(questId);
        final List<SimplePostResponse> responses = posts.stream().map(SimplePostResponse::of).toList();
        return new NeedCheckPostsResponse(
                quest.getImages().stream().map(Image::getIdentifier).toList(), responses);
    }
}
