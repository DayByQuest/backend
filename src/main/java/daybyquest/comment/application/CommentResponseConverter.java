package daybyquest.comment.application;

import daybyquest.comment.dto.response.CommentResponse;
import daybyquest.comment.query.CommentData;
import daybyquest.user.query.Profile;
import daybyquest.user.query.ProfileDao;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
class CommentResponseConverter {

    private final ProfileDao profileDao;

    CommentResponseConverter(final ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    List<CommentResponse> convertFromCommentData(final Long loginId,
            final List<CommentData> commentData) {
        final Set<Long> userIds = commentData.stream().map(CommentData::getUserId)
                .collect(Collectors.toSet());
        final Map<Long, Profile> profiles = profileDao.findMapByUserIdIn(loginId, userIds);
        return commentData.stream().map((cd) ->
                CommentResponse.of(cd, profiles.get(cd.getUserId()))
        ).toList();
    }
}
