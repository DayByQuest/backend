package daybyquest.like.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
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
    public ResponseEntity<Void> savePostLike(final AccessUser accessUser, @PathVariable final Long postId) {
        savePostLikeService.invoke(accessUser.getId(), postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{postId}/like")
    @Authorization
    public ResponseEntity<Void> deletePostLike(final AccessUser accessUser,
            @PathVariable final Long postId) {
        deletePostLikeService.invoke(accessUser.getId(), postId);
        return ResponseEntity.ok().build();
    }
}
