package daybyquest.support.fixture;


import static daybyquest.post.domain.PostState.SUCCESS;

import daybyquest.image.domain.Image;
import daybyquest.post.domain.Post;
import daybyquest.post.dto.response.PostResponse;
import daybyquest.post.dto.response.PostWithQuestResponse;
import daybyquest.post.query.PostData;
import daybyquest.quest.domain.Quest;
import daybyquest.user.domain.User;
import daybyquest.user.query.Profile;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

public enum PostFixtures {

    POST_1("게시물 내용 1", List.of("이미지주소1")),
    POST_2("게시물 내용 2", List.of("이미지주소2")),
    POST_3("게시물 내용 3", List.of("이미지주소3")),
    POST_4("게시물 내용 4", List.of("이미지주소4")),
    POST_WITH_3_IMAGES("게시물 내용", List.of("이미지주소1", "이미지주소2", "이미지주소3"));

    public final String content;

    public final List<String> imageIdentifiers;

    PostFixtures(final String content, final List<String> imageIdentifiers) {
        this.content = content;
        this.imageIdentifiers = imageIdentifiers;
    }

    public Post 생성(final Long id, final Long userId, final Long questId) {
        final Post post = new Post(userId, questId, content, 사진_목록());
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

    public Post 링크_성공_상태로_생성(final User user, final Quest quest) {
        final Post post = 생성(null, user.getId(), quest.getId());
        ReflectionTestUtils.setField(post, "state", SUCCESS);
        return post;
    }

    public Post 생성(final User user) {
        return 생성(null, user.getId(), null);
    }

    public List<Image> 사진_목록() {
        return imageIdentifiers.stream().map(Image::new).toList();
    }

    public PostResponse 응답(final Long id, final Profile profile) {
        final PostData postData = new PostData(profile.getId(), id, content, LocalDateTime.MIN, false,
                null, null, SUCCESS);
        postData.setImages(사진_목록());
        return PostResponse.of(postData, profile);
    }

    public PostWithQuestResponse 퀘스트와_함께_응답(final Long id, final Profile profile) {
        final PostData postData = new PostData(profile.getId(), id, content, LocalDateTime.MIN, false,
                null, null, SUCCESS);
        postData.setImages(사진_목록());
        return PostWithQuestResponse.of(postData, profile);
    }
}
