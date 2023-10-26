package daybyquest.post.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.post.application.SavePostService;
import daybyquest.post.dto.request.SavePostRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PostCommandApi {

    private final SavePostService savePostService;

    public PostCommandApi(final SavePostService savePostService) {
        this.savePostService = savePostService;
    }

    @PostMapping("/post")
    @Authorization
    public ResponseEntity<Long> savePost(@UserId final Long loginId, @RequestPart SavePostRequest request,
            @RequestPart List<MultipartFile> files) {
        final Long postId = savePostService.invoke(loginId, request, files);
        return ResponseEntity.ok(postId);
    }
}
