package daybyquest.relation.application;

import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.relation.domain.Follow;
import daybyquest.support.test.ServiceTest;
import daybyquest.user.dto.response.PageProfilesResponse;
import daybyquest.user.dto.response.ProfileResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetFollowingsServiceTest extends ServiceTest {

    @Autowired
    private GetFollowingsService getFollowingsService;

    @Test
    void 팔로잉_목록을_조회한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();
        final Long charlieId = CHARLIE를_저장한다();
        final Long davidId = DAVID를_저장한다();
        follows.save(new Follow(aliceId, bobId));
        follows.save(new Follow(aliceId, charlieId));
        follows.save(new Follow(davidId, aliceId));

        final List<String> expected = List.of(BOB.username, CHARLIE.username);

        // when
        final PageProfilesResponse response = getFollowingsService.invoke(aliceId, 페이지());
        final List<String> actual = response.users().stream().map(ProfileResponse::username).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
