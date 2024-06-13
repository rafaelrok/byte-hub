package br.com.rafaelvieira.bytehub.api.model.notification.follow;


import br.com.rafaelvieira.bytehub.api.model.profile.ProfileResponse;
import br.com.rafaelvieira.bytehub.api.model.user.UserResponse;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("follow")
public class NotificationMessageFollow {

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private ProfileResponse follower;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private ProfileResponse followed;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private UserResponse user;

    private NotificationType type;
}
