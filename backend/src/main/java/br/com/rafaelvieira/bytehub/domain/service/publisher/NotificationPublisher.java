package br.com.rafaelvieira.bytehub.domain.service.publisher;

import br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO;
import br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import br.com.rafaelvieira.bytehub.infra.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationPublisher.class);

    private final RabbitMQConfig rabbitMQConfig;
    private final RabbitTemplate rabbitTemplate;

    public NotificationPublisher(RabbitMQConfig rabbitMQConfig, RabbitTemplate rabbitTemplate) {
        this.rabbitMQConfig = rabbitMQConfig;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishFollowNotification(Long notificationFollowId, Long followerId, Long followedId, String sendUsername) {
        NotificationMessageFollowDTO message = new NotificationMessageFollowDTO(
                notificationFollowId,
                followerId,
                followedId,
                sendUsername,
                NotificationType.FOLLOW,
                false);
        try {
            rabbitTemplate.convertAndSend(String.valueOf(rabbitMQConfig.getExchangeName()), rabbitMQConfig.getFollowRoutingKey(), message);
            LOG.info("Follow notification published to follow_queue successfully.");
        } catch (Exception e) {
            LOG.error("Failed to publish follow notification to follow_queue", e);
            throw new RuntimeException("Failed to publish follow notification", e);
        }
    }

    public void publishLikeNotification(Long notificationLikeId, Long likerId, Long articleId, String userName) {
        if (notificationLikeId == null || likerId == null || articleId == null || userName == null || userName.isEmpty()) {
            LOG.error("Invalid parameters for publishing like notification");
            throw new IllegalArgumentException("Invalid parameters for like notification");
        }

        NotificationMessageLikeDTO message = new NotificationMessageLikeDTO(
                notificationLikeId,
                likerId,
                articleId,
                userName,
                NotificationType.LIKE,
                false);

        try {
            rabbitTemplate.convertAndSend(String.valueOf(rabbitMQConfig.topicExchange()), rabbitMQConfig.getLikeRoutingKey(), message);
            LOG.info("Like notification published for ArticleId: {}, LikerId: {}", articleId, likerId);
        } catch (Exception e) {
            LOG.error("Failed to publish like notification for ArticleId: {}, LikerId: {}", articleId, likerId, e);
            throw new RuntimeException("Failed to publish like notification", e);
        }
    }

}

