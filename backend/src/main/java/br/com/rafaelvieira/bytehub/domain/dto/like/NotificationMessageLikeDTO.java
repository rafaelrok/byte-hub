package br.com.rafaelvieira.bytehub.domain.dto.like;

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
public class NotificationMessageLikeDTO extends BaseDTO<Long> {

    private Long profileId;
    private Long articleId;
    private String username;
    private NotificationType type;
    private boolean reading;

    public NotificationMessageLikeDTO(){
        super();
    }

    @JsonCreator
    public NotificationMessageLikeDTO(@JsonProperty("id") Long id,
                                        @JsonProperty("profileId") Long profileId,
                                        @JsonProperty("articleId") Long articleId,
                                        @JsonProperty("username") String username,
                                        @JsonProperty("type") NotificationType notificationType,
                                        @JsonProperty("reading") boolean reading) {
        super(id);
        this.profileId = profileId;
        this.articleId = articleId;
        this.username = username;
        this.type = notificationType;
        this.reading = reading;
    }

    public NotificationMessageLikeDTO(Long profileId,
                                      Long articleId,
                                      String username,
                                      NotificationType notificationType,
                                      boolean reading) {
        this.profileId = profileId;
        this.articleId = articleId;
        this.username = username;
        this.type = notificationType;
        this.reading = reading;
    }
}
