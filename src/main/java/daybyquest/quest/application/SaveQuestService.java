package daybyquest.quest.application;

import daybyquest.global.utils.MultipartFileUtils;
import daybyquest.image.domain.Image;
import daybyquest.image.domain.ImageIdentifierGenerator;
import daybyquest.image.domain.Images;
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

    private final Images images;

    private final ImageIdentifierGenerator generator;

    public SaveQuestService(final Quests quests, final Images images,
            final ImageIdentifierGenerator generator) {
        this.quests = quests;
        this.images = images;
        this.generator = generator;
    }

    @Transactional
    public Long invoke(final SaveQuestRequest request, final List<MultipartFile> files) {
        final Quest quest = toEntity(request, toImageList(files));
        return quests.save(quest);
    }

    private List<Image> toImageList(final List<MultipartFile> files) {
        return files.stream().map((file) -> {
            final String identifier = generator.generateIdentifier(CATEGORY, file.getOriginalFilename());
            return images.upload(identifier, MultipartFileUtils.getInputStream(file));
        }).toList();
    }

    private Quest toEntity(final SaveQuestRequest request, final List<Image> images) {
        return Quest.createNormalQuest(request.getBadgeId(), request.getInterest(), request.getTitle(),
                request.getContent(), request.getRewardCount(), request.getImageDescription(), images);
    }
}
