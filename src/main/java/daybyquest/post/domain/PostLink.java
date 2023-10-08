package daybyquest.post.domain;


import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class PostLink {

    private Long questId;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private PostLinkState state;

    public PostLink(Long questId) {
        this.questId = questId;
        this.state = PostLinkState.NOT_DECIDED;
    }
}
