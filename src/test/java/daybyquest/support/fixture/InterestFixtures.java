package daybyquest.support.fixture;

import daybyquest.image.domain.Image;
import daybyquest.interest.domain.Interest;
import daybyquest.interest.dto.response.InterestResponse;

public enum InterestFixtures {

    INTERST_1("관심사1", "관심사사진1"),
    INTERST_2("관심사2", "관심사사진2"),
    INTERST_3("관심사3", "관심사사진3"),
    INTERST_4("관심사4", "관심사사진4");

    public final String name;

    public final String imageIdentifier;

    InterestFixtures(final String name, final String imageIdentifier) {
        this.name = name;
        this.imageIdentifier = imageIdentifier;
    }

    public Interest 생성() {
        return new Interest(name, 사진());
    }

    public Image 사진() {
        return new Image(imageIdentifier);
    }

    public InterestResponse 응답() {
        return InterestResponse.of(생성());
    }
}
