package daybyquest.post.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.post.application.GetPostService;
import daybyquest.post.application.JudgePostService;
import daybyquest.post.application.SavePostService;
import daybyquest.post.application.SwipePostService;
import daybyquest.post.dto.request.JudgePostRequest;
import daybyquest.post.dto.request.SavePostRequest;
import daybyquest.post.dto.response.PostResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PostCommandApi {

    private final SavePostService savePostService;

    private final SwipePostService swipePostService;

    private final GetPostService getPostService;

    private final JudgePostService judgePostService;

    public PostCommandApi(final SavePostService savePostService, final SwipePostService swipePostService,
            final GetPostService getPostService, final JudgePostService judgePostService) {
        this.savePostService = savePostService;
        this.swipePostService = swipePostService;
        this.getPostService = getPostService;
        this.judgePostService = judgePostService;
    }

    @PostMapping("/post")
    @Authorization
    public ResponseEntity<PostResponse> savePost(final AccessUser accessUser,
            @RequestPart SavePostRequest request,
            @RequestPart List<MultipartFile> files) {
        final Long postId = savePostService.invoke(accessUser.getId(), request, files);
        final PostResponse response = getPostService.invoke(accessUser.getId(), postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/post/{postId}/swipe")
    @Authorization
    public ResponseEntity<Void> swipePost(final AccessUser accessUser, @PathVariable final Long postId) {
        swipePostService.invoke(accessUser.getId(), postId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/post/{postId}/judge")
    @Authorization(admin = true)
    public ResponseEntity<Void> swipePost(final AccessUser accessUser,
            @PathVariable final Long postId, @RequestBody final JudgePostRequest request) {
        judgePostService.invoke(postId, request);
        return ResponseEntity.ok().build();
    }
}
