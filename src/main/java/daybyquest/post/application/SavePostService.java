package daybyquest.post.application;

import daybyquest.global.utils.MultipartFileUtils;
import daybyquest.image.domain.Image;
import daybyquest.image.domain.ImageIdentifierGenerator;
import daybyquest.image.domain.Images;
import daybyquest.post.domain.Post;
import daybyquest.post.domain.Posts;
import daybyquest.post.dto.request.SavePostRequest;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SavePostService {

    private static final String CATEGORY = "POST";

    private final Posts posts;

    private final Images images;

    private final ImageIdentifierGenerator generator;

    public SavePostService(final Posts posts, final Images images,
            final ImageIdentifierGenerator generator) {
        this.posts = posts;
        this.images = images;
        this.generator = generator;
    }

    @Transactional
    public Long invoke(final Long loginId, final SavePostRequest request,
            final List<MultipartFile> files) {
        final Post post = toEntity(loginId, request, toImageList(files));
        return posts.save(post);
    }

    private List<Image> toImageList(final List<MultipartFile> files) {
        return files.stream().map((file) -> {
            final String identifier = generator.generateIdentifier(CATEGORY, file.getOriginalFilename());
            return images.upload(identifier, MultipartFileUtils.getInputStream(file));
        }).toList();
    }

    private Post toEntity(final Long loginId, final SavePostRequest request, final List<Image> images) {
        return new Post(loginId, request.getQuestId(), request.getContent(), images);
    }
}
