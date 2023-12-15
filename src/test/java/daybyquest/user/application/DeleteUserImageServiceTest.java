package daybyquest.user.application;

import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.image.domain.BaseImageProperties;
import daybyquest.support.config.StubImages;
import daybyquest.support.test.ServiceTest;
import daybyquest.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteUserImageServiceTest extends ServiceTest {

    @Autowired
    private DeleteUserImageService deleteUserImageService;

    @Autowired
    private BaseImageProperties properties;

    @Autowired
    private StubImages images;

    @Test
    void 사용자_사진을_삭제한다() {
        // given
        final Long id = BOB을_저장한다();
        images.upload(BOB.imageIdentifier, null);

        // when
        deleteUserImageService.invoke(id);

        // then
        final User user = users.getById(id);
        assertAll(() -> {
            assertThat(images.hasUploadImage(BOB.imageIdentifier)).isFalse();
            assertThat(user.getImageIdentifier()).isEqualTo(properties.getUserIdentifier());
        });
    }

    @Test
    void 기본_사진이라면_삭제하지_않는다() {
        // given
        final Long id = ALICE를_저장한다();
        images.upload(properties.getUserIdentifier(), null);

        // when
        deleteUserImageService.invoke(id);

        // then
        final User user = users.getById(id);
        assertAll(() -> {
            assertThat(images.hasUploadImage(properties.getUserIdentifier())).isTrue();
            assertThat(user.getImageIdentifier()).isEqualTo(properties.getUserIdentifier());
        });
    }
}
