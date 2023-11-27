package daybyquest.group.presentation;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.GroupFixtures.GROUP_2;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.group.application.CheckGroupNameService;
import daybyquest.group.application.GetGroupProfileService;
import daybyquest.group.application.GetGroupUsersService;
import daybyquest.group.application.GetGroupsByInterestService;
import daybyquest.group.application.GetGroupsService;
import daybyquest.group.application.RecommendGroupsService;
import daybyquest.group.application.SearchGroupService;
import daybyquest.group.dto.response.MultipleGroupsResponse;
import daybyquest.group.dto.response.PageGroupUsersResponse;
import daybyquest.group.dto.response.PageGroupsResponse;
import daybyquest.support.test.ApiTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({GroupQueryApi.class})
public class GroupQueryApiTest extends ApiTest {

    @MockBean
    private CheckGroupNameService checkGroupNameService;

    @MockBean
    private GetGroupProfileService getGroupProfileService;

    @MockBean
    private GetGroupUsersService getGroupUsersService;

    @MockBean
    private GetGroupsService getGroupsService;

    @MockBean
    private RecommendGroupsService recommendGroupsService;

    @MockBean
    private SearchGroupService searchGroupService;

    @MockBean
    private GetGroupsByInterestService getGroupsByInterestService;

    @Test
    void 그룹_이름_중복_검사를_한다() throws Exception {
        // given & when
        final ResultActions resultActions = mockMvc.perform(
                get("/group/{groupName}/check", GROUP_1.name));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(문서화한다("group/checkName"));
        then(checkGroupNameService).should().invoke(GROUP_1.name);
    }

    @Test
    void 그룹_프로필을_조회한다() throws Exception {
        // given
        final Long groupId = 2L;
        given(getGroupProfileService.invoke(로그인_ID, groupId)).willReturn(GROUP_1.응답(groupId));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/group/{groupId}", groupId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("group/getGroup"));
        then(getGroupProfileService).should().invoke(로그인_ID, groupId);
    }

    @Test
    void 그룹원_목록을_조회한다() throws Exception {
        // given
        final Long groupId = 2L;
        given(getGroupUsersService.invoke(eq(로그인_ID), eq(groupId), any()))
                .willReturn(new PageGroupUsersResponse(List.of(BOB.그룹원_응답(1L), ALICE.그룹원_응답(2L)), 2L));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/group/{groupId}/user", groupId)
                        .param("limit", "5")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("group/getGroupUsers"));
        then(getGroupUsersService).should().invoke(eq(로그인_ID), eq(groupId), any());
    }

    @Test
    void 가입한_그룹_목록을_조회한다() throws Exception {
        // given
        given(getGroupsService.invoke(로그인_ID))
                .willReturn(new MultipleGroupsResponse(List.of(GROUP_1.응답(1L), GROUP_2.응답(2L))));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/group")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("group/getGroups"));
        then(getGroupsService).should().invoke(로그인_ID);
    }

    @Test
    void 그룹을_추천받는다() throws Exception {
        // given
        given(recommendGroupsService.invoke(로그인_ID))
                .willReturn(new MultipleGroupsResponse(List.of(GROUP_1.응답(1L), GROUP_2.응답(2L))));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/group/recommendation")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("group/recommendGroups"));
        then(recommendGroupsService).should().invoke(로그인_ID);
    }

    @Test
    void 그룹을_검색한다() throws Exception {
        // given
        final String keyword = "키워드";
        given(searchGroupService.invoke(eq(로그인_ID), eq(keyword), any()))
                .willReturn(new PageGroupsResponse(List.of(GROUP_1.응답(1L), GROUP_2.응답(2L)), 2L));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/search/group")
                        .param("keyword", keyword)
                        .param("limit", "5")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("group/searchGroups"));
        then(searchGroupService).should().invoke(eq(로그인_ID), eq(keyword), any());
    }

    @Test
    void 관심사로_그룹_목록을_조회한다() throws Exception {
        // given
        final String interest = "관심사1";
        given(getGroupsByInterestService.invoke(eq(로그인_ID), eq(interest), any()))
                .willReturn(new PageGroupsResponse(List.of(GROUP_1.응답(1L), GROUP_2.응답(2L)), 2L));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/groups")
                        .param("interest", interest)
                        .param("limit", "5")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("group/getGroupsByInterest"));
        then(getGroupsByInterestService).should().invoke(eq(로그인_ID), eq(interest), any());
    }
}
