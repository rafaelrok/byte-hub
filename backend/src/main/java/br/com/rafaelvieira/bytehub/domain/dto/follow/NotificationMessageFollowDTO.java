package br.com.rafaelvieira.bytehub.domain.dto.follow;

import br.com.rafaelvieira.bytehub.domain.dto.BaseDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private boolean reading;

    public NotificationMessageFollowDTO() {
        super();
    }

    @JsonCreator
    public NotificationMessageFollowDTO(
            @JsonProperty("id") Long id,
            @JsonProperty("followerId") Long followerId,
            @JsonProperty("followedId") Long followedId,
            @JsonProperty("username") String username,
            @JsonProperty("type") NotificationType type,
            @JsonProperty("reading") boolean reading) {
        super(id);
        this.followerId = followerId;
        this.followedId = followedId;
        this.username = username;
        this.type = type;
        this.reading = reading;
    }

    public NotificationMessageFollowDTO(Long followerId,
                                        Long followedId,
                                        String username,
                                        NotificationType type,
                                        boolean reading) {
        super();
        this.followerId = followerId;
        this.followedId = followedId;
        this.username = username;
        this.type = type;
        this.reading = reading;
    }


}
