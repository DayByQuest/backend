package daybyquest.badge.application;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.badge.domain.Badge;
import daybyquest.badge.dto.request.SaveBadgeRequest;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

public class SaveBadgeServiceTest extends ServiceTest {

    @Autowired
    private SaveBadgeService saveBadgeService;

    @Test
    void 뱃지를_생성한다() {
        // given
        final SaveBadgeRequest request = 뱃지_생성_요청(BADGE_1.생성());
        final MultipartFile file = 사진_파일(BADGE_1.imageIdentifier);

        // when
        final Long badgeId = saveBadgeService.invoke(request, file);
        final Badge actual = badges.getById(badgeId);

        // then
        assertAll(() -> {
            assertThat(actual.getName()).isEqualTo(BADGE_1.name);
            assertThat(actual.getImage().getIdentifier()).isEqualTo(BADGE_1.imageIdentifier);
        });
    }

    private SaveBadgeRequest 뱃지_생성_요청(final Badge badge) {
        final SaveBadgeRequest request = new SaveBadgeRequest();
        ReflectionTestUtils.setField(request, "name", badge.getName());
        return request;
    }
}
