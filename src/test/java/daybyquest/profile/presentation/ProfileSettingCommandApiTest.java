package daybyquest.profile.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.profile.application.SaveBadgeListService;
import daybyquest.profile.dto.request.SaveBadgeListRequest;
import daybyquest.support.test.ApiTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({ProfileSettingCommandApi.class})
public class ProfileSettingCommandApiTest extends ApiTest {

    @MockBean
    private SaveBadgeListService saveBadgeListService;

    @Test
    void 프로필_뱃지_목록을_설정한다() throws Exception {
        // given
        final SaveBadgeListRequest request = 뱃지_목록_수정_요청(List.of(1L, 2L, 3L));

        // when
        final ResultActions resultActions = mockMvc.perform(patch("/badge")
                .header(인증_헤더_이름, 사용자_인증_헤더)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("profile/updateBadgeIds"));
        then(saveBadgeListService).should().invoke(eq(로그인_ID), any(SaveBadgeListRequest.class));
    }

    private SaveBadgeListRequest 뱃지_목록_수정_요청(final List<Long> badgeIds) {
        final SaveBadgeListRequest request = new SaveBadgeListRequest();
        ReflectionTestUtils.setField(request, "badgeIds", badgeIds);
        return request;
    }
}
