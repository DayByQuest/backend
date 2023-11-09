package daybyquest.quest.dto.response;

import daybyquest.image.domain.Image;
import daybyquest.quest.domain.Quest;
import java.util.List;

public record QuestImagesResponse(List<String> imageIdentifiers, String description) {

    public static QuestImagesResponse of(final Quest quest) {
        return new QuestImagesResponse(quest.getImages().stream().map(Image::getIdentifier).toList(),
                quest.getImageDescription());
    }
}
