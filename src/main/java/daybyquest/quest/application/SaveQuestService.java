package daybyquest.quest.application;

import daybyquest.badge.domain.Badge;
import daybyquest.badge.domain.Badges;
import daybyquest.image.application.ImageService;
import daybyquest.image.domain.Image;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import daybyquest.quest.dto.request.SaveQuestRequest;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveQuestService {

    private static final String CATEGORY = "QUEST";

    private final Quests quests;

    private final Badges badges;

    private final ImageService imageService;

    private final QuestClient questClient;

    public SaveQuestService(final Quests quests, final Badges badges, final ImageService imageService,
            final QuestClient questClient) {
        this.quests = quests;
        this.badges = badges;
        this.imageService = imageService;
        this.questClient = questClient;
    }

    @Transactional
    public Long invoke(final SaveQuestRequest request, final List<MultipartFile> files) {
        final Quest quest = toEntity(request, toImageList(files));
        final Long questId = quests.save(quest);
        questClient.requestLabels(questId, quest.getImages().stream().map(Image::getIdentifier).toList());
        return questId;
    }

    private List<Image> toImageList(final List<MultipartFile> files) {
        return files.stream().map((file) -> imageService.convertToImage(CATEGORY, file)).toList();
    }

    private Quest toEntity(final SaveQuestRequest request, final List<Image> images) {
        final Badge badge = badges.getById(request.getBadgeId());
        return Quest.createNormalQuest(badge.getId(), request.getImageDescription(), images,
                badge.getImage());
    }
}
