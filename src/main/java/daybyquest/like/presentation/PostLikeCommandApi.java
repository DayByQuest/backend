package daybyquest.like.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.like.application.DeletePostLikeService;
import daybyquest.like.application.SavePostLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostLikeCommandApi {

    private final SavePostLikeService savePostLikeService;

    private final DeletePostLikeService deletePostLikeService;

    public PostLikeCommandApi(final SavePostLikeService savePostLikeService,
            final DeletePostLikeService deletePostLikeService) {
        this.savePostLikeService = savePostLikeService;
        this.deletePostLikeService = deletePostLikeService;
    }

    @PostMapping("/post/{postId}/like")
    @Authorization
    public ResponseEntity<Void> savePostLike(@UserId final Long loginId, @PathVariable final Long postId) {
        savePostLikeService.invoke(loginId, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{postId}/like")
    @Authorization
    public ResponseEntity<Void> deletePostLike(@UserId final Long loginId,
            @PathVariable final Long postId) {
        deletePostLikeService.invoke(loginId, postId);
        return ResponseEntity.ok().build();
    }
}
