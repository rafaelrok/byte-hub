package br.com.rafaelvieira.bytehub.domain.service.consumer;

import br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO;
import br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import br.com.rafaelvieira.bytehub.domain.service.NotificationServiceFollow;
import br.com.rafaelvieira.bytehub.domain.service.NotificationServiceLike;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final NotificationServiceFollow notificationServiceFollow;
    private final NotificationServiceLike notificationServiceLike;

    public NotificationConsumer(NotificationServiceFollow notificationServiceFollow, NotificationServiceLike notificationServiceLike) {
        this.notificationServiceFollow = notificationServiceFollow;
        this.notificationServiceLike = notificationServiceLike;
    }

    @RabbitListener(queues = "follow_queue")
    public void handleFollowNotification(NotificationMessageFollowDTO message) {
        if (message.getType() == NotificationType.FOLLOW) {
            notificationServiceFollow.sendFollowNotification(
                    message.getFollowerId(),
                    message.getFollowedId()
            );
        }
    }

    @RabbitListener(queues = "like_queue")
    public void handleLikeNotification(NotificationMessageLikeDTO message) {
        if (message.getType() == NotificationType.LIKE) {
            notificationServiceLike.sendLikeNotification(
                    message.getProfileId(),
                    message.getArticleId()
            );
        }
    }
}

