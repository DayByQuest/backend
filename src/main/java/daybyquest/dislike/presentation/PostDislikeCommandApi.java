package daybyquest.dislike.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
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
    public ResponseEntity<Void> savePostDislike(final AccessUser accessUser,
            @PathVariable final Long postId) {
        savePostDislikeService.invoke(accessUser.getId(), postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{postId}/dislike")
    @Authorization
    public ResponseEntity<Void> deletePostDislike(final AccessUser accessUser,
            @PathVariable final Long postId) {
        deletePostDislikeService.invoke(accessUser.getId(), postId);
        return ResponseEntity.ok().build();
    }
}
