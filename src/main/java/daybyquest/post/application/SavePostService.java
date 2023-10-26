package daybyquest.post.application;

import daybyquest.global.utils.MultipartFileUtils;
import daybyquest.image.vo.Image;
import daybyquest.image.vo.ImageIdentifierGenerator;
import daybyquest.image.vo.Images;
import daybyquest.post.domain.Post;
import daybyquest.post.domain.Posts;
import daybyquest.post.dto.request.SavePostRequest;
import daybyquest.user.domain.Users;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SavePostService {

    private static final String CATEGORY = "POST";

    private final Users users;

    private final Posts posts;

    private final Images images;

    private final ImageIdentifierGenerator generator;

    public SavePostService(final Users users, final Posts posts, final Images images,
            final ImageIdentifierGenerator generator) {
        this.users = users;
        this.posts = posts;
        this.images = images;
        this.generator = generator;
    }

    @Transactional
    public Long invoke(final Long loginId, final SavePostRequest request,
            final List<MultipartFile> files) {
        users.validateExistentById(loginId);
        final Post post = toEntity(loginId, request, toImageList(files));
        return posts.save(post);
    }

    private List<Image> toImageList(final List<MultipartFile> files) {
        return files.stream().map((file) -> {
            final String identifier = generator.generateIdentifier(CATEGORY, file.getOriginalFilename());
            images.upload(identifier, MultipartFileUtils.getInputStream(file));
            return new Image(identifier);
        }).toList();
    }

    private Post toEntity(final Long loginId, final SavePostRequest request, final List<Image> images) {
        return new Post(loginId, request.getQuestId(), request.getContent(), images);
    }
}
