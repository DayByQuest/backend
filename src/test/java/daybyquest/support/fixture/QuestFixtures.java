package daybyquest.support.fixture;

import daybyquest.badge.domain.Badge;
import daybyquest.group.domain.Group;
import daybyquest.image.vo.Image;
import daybyquest.quest.domain.Quest;
import java.time.LocalDateTime;
import java.util.List;

public enum QuestFixtures {
    QUEST_1("일반퀘스트1", "퀘스트1입니다", "관심사", 5L, null,
            "퀘스트1 사진 설명입니다", List.of("퀘스트사진1_1", "퀘스트사진1_2", "퀘스트사진1_3"), "라벨1"),
    QUEST_2("일반퀘스트2", "퀘스트2입니다", "관심사", 10L, null,
            "퀘스트2 사진 설명입니다", List.of("퀘스트사진2_1", "퀘스트사진2_2", "퀘스트사진2_3"), "라벨2"),
    QUEST_3("일반퀘스트3", "퀘스트3입니다", "관심사", 15L, null,
            "퀘스트3 사진 설명입니다", List.of("퀘스트사진3_1", "퀘스트사진3_2", "퀘스트사진3_3"), "라벨3"),
    QUEST_WITHOUT_REWARD("일반퀘스트", "퀘스트입니다", "관심사", null, null,
            "퀘스트 사진 설명입니다", List.of("퀘스트사진_1", "퀘스트사진_2", "퀘스트사진_3"), "라벨4");

    public final String title;

    public final String content;

    public final String interest;

    public final Long rewardCount;

    public final LocalDateTime expiredAt;

    public final String imageDescription;

    public final List<String> imageIdentifiers;

    public final String label;

    QuestFixtures(final String title, final String content, final String interest,
            final Long rewardCount, final LocalDateTime expiredAt,
            final String imageDescription, final List<String> imageIdentifiers, final String label) {
        this.title = title;
        this.content = content;
        this.interest = interest;
        this.rewardCount = rewardCount;
        this.expiredAt = expiredAt;
        this.imageDescription = imageDescription;
        this.imageIdentifiers = imageIdentifiers;
        this.label = label;
    }

    public Quest 일반_퀘스트_생성() {
        return Quest.createNormalQuest(null, interest, title, content, null, imageDescription,
                사진_목록());
    }

    public Quest 일반_퀘스트_생성(final Long badgeId) {
        return Quest.createNormalQuest(badgeId, interest, title, content, rewardCount, imageDescription,
                사진_목록());
    }

    public Quest 일반_퀘스트_생성(final Badge badge) {
        return 일반_퀘스트_생성(badge.getId());
    }

    public Quest 그룹_퀘스트_생성(final Long groupId) {
        return Quest.createGroupQuest(groupId, interest, title, content, expiredAt, imageDescription,
                사진_목록());
    }

    public Quest 그룹_퀘스트_생성(final Group group) {
        return 일반_퀘스트_생성(group.getId());
    }

    public List<Image> 사진_목록() {
        return imageIdentifiers.stream().map(Image::new).toList();
    }
}
