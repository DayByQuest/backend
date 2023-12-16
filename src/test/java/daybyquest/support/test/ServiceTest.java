package daybyquest.support.test;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static daybyquest.support.fixture.UserFixtures.DARTH;
import static daybyquest.support.fixture.UserFixtures.DAVID;

import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.support.config.StubInfraConfig;
import daybyquest.support.util.DatabaseCleaner;
import daybyquest.user.domain.Users;
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

    @MockBean
    protected DateTimeProvider dataTimeProvider;

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

    protected MultipartFile 사진_파일() {
        return new MockMultipartFile("image", "image.png",
                MediaType.MULTIPART_FORM_DATA_VALUE, "file content".getBytes());
    }
}
