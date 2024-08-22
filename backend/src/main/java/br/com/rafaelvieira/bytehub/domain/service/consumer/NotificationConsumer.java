package br.com.rafaelvieira.bytehub.domain.service.consumer;

import br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO;
import br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import br.com.rafaelvieira.bytehub.domain.service.NotificationServiceFollow;
import br.com.rafaelvieira.bytehub.domain.service.NotificationServiceLike;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @RabbitListener(queues = "${rabbitmq.queue.follow}")
    public void handleFollowNotification(NotificationMessageFollowDTO message) {
        try {
            if (message.getType() == NotificationType.FOLLOW && message.getFollowerId() != null && message.getFollowedId() != null) {
                new ObjectMapper().writeValueAsString(message);
            } else {
                throw new RuntimeException("Invalid follow notification message");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to handle follow notification", e);
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.like}")
    public void handleLikeNotification(NotificationMessageLikeDTO message) {
        try {
            if (message.getType() == NotificationType.LIKE) {
                notificationServiceLike.sendLikeNotification(
                        message.getProfileId(),
                        message.getArticleId()
                );
            } else {
                throw new RuntimeException("Invalid like notification message");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to handle like notification", e);
        }
    }
}

