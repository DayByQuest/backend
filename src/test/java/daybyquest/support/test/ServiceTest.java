package daybyquest.support.test;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static daybyquest.support.fixture.UserFixtures.DARTH;
import static daybyquest.support.fixture.UserFixtures.DAVID;

import daybyquest.badge.domain.Badges;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.group.domain.GroupUsers;
import daybyquest.group.domain.Groups;
import daybyquest.interest.domain.Interests;
import daybyquest.participant.domain.Participants;
import daybyquest.post.application.PostClient;
import daybyquest.post.domain.Posts;
import daybyquest.quest.application.QuestClient;
import daybyquest.quest.domain.Quests;
import daybyquest.relation.domain.Follows;
import daybyquest.support.config.StubInfraConfig;
import daybyquest.support.util.DatabaseCleaner;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest(classes = {StubInfraConfig.class})
public class ServiceTest {

    @Autowired
    private DatabaseCleaner cleaner;

    @Autowired
    protected Users users;

    @Autowired
    protected Posts posts;

    @Autowired
    protected Groups groups;

    @Autowired
    protected GroupUsers groupUsers;

    @Autowired
    protected Quests quests;

    @Autowired
    protected Badges badges;

    @Autowired
    protected Participants participants;

    @Autowired
    protected Interests interests;

    @Autowired
    protected Follows follows;

    @MockBean
    protected DateTimeProvider dataTimeProvider;

    @SpyBean
    protected PostClient postClient;

    @SpyBean
    protected QuestClient questClient;

    @SpyBean
    private AuditingHandler handler;

    @BeforeEach
    void setUp() {
        handler.setDateTimeProvider(dataTimeProvider);
    }

    @AfterEach
    void cleanDatabase() {
        cleaner.clean();
    }

    protected Long 중재자_권한으로_ALICE를_저장한다() {
        final User user = ALICE.생성();
        user.promote();
        return users.save(user).getId();
    }

    protected Long 중재자_권한으로_BOB을_저장한다() {
        final User user = BOB.생성();
        user.promote();
        return users.save(user).getId();
    }

    protected Long ALICE를_저장한다() {
        return users.save(ALICE.생성()).getId();
    }

    protected Long BOB을_저장한다() {
        return users.save(BOB.생성()).getId();
    }

    protected Long CHARLIE를_저장한다() {
        return users.save(CHARLIE.생성()).getId();
    }

    protected Long DAVID를_저장한다() {
        return users.save(DAVID.생성()).getId();
    }

    protected Long DARTH를_저장한다() {
        return users.save(DARTH.생성()).getId();
    }

    protected NoOffsetIdPage 페이지() {
        return new NoOffsetIdPage(null, 5);
    }

    protected MultipartFile 사진_파일(final String name) {
        return new MockMultipartFile("image", name,
                MediaType.MULTIPART_FORM_DATA_VALUE, "file content".getBytes());
    }

    protected MultipartFile 사진_파일() {
        return 사진_파일("image.png");
    }

    protected List<MultipartFile> 사진_파일(final List<String> names) {
        return names.stream().map(this::사진_파일).toList();
    }
}
