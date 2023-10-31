package daybyquest.support.test;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import daybyquest.global.config.WebMvcConfig;
import daybyquest.user.domain.Users;
import daybyquest.user.presentation.UserCommandApi;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({UserCommandApi.class})
@Import({WebMvcConfig.class})
@AutoConfigureRestDocs
public abstract class ApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private Users users;

    @BeforeEach
    void setUp() {
        given(users.getById(1L)).willReturn(ALICE.생성(1L));
    }

}
