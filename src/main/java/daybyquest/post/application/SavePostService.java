package daybyquest.post.application;

import daybyquest.image.application.ImageService;
import daybyquest.image.domain.Image;
import daybyquest.post.domain.Post;
import daybyquest.post.domain.Posts;
import daybyquest.post.dto.request.SavePostRequest;
import daybyquest.quest.domain.Quests;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SavePostService {

    private static final String CATEGORY = "POST";

    private final Posts posts;

    private final ImageService imageService;

    private final PostClient postClient;

    private final Quests quests;

    public SavePostService(final Posts posts, final ImageService imageService, final PostClient postClient,
            final Quests quests) {
        this.posts = posts;
        this.imageService = imageService;
        this.postClient = postClient;
        this.quests = quests;
    }

    @Transactional
    public Long invoke(final Long loginId, final SavePostRequest request,
            final List<MultipartFile> files) {
        final Post post = posts.save(toEntity(loginId, request, toImageList(files)));
        requestJudge(post);
        return post.getId();
    }

    private List<Image> toImageList(final List<MultipartFile> files) {
        return files.stream().map((file) -> imageService.convertToImage(CATEGORY, file)).toList();
    }

    private Post toEntity(final Long loginId, final SavePostRequest request, final List<Image> images) {
        return new Post(loginId, request.getQuestId(), request.getContent(), images);
    }

    private void requestJudge(final Post post) {
        if (post.isQuestLinked()) {
            postClient.requestJudge(post.getId(), quests.getLabelById(post.getQuestId()),
                    post.getImages().stream().map(Image::getIdentifier).toList());
        }
    }
}
