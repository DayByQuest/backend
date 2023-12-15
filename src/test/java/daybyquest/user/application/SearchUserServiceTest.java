package daybyquest.user.application;

import static daybyquest.support.fixture.UserFixtures.DARTH;
import static daybyquest.support.fixture.UserFixtures.DAVID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.support.test.ServiceTest;
import daybyquest.user.dto.response.PageProfilesResponse;
import daybyquest.user.dto.response.ProfileResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchUserServiceTest extends ServiceTest {

    @Autowired
    private SearchUserService searchUserService;

    @Test
    void 사용자를_검색한다() {
        // given
        final Long id = DAVID를_저장한다();
        DARTH를_저장한다();
        ALICE를_저장한다();
        final List<String> expected = List.of(DARTH.username, DAVID.username);

        // when
        final PageProfilesResponse response = searchUserService.invoke(id, "da", 페이지());
        final List<String> actual = response.users().stream().map(ProfileResponse::username).toList();

        // then
        assertAll(() -> {
            assertThat(response.users()).hasSize(2);
            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        });
    }
}
