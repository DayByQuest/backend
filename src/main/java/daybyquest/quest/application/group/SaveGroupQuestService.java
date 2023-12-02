package daybyquest.quest.application.group;

import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUsers;
import daybyquest.group.domain.Groups;
import daybyquest.image.application.ImageService;
import daybyquest.image.domain.Image;
import daybyquest.quest.application.QuestClient;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import daybyquest.quest.dto.request.SaveGroupQuestRequest;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveGroupQuestService {

    private static final String CATEGORY = "QUEST";

    private final Quests quests;

    private final Groups groups;

    private final GroupUsers groupUsers;

    private final ImageService imageService;

    private final QuestClient questClient;

    public SaveGroupQuestService(final Quests quests, final Groups groups, final GroupUsers groupUsers,
            final ImageService imageService, final QuestClient questClient) {
        this.quests = quests;
        this.groups = groups;
        this.groupUsers = groupUsers;
        this.imageService = imageService;
        this.questClient = questClient;
    }

    @Transactional
    public Long invoke(final Long loginId, final SaveGroupQuestRequest request,
            final List<MultipartFile> files) {
        groupUsers.validateGroupManager(loginId, request.getGroupId());
        final Quest quest = toEntity(request, toImageList(files));
        final Long questId = quests.save(quest);
        questClient.requestLabels(questId, quest.getImages().stream().map(Image::getIdentifier).toList(),
                quest.getImageDescription());
        return questId;
    }

    private List<Image> toImageList(final List<MultipartFile> files) {
        return files.stream().map((file) -> imageService.convertToImage(CATEGORY, file)).toList();
    }

    private Quest toEntity(final SaveGroupQuestRequest request, final List<Image> images) {
        final Group group = groups.getById(request.getGroupId());
        return Quest.createGroupQuest(group.getId(), request.getImageDescription(), images,
                group.getImage());
    }
}
