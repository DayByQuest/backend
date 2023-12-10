package daybyquest.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.support.config.StubImages;
import daybyquest.support.test.ServiceTest;
import daybyquest.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class UpdateUserImageServiceTest extends ServiceTest {

    @Autowired
    private UpdateUserImageService updateUserImageService;

    @Autowired
    private StubImages images;

    @Test
    void 사용자_사진을_수정한다() {
        // given
        final Long id = ALICE를_저장한다();
        final MultipartFile file = 사진_파일();

        // when
        updateUserImageService.invoke(id, file);

        // then
        final User user = users.getById(id);
        assertAll(() -> {
            assertThat(user.getImageIdentifier()).isEqualTo(file.getOriginalFilename());
            assertThat(images.hasUploadImage(file.getOriginalFilename())).isTrue();
        });
    }
}
