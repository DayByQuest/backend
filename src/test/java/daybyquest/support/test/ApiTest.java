package daybyquest.support.test;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.databind.ObjectMapper;
import daybyquest.global.config.WebMvcConfig;
import daybyquest.user.domain.Users;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

@Import({WebMvcConfig.class})
@AutoConfigureRestDocs
public abstract class ApiTest {

    protected final Long 로그인_ID = 1L;

    @MockBean
    private Users users;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUpAuthentication() {
        given(users.getById(1L)).willReturn(ALICE.생성(로그인_ID));
    }

    protected ResultHandler 문서화한다(final String identifier) {
        return document(identifier, preprocessRequest(
                        modifyHeaders().remove("Host").remove("Content-Length"), prettyPrint()),
                preprocessResponse(prettyPrint()));
    }

    protected ResultHandler 인증_상태로_문서화한다(final String identifier) {
        return document(identifier, preprocessRequest(modifyHeaders().remove("Host").remove("Content-Length")
                        , prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(headerWithName("Authorization").description("UserId 헤더")));
    }
}
