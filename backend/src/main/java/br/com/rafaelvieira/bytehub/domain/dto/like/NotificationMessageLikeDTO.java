package br.com.rafaelvieira.bytehub.domain.dto.like;

import br.com.rafaelvieira.bytehub.domain.dto.BaseDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class NotificationMessageLikeDTO extends BaseDTO<Long> {

    private Long profileId;
    private Long articleId;
    private String username;
    private NotificationType type;

    public NotificationMessageLikeDTO(){}

    public NotificationMessageLikeDTO(Long id,
                                      Long profileId,
                                      Long articleId,
                                      String username,
                                      NotificationType notificationType) {
        super(id);
        this.profileId = profileId;
        this.articleId = articleId;
        this.username = username;
        this.type = notificationType;
    }

    public NotificationMessageLikeDTO(Long profileId,
                                      Long articleId,
                                      String username,
                                      NotificationType notificationType) {
        this.profileId = profileId;
        this.articleId = articleId;
        this.username = username;
        this.type = notificationType;
    }
}
