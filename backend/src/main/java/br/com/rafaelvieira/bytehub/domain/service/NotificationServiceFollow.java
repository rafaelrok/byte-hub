package br.com.rafaelvieira.bytehub.domain.service;

import br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO;
import br.com.rafaelvieira.bytehub.domain.model.NotificationMessage;
import br.com.rafaelvieira.bytehub.domain.model.User;
import br.com.rafaelvieira.bytehub.domain.repository.NotificationMessageRepository;
import br.com.rafaelvieira.bytehub.domain.repository.UserRepository;
import br.com.rafaelvieira.bytehub.domain.service.publisher.NotificationPublisher;
import br.com.rafaelvieira.bytehub.domain.util.ConvertTo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificationServiceFollow extends ConvertTo<NotificationMessageFollowDTO, NotificationMessage> {

    private final UserRepository userRepository;
    private final NotificationPublisher notificationPublisher;
    private final NotificationMessageRepository notificationMessageRepository;


//    @Transactional
//    public void saveAndPublishFollowNotification(NotificationMessageFollowDTO notificationFollow) {
//        notificationMessageRepository.save(convertToDto(notificationFollow));
//        NotificationMessage notificationId = notificationMessageRepository.getByTargetProfileId(notificationFollow.getFollowedId());
//
//        notificationPublisher.publishFollowNotification(
//                notificationId.getId(),
//                notificationFollow.getFollowerId(),
//                notificationFollow.getFollowedId(),
//                notificationFollow.getUsername()
//        );
//    }

    @Transactional
    public void saveAndPublishFollowNotification(NotificationMessageFollowDTO notificationFollow) {
        try {
            if (notificationFollow != null) {
                NotificationMessage notificationMessage = saveNotificationMessage(notificationFollow);
                publishFollowNotificationAsync(notificationMessage, notificationFollow);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save and publish follow notification", e);
        }
    }

    private NotificationMessage saveNotificationMessage(NotificationMessageFollowDTO notificationFollow) {
        return notificationMessageRepository.save(convertToDto(notificationFollow));
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

    public void sendFollowNotification(Long followerId, Long followedId) {
        User follower = userRepository.findById(followerId).orElseThrow();
        User followed = userRepository.findById(followedId).orElseThrow();

        // Send a notification to the followed user


        // por exemplo, armazenar a notificação no banco de dados, enviar um e-mail, etc.
    }

    @Transactional(readOnly = true)
    public List<NotificationMessageFollowDTO> getNotificationsFollowForProfile(Long profileId) {
        return notificationMessageRepository.findTargetProfileIdFollow(profileId);
    }

    public Long getTotalCount(Long profileId) {
        return notificationMessageRepository.countTargetProfileIdFollow(profileId);
    }

//    public NotificationMessage toEntity(NotificationMessageFollowDTO dto) {
//        return NotificationMessage.builder()
//                .id(dto.getId())
//                .sourceProfileId(dto.getFollowerId())
//                .targetProfileId(dto.getFollowedId())
//                .sendUsername(dto.getUsername())
//                .type(dto.getType())
//                .build();
//    }
}

