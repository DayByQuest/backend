package daybyquest.group.application;

import daybyquest.global.utils.MultipartFileUtils;
import daybyquest.group.domain.Group;
import daybyquest.group.domain.Groups;
import daybyquest.group.dto.request.SaveGroupRequest;
import daybyquest.image.vo.Image;
import daybyquest.image.vo.ImageIdentifierGenerator;
import daybyquest.image.vo.Images;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveGroupService {

    private static final String CATEGORY = "GROUP";

    private final Groups groups;

    private final Images images;

    private final ImageIdentifierGenerator generator;

    public SaveGroupService(final Groups groups, final Images images,
            final ImageIdentifierGenerator generator) {
        this.groups = groups;
        this.images = images;
        this.generator = generator;
    }

    @Transactional
    public Long invoke(final Long loginId, final SaveGroupRequest request, final MultipartFile file) {
        final String identifier = generator.generateIdentifier(CATEGORY, file.getOriginalFilename());
        images.upload(identifier, MultipartFileUtils.getInputStream(file));
        final Group group = toEntity(request, identifier);
        return groups.save(loginId, group);
    }

    public Group toEntity(final SaveGroupRequest request, final String imageIdentifier) {
        return new Group(request.getInterest(), request.getName(), request.getDescription(),
                new Image(imageIdentifier));
    }
}
