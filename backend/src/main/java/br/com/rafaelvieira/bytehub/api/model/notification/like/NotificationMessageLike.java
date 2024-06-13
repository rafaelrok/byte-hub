package br.com.rafaelvieira.bytehub.api.model.notification.like;


import br.com.rafaelvieira.bytehub.api.model.article.ArticleResponse;
import br.com.rafaelvieira.bytehub.api.model.profile.ProfileResponse;
import br.com.rafaelvieira.bytehub.api.model.user.UserResponse;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("like")
public class NotificationMessageLike {

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private ProfileResponse profile;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private ArticleResponse article;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private UserResponse user;

    private NotificationType type;
}
