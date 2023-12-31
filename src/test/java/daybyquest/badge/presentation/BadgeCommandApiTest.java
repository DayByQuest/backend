package daybyquest.badge.presentation;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.badge.application.SaveBadgeService;
import daybyquest.badge.domain.Badge;
import daybyquest.badge.dto.request.SaveBadgeRequest;
import daybyquest.support.test.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({BadgeCommandApi.class})
public class BadgeCommandApiTest extends ApiTest {

    @MockBean
    private SaveBadgeService saveBadgeService;

    @Test
    void 뱃지를_생성한다() throws Exception {
        // given
        final SaveBadgeRequest saveBadgeRequest = 뱃지_생성_요청(BADGE_1.생성());
        final MockMultipartFile file = 사진을_전송한다("image");
        final MockMultipartFile request = 멀티파트_JSON을_전송한다("request", saveBadgeRequest);

        // when
        final ResultActions resultActions = mockMvc.perform(
                multipart("/badge")
                        .file(file)
                        .file(request)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header(인증_헤더_이름, 어드민_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("badge/saveBadge",
                        requestParts(partWithName("image").description("사진"),
                                partWithName("request").description("요청")))
                );
        then(saveBadgeService).should().invoke(any(), any());
    }

    private SaveBadgeRequest 뱃지_생성_요청(final Badge badge) {
        final SaveBadgeRequest request = new SaveBadgeRequest();
        ReflectionTestUtils.setField(request, "name", badge.getName());
        return request;
    }
}
