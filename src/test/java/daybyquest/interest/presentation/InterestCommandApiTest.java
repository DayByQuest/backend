package daybyquest.interest.presentation;

import static daybyquest.support.fixture.InterestFixtures.INTERST_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.interest.application.SaveInterestService;
import daybyquest.interest.domain.Interest;
import daybyquest.interest.dto.request.SaveInterestRequest;
import daybyquest.support.test.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({InterestCommandApi.class})
public class InterestCommandApiTest extends ApiTest {

    @MockBean
    private SaveInterestService saveInterestService;

    @Test
    void 관심사를_생성한다() throws Exception {
        // given
        final SaveInterestRequest saveInterestRequest = 관심사_생성_요청(INTERST_1.생성());
        final MockMultipartFile file = 사진을_전송한다("image");
        final MockMultipartFile request = 멀티파트_JSON을_전송한다("request", saveInterestRequest);

        // when
        final ResultActions resultActions = mockMvc.perform(
                multipart("/interest")
                        .file(file)
                        .file(request)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header(인증_헤더_이름, 어드민_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("interest/saveInterest",
                        requestParts(partWithName("image").description("사진"),
                                partWithName("request").description("요청")))
                );
        then(saveInterestService).should().invoke(any(), any());
    }

    private SaveInterestRequest 관심사_생성_요청(final Interest interest) {
        final SaveInterestRequest request = new SaveInterestRequest();
        ReflectionTestUtils.setField(request, "name", interest.getName());
        return request;
    }
}
