package br.com.rafaelvieira.bytehub.domain.service;

import br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import br.com.rafaelvieira.bytehub.domain.model.Article;
import br.com.rafaelvieira.bytehub.domain.model.NotificationMessage;
import br.com.rafaelvieira.bytehub.domain.model.Profile;
import br.com.rafaelvieira.bytehub.domain.model.User;
import br.com.rafaelvieira.bytehub.domain.repository.ArticleRepository;
import br.com.rafaelvieira.bytehub.domain.repository.NotificationMessageRepository;
import br.com.rafaelvieira.bytehub.domain.repository.ProfileRepository;
import br.com.rafaelvieira.bytehub.domain.repository.UserRepository;
import br.com.rafaelvieira.bytehub.domain.service.publisher.NotificationPublisher;
import br.com.rafaelvieira.bytehub.domain.util.ConvertTo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class NotificationServiceLike extends ConvertTo<NotificationMessageLikeDTO, NotificationMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationServiceLike.class);

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final NotificationPublisher notificationPublisher;
    private final NotificationMessageRepository notificationMessageRepository;


    @Transactional
    public void saveAndPublishLikeNotification(NotificationMessageLikeDTO notificationLike) {
        try {
            LOG.info("Saving like notification for profileId: {}, articleId: {}", notificationLike.getProfileId(), notificationLike.getArticleId());

            Profile profile = profileRepository.findById(notificationLike.getProfileId())
                    .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

            Optional<NotificationMessage> notificationMessageOpt = convertToDto(notificationLike);
            if (notificationMessageOpt.isPresent()) {
                NotificationMessage notificationMessage = notificationMessageOpt.get();
                notificationMessage.setProfile(profile); // Associate Profile
                notificationMessageRepository.save(notificationMessage); // Save NotificationMessage com o Profile associado

                LOG.info("Like notification saved, publishing to RabbitMQ. NotificationId: {}", notificationMessage.getId());
                publishLikeNotificationAsync(notificationMessage, notificationLike);
//                notificationPublisher.publishLikeNotification(
//                        notificationMessage.getId(),
//                        notificationLike.getProfileId(),
//                        notificationLike.getArticleId(),
//                        notificationLike.getUsername()
//                );
                LOG.info("Like notification published to RabbitMQ successfully.");
            } else {
                LOG.warn("NotificationMessage conversion failed for NotificationMessageLikeDTO: {}", notificationLike);
            }
        } catch (Exception e) {
            LOG.error("Failed to save and publish like notification", e);
            throw new RuntimeException("Failed to save and publish like notification", e);
        }
    }

    @Transactional
    public void sendLikeNotification(Long likerId, Long articleId) {
        User liker = userRepository.findById(likerId).orElseThrow();
        Article article = articleRepository.findById(articleId).orElseThrow();

        Profile profile = profileRepository.findByUsername(liker.getProfile().getUsername()).orElseThrow();
        saveAndPublishLikeNotification(NotificationMessageLikeDTO.builder()
                .profileId(profile.getId())
                .username(profile.getUsername())
                .articleId(article.getId())
                .type(NotificationType.LIKE)
                .reading(false)
                .build());
    }

    @Async
    protected void publishLikeNotificationAsync(NotificationMessage notificationId, NotificationMessageLikeDTO notificationLike) {
        notificationPublisher.publishLikeNotification(
                notificationId.getId(),
                notificationLike.getProfileId(),
                notificationLike.getArticleId(),
                notificationLike.getUsername()
        );
    }

    @Transactional(readOnly = true)
    public List<NotificationMessageLikeDTO> getNotificationsLikeForProfile(Long profileId) {
        NotificationType type = NotificationType.LIKE;
        return notificationMessageRepository.findTargetProfileIdLike(profileId, type);
    }

    @Transactional(readOnly = true)
    public Long getTotalCount(Long profileId) {
        NotificationType type = NotificationType.LIKE;
        return notificationMessageRepository.countTargetProfileIdLike(profileId, type);
    }

    @Transactional(readOnly = true)
    public NotificationMessage getNotificationMessage(NotificationMessageLikeDTO notificationLikeDTO) {
        return notificationMessageRepository.findByProfileIdAndArticleId(
                notificationLikeDTO.getProfileId(),
                notificationLikeDTO.getArticleId()
        ).orElseThrow(() -> new EntityNotFoundException("Notification not found for given profileId and articleId"));
    }

    @Override
    public Optional<NotificationMessage> convertToDto(NotificationMessageLikeDTO dto) {
        if(dto == null) {
            LOG.warn("NotificationMessageLikeDTO is null");
            return Optional.empty();
        }
        return Optional.of(NotificationMessage.builder()
                .id(dto.getId())
                .sourceProfileId(dto.getProfileId())
                .sendUsername(dto.getUsername())
                .type(dto.getType())
                .reading(false)
                .build());
    }

    @Override
    public Optional<NotificationMessageLikeDTO> convertToEntity(NotificationMessage entity) {
        if(entity == null) {
            LOG.warn("NotificationMessage is null");
            return Optional.empty();
        }
        return Optional.of(NotificationMessageLikeDTO.builder()
                .profileId(entity.getSourceProfileId())
                .username(entity.getSendUsername())
                .type(entity.getType())
                .reading(entity.isReading())
                .build());
    }
    
}