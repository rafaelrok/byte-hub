package br.com.rafaelvieira.bytehub.domain.service;

import br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import br.com.rafaelvieira.bytehub.domain.model.NotificationMessage;
import br.com.rafaelvieira.bytehub.domain.model.Profile;
import br.com.rafaelvieira.bytehub.domain.repository.NotificationMessageRepository;
import br.com.rafaelvieira.bytehub.domain.repository.ProfileRepository;
import br.com.rafaelvieira.bytehub.domain.service.publisher.NotificationPublisher;
import br.com.rafaelvieira.bytehub.domain.util.ConvertTo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class NotificationServiceFollow extends ConvertTo<NotificationMessageFollowDTO, NotificationMessage> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(NotificationServiceFollow.class);

    private final ProfileRepository profileRepository;
    private final NotificationPublisher notificationPublisher;
    private final NotificationMessageRepository notificationMessageRepository;


    @Transactional
    public void saveAndPublishFollowNotification(NotificationMessageFollowDTO notificationFollow) {
        if (notificationFollow == null) {
            LOG.warn("Notification follow DTO is null");
            return;
        }
        try {
            NotificationMessage notificationMessage = saveNotificationMessage(notificationFollow);
            publishFollowNotificationAsync(notificationMessage, notificationFollow);
        } catch (Exception e) {
            LOG.error("Failed to save and publish follow notification", e);
            throw new RuntimeException("Failed to save and publish follow notification", e);
        }
    }

    private NotificationMessage saveNotificationMessage(NotificationMessageFollowDTO notificationFollow) {
        NotificationMessage notificationMessage = convertToDto(notificationFollow).orElseThrow();
        Profile profile = profileRepository.findById(notificationFollow.getFollowerId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        notificationMessage.setProfile(profile);
        notificationMessageRepository.save(notificationMessage);
        return notificationMessage;
    }

    @Async
    protected void publishFollowNotificationAsync(NotificationMessage notificationId, NotificationMessageFollowDTO notificationFollow) {
        notificationPublisher.publishFollowNotification(
                notificationId.getId(),
                notificationFollow.getFollowerId(),
                notificationFollow.getFollowedId(),
                notificationFollow.getUsername()
        );
    }

    @Transactional
    public void sendFollowNotification(Long followerId, Long followedId) {
        Profile follower = profileRepository.findById(followerId)
                .orElseThrow(() -> new EntityNotFoundException("Follower profile not found"));
        Profile followed = profileRepository.findById(followedId)
                .orElseThrow(() -> new EntityNotFoundException("Followed profile not found"));

        NotificationMessageFollowDTO notification = NotificationMessageFollowDTO.builder()
                .followerId(followerId)
                .followedId(followedId)
                .username(followed.getUsername())
                .type(NotificationType.FOLLOW)
                .reading(false)
                .build();

        saveAndPublishFollowNotification(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationMessageFollowDTO> getNotificationsFollowForProfile(Long profileId) {
        NotificationType type = NotificationType.FOLLOW;
        return notificationMessageRepository.findTargetProfileIdFollow(profileId, type);
    }

    public Long getTotalCount(Long profileId) {
        boolean reading = false;
        return notificationMessageRepository.countTargetProfileIdFollow(profileId, NotificationType.FOLLOW, reading);
    }

    @Transactional(readOnly = true)
    public NotificationMessage getNotificationMessage(NotificationMessageFollowDTO notificationFollowDTO) {
        return notificationMessageRepository.findFirstBySourceProfileIdAndTargetProfileId(
                notificationFollowDTO.getFollowerId(),
                notificationFollowDTO.getFollowedId()
        ).orElseThrow(() -> new EntityNotFoundException("Notification not found for given followerId and followedId"));
    }

    @Override
    public Optional<NotificationMessage> convertToDto(NotificationMessageFollowDTO dto) {
        if (dto == null) {
            LOG.warn("NotificationMessageFollowDTO is null");
            return Optional.empty();
        }
        return Optional.ofNullable(NotificationMessage.builder()
                .sourceProfileId(dto.getFollowerId())
                .targetProfileId(dto.getFollowedId())
                .sendUsername(dto.getUsername())
                .type(dto.getType())
                .reading(false)
                .build());
    }

    @Override
    public Optional<NotificationMessageFollowDTO> convertToEntity(NotificationMessage entity) {
        if (entity == null) {
            LOG.warn("NotificationMessage is null");
            return Optional.empty();
        }
        return Optional.ofNullable(NotificationMessageFollowDTO.builder()
                .followerId(entity.getSourceProfileId())
                .followedId(entity.getTargetProfileId())
                .username(entity.getSendUsername())
                .type(entity.getType())
                .reading(entity.isReading())
                .build());
    }

    public void markAsReadFollow(Long notificationId) {
        NotificationMessage notification = notificationMessageRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        notification.setReading(true);
        notificationMessageRepository.save(notification);
    }

    public List<NotificationMessageFollowDTO> getUnreadNotifications(Long userId) {
        return notificationMessageRepository.findByTargetProfileIdAndReadIsFalse(userId);
    }

}

