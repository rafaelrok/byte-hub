package br.com.rafaelvieira.bytehub.domain.service.publisher;

import br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO;
import br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import br.com.rafaelvieira.bytehub.infra.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public NotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishFollowNotification(Long notificationFollowId,
                                          Long followerId,
                                          Long followedId,
                                          String sendUsername) {
        NotificationMessageFollowDTO message = new NotificationMessageFollowDTO(
                notificationFollowId,
                followerId,
                followedId,
                sendUsername,
                NotificationType.FOLLOW);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.FOLLOW_ROUTING_KEY, message);
    }

    public void publishLikeNotification(Long notificationLikeId,
                                        Long likerId,
                                        Long articleId,
                                        String userName) {
        NotificationMessageLikeDTO message = new NotificationMessageLikeDTO(
                notificationLikeId,
                likerId,
                articleId,
                userName,
                NotificationType.LIKE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.LIKE_ROUTING_KEY, message);
    }

    // NotificationMessage is a class that holds the necessary data for the notification
    // NotificationType is an enum representing the type of notification (FOLLOW or LIKE)
}

