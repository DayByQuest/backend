package daybyquest.support.fixture;

import daybyquest.group.domain.Group;
import daybyquest.image.vo.Image;
import org.springframework.test.util.ReflectionTestUtils;

public enum GroupFixtures {

    GROUP_1("그룹1", "관심사", "설명1", "group1.png"),
    GROUP_2("그룹2", "관심사", "설명2", "group2.png"),
    GROUP_3("그룹3", "관심사", "설명3", "group3.png"),
    GROUP_4("그룹4", "관심사", "설명4", "group4.png");

    public final String name;

    public final String interest;

    public final String description;

    public final String imageIdentifier;

    GroupFixtures(final String name, final String interest, final String description,
            final String imageIdentifier) {
        this.name = name;
        this.interest = interest;
        this.description = description;
        this.imageIdentifier = imageIdentifier;
    }

    public Group 생성(final Long id) {
        final Group group = new Group(this.interest, this.name, this.description, 대표_사진());
        ReflectionTestUtils.setField(group, "id", id);
        return group;
    }

    public Group 생성() {
        return 생성(null);
    }

    public Image 대표_사진() {
        return new Image(this.imageIdentifier);
    }
}
