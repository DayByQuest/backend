package daybyquest.quest.application;

import daybyquest.global.utils.MultipartFileUtils;
import daybyquest.image.vo.Image;
import daybyquest.image.vo.ImageIdentifierGenerator;
import daybyquest.image.vo.Images;
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
    public void invoke(final SaveQuestRequest request, final List<MultipartFile> files) {
        final Quest quest = toEntity(request, toImageList(files));
        quests.save(quest);
    }

    private List<Image> toImageList(final List<MultipartFile> files) {
        return files.stream().map((file) -> {
            final String identifier = generator.generateIdentifier(CATEGORY, file.getOriginalFilename());
            images.upload(identifier, MultipartFileUtils.getInputStream(file));
            return new Image(identifier);
        }).toList();
    }

    private Quest toEntity(final SaveQuestRequest request, final List<Image> images) {
        return Quest.createNormalQuest(request.getBadgeId(), request.getInterest(), request.getTitle(),
                request.getContent(), request.getRewardCount(), request.getImageDescription(), images);
    }
}
