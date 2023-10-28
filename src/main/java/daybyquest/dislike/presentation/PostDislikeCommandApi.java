package daybyquest.dislike.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.dislike.application.DeletePostDislikeService;
import daybyquest.dislike.application.SavePostDislikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostDislikeCommandApi {

    private final SavePostDislikeService savePostDislikeService;

    private final DeletePostDislikeService deletePostDislikeService;

    public PostDislikeCommandApi(final SavePostDislikeService savePostDislikeService,
            final DeletePostDislikeService deletePostDislikeService) {
        this.savePostDislikeService = savePostDislikeService;
        this.deletePostDislikeService = deletePostDislikeService;
    }

    @PostMapping("/post/{postId}/dislike")
    @Authorization
    public ResponseEntity<Void> savePostDislike(@UserId final Long loginId, @PathVariable final Long postId) {
        savePostDislikeService.invoke(loginId, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{postId}/dislike")
    @Authorization
    public ResponseEntity<Void> deletePostDislike(@UserId final Long loginId,
            @PathVariable final Long postId) {
        deletePostDislikeService.invoke(loginId, postId);
        return ResponseEntity.ok().build();
    }
}
