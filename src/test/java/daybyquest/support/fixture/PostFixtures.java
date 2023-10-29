package daybyquest.support.fixture;


import daybyquest.image.vo.Image;
import daybyquest.post.domain.Post;
import daybyquest.quest.domain.Quest;
import daybyquest.user.domain.User;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

public enum PostFixtures {

    POST_1("게시물 내용 1", List.of("이미지주소1")),
    POST_2("게시물 내용 2", List.of("이미지주소2")),
    POST_3("게시물 내용 3", List.of("이미지주소3")),
    POST_WITH_3_IMAGES("게시물 내용", List.of("이미지주소1", "이미지주소2", "이미지주소3"));

    public final String content;

    public final List<String> imageIdentifiers;

    PostFixtures(final String content, final List<String> imageIdentifiers) {
        this.content = content;
        this.imageIdentifiers = imageIdentifiers;
    }

    public Post 생성(final Long id, final Long userId, final Long questId) {
        final Post post = new Post(userId, questId, content, getImages());
        ReflectionTestUtils.setField(post, "id", id);
        return post;
    }

    public Post 생성(final Long userId, final Long questId) {
        return 생성(null, userId, questId);
    }

    public Post 생성(final Long userId) {
        return 생성(null, userId, null);
    }

    public Post 생성(final User user, final Quest quest) {
        return 생성(null, user.getId(), quest.getId());
    }

    public Post 생성(final User user) {
        return 생성(null, user.getId(), null);
    }

    private List<Image> getImages() {
        return imageIdentifiers.stream().map(Image::new).toList();
    }
}
