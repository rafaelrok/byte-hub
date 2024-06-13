package br.com.rafaelvieira.bytehub.domain.dto.follow;

import br.com.rafaelvieira.bytehub.domain.dto.BaseDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationMessageFollowDTO extends BaseDTO<Long> {

    private Long followerId;
    private Long followedId;
    private String username;
    private NotificationType type;


    public NotificationMessageFollowDTO() {
    }

    public NotificationMessageFollowDTO(Long followerId,
                                        Long followedId,
                                        String username,
                                        NotificationType type) {
        this.followerId = followerId;
        this.followedId = followedId;
        this.username = username;
        this.type = type;
    }

    public NotificationMessageFollowDTO(Long id,
                                        Long followerId,
                                        Long followedId,
                                        String username,
                                        NotificationType type) {
        super(id);
        this.followerId = followerId;
        this.followedId = followedId;
        this.username = username;
        this.type = type;
    }


}
