package daybyquest.profile.application;

import static daybyquest.global.error.ExceptionCode.NOT_OWNING_BADGE;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_2;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.profile.dto.request.SaveBadgeListRequest;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class SaveBadgeListServiceTest extends ServiceTest {

    @Autowired
    private SaveBadgeListService saveBadgeListService;

    @Test
    void 뱃지_목록을_저장한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badge1Id = badges.save(BADGE_1.생성()).getId();
        final Long badge2Id = badges.save(BADGE_2.생성()).getId();
        badges.save(BADGE_3.생성());

        ownings.saveByUserIdAndBadgeId(aliceId, badge1Id);
        ownings.saveByUserIdAndBadgeId(aliceId, badge2Id);

        final List<Long> expected = List.of(badge1Id, badge2Id);
        final SaveBadgeListRequest request = 뱃지_목록_수정_요청(expected);

        // when
        saveBadgeListService.invoke(aliceId, request);
        final List<Long> actual = profileSettings.getBadgeIdsById(aliceId);

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 소유하지_않은_뱃지는_지정할_수_없다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badgeId = badges.save(BADGE_1.생성()).getId();

        final SaveBadgeListRequest request = 뱃지_목록_수정_요청(List.of(badgeId));

        // when & then
        assertThatThrownBy(() -> saveBadgeListService.invoke(aliceId, request))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_OWNING_BADGE.getMessage());
    }

    private SaveBadgeListRequest 뱃지_목록_수정_요청(final List<Long> badgeIds) {
        final SaveBadgeListRequest request = new SaveBadgeListRequest();
        ReflectionTestUtils.setField(request, "badgeIds", badgeIds);
        return request;
    }
}
