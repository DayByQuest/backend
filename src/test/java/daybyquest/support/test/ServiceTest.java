package daybyquest.support.test;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;

import daybyquest.support.config.StubInfraConfig;
import daybyquest.support.util.DatabaseCleaner;
import daybyquest.user.domain.Users;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest(classes = {StubInfraConfig.class})
public class ServiceTest {

    @Autowired
    private DatabaseCleaner cleaner;

    @Autowired
    protected Users users;

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

    protected MultipartFile 사진_파일() {
        return new MockMultipartFile("image", "image.png",
                MediaType.MULTIPART_FORM_DATA_VALUE, "file content".getBytes());
    }
}
