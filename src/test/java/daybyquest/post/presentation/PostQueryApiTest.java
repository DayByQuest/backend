package daybyquest.post.presentation;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.PostFixtures.POST_2;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.post.application.GetNeedCheckPostsService;
import daybyquest.post.application.GetPostByGroupIdService;
import daybyquest.post.application.GetPostByQuestIdService;
import daybyquest.post.application.GetPostByUsernameService;
import daybyquest.post.application.GetPostFromFollowingService;
import daybyquest.post.application.GetPostService;
import daybyquest.post.application.GetTrackerService;
import daybyquest.post.dto.response.NeedCheckPostsResponse;
import daybyquest.post.dto.response.PagePostsResponse;
import daybyquest.post.dto.response.SimplePostResponse;
import daybyquest.post.dto.response.TrackerResponse;
import daybyquest.support.test.ApiTest;
import daybyquest.user.query.Profile;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({PostQueryApi.class})
public class PostQueryApiTest extends ApiTest {

    @MockBean
    private GetPostService getPostService;

    @MockBean
    private GetPostFromFollowingService getPostFromFollowingService;

    @MockBean
    private GetPostByUsernameService getPostByUsernameService;

    @MockBean
    private GetTrackerService getTrackerService;

    @MockBean
    private GetPostByQuestIdService getPostByQuestIdService;

    @MockBean
    private GetPostByGroupIdService getPostByGroupIdService;

    @MockBean
    private GetNeedCheckPostsService getNeedCheckPostsService;

    @Test
    void 게시물을_조회한다() throws Exception {
        // given
        final Long postId = 2L;
        final Profile profile = ALICE.프로필(로그인_ID);
        given(getPostService.invoke(로그인_ID, postId)).willReturn(POST_1.퀘스트와_함께_응답(postId, profile));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/post/{postId}", postId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/getPost"));
        then(getPostService).should().invoke(로그인_ID, postId);
    }

    @Test
    void 팔로잉_피드를_조회한다() throws Exception {
        // given
        final Profile aliceProfile = ALICE.프로필(로그인_ID);
        final Profile bobProfile = BOB.프로필(3L);
        given(getPostFromFollowingService.invoke(eq(로그인_ID), any())).willReturn(
                new PagePostsResponse(List.of(POST_1.응답(2L, aliceProfile), POST_2.응답(3L, bobProfile)), 3L)
        );

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/feed")
                        .param("limit", "5")
                        .param("lastId", "0")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/getFeed"));
        then(getPostFromFollowingService).should().invoke(eq(로그인_ID), any());
    }

    @Test
    void 사용자_이름으로_게시물_목록을_조회한다() throws Exception {
        // given
        final Profile aliceProfile = ALICE.프로필(로그인_ID);
        given(getPostByUsernameService.invoke(eq(로그인_ID), eq(aliceProfile.getUsername()), any())).willReturn(
                new PagePostsResponse(List.of(POST_1.응답(2L, aliceProfile), POST_2.응답(3L, aliceProfile)), 3L)
        );

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/profile/{username}/post", ALICE.username)
                        .param("limit", "5")
                        .param("lastId", "0")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/getPostsByUsername"));
        then(getPostByUsernameService).should().invoke(eq(로그인_ID), eq(aliceProfile.getUsername()), any());
    }

    @Test
    void 트래커를_조회한다() throws Exception {
        // given
        final Profile aliceProfile = ALICE.프로필(로그인_ID);
        given(getTrackerService.invoke(로그인_ID, aliceProfile.getUsername())).willReturn(
                new TrackerResponse(List.of(1L, 1L, 1L, 0L, 1L)));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/profile/{username}/tracker", ALICE.username)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/getTracker"));
        then(getTrackerService).should().invoke(로그인_ID, aliceProfile.getUsername());
    }

    @Test
    void 퀘스트_ID로_게시물_목록을_조회한다() throws Exception {
        // given
        final Long questId = 5L;
        final Profile aliceProfile = ALICE.프로필(로그인_ID);
        given(getPostByQuestIdService.invoke(eq(로그인_ID), eq(questId), any())).willReturn(
                new PagePostsResponse(List.of(POST_1.응답(2L, aliceProfile), POST_2.응답(3L, aliceProfile)), 3L)
        );

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/quest/{questId}/post", questId)
                        .param("limit", "5")
                        .param("lastId", "0")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/getPostsByQuest"));
        then(getPostByQuestIdService).should().invoke(eq(로그인_ID), eq(questId), any());
    }

    @Test
    void 그룹_ID로_게시물_목록을_조회한다() throws Exception {
        // given
        final Long groupId = 5L;
        final Profile aliceProfile = ALICE.프로필(로그인_ID);
        given(getPostByGroupIdService.invoke(eq(로그인_ID), eq(groupId), any())).willReturn(
                new PagePostsResponse(List.of(POST_1.응답(2L, aliceProfile), POST_2.응답(3L, aliceProfile)), 3L)
        );

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/group/{questId}/post", groupId)
                        .param("limit", "5")
                        .param("lastId", "0")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/getPostsByGroup"));
        then(getPostByGroupIdService).should().invoke(eq(로그인_ID), eq(groupId), any());
    }

    @Test
    void 확인이_필요한_게시물_목록을_조회한다() throws Exception {
        // given
        final Long groupId = 5L;
        final List<SimplePostResponse> responses = List.of(
                SimplePostResponse.of(POST_1.생성(1L, 로그인_ID, 3L)),
                SimplePostResponse.of(POST_1.생성(2L, 로그인_ID, 3L))
        );
        given(getNeedCheckPostsService.invoke(로그인_ID, groupId)).willReturn(
                new NeedCheckPostsResponse(QUEST_1.imageIdentifiers, responses)
        );

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/group/{questId}/quest/failed", groupId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/getNeedCheckPosts"));
        then(getNeedCheckPostsService).should().invoke(로그인_ID, groupId);
    }
}
