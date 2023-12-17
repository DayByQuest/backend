package daybyquest.group.application;

import daybyquest.group.domain.Group;
import daybyquest.group.domain.Groups;
import daybyquest.group.dto.request.SaveGroupRequest;
import daybyquest.image.application.ImageService;
import daybyquest.image.domain.Image;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveGroupService {

    private static final String CATEGORY = "GROUP";

    private final Groups groups;

    private final ImageService imageService;

    public SaveGroupService(final Groups groups, final ImageService imageService) {
        this.groups = groups;
        this.imageService = imageService;
    }

    @Transactional
    public Long invoke(final Long loginId, final SaveGroupRequest request, final MultipartFile file) {
        final Image image = imageService.convertToImage(CATEGORY, file);
        final Group group = toEntity(request, image);
        return groups.save(loginId, group).getId();
    }

    public Group toEntity(final SaveGroupRequest request, final Image image) {
        return new Group(request.getInterest(), request.getName(), request.getDescription(),
                image);
    }
}
