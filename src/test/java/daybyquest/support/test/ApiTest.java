package daybyquest.support.test;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.user.domain.UserState.ADMIN;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import daybyquest.global.config.WebMvcConfig;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.request.RequestPartsSnippet;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

@Import({WebMvcConfig.class})
@AutoConfigureRestDocs
public abstract class ApiTest {

    protected final String 인증_헤더_이름 = "Authorization";

    protected final String 사용자_인증_헤더 = "UserId 1";

    protected final String 어드민_인증_헤더 = "UserId 2";

    protected final Long 로그인_ID = 1L;

    protected final Long 어드민_ID = 2L;

    @MockBean
    private Users users;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUpAuthentication() {
        given(users.getById(로그인_ID)).willReturn(ALICE.생성(로그인_ID));
        final User admin = BOB.생성(로그인_ID);
        ReflectionTestUtils.setField(admin, "state", ADMIN);
        given(users.getById(어드민_ID)).willReturn(admin);
    }

    protected MockMultipartFile 사진을_전송한다(final String name) {
        return new MockMultipartFile(name, "image.png",
                MediaType.MULTIPART_FORM_DATA_VALUE, "file content".getBytes());
    }

    protected MockMultipartFile 멀티파트_JSON을_전송한다(final String name, final Object request)
            throws JsonProcessingException {
        return new MockMultipartFile(name, "request",
                MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(request));
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

    protected ResultHandler 인증_상태로_문서화한다(final String identifier, final RequestPartsSnippet snippet) {
        return document(identifier, preprocessRequest(modifyHeaders().remove("Host").remove("Content-Length")
                        , prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(headerWithName("Authorization").description("UserId 헤더")),
                snippet);
    }
}
