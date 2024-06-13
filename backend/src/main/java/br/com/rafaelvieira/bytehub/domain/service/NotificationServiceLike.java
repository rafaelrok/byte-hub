package br.com.rafaelvieira.bytehub.domain.service;

import br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO;
import br.com.rafaelvieira.bytehub.domain.model.Article;
import br.com.rafaelvieira.bytehub.domain.model.NotificationMessage;
import br.com.rafaelvieira.bytehub.domain.model.User;
import br.com.rafaelvieira.bytehub.domain.repository.ArticleRepository;
import br.com.rafaelvieira.bytehub.domain.repository.NotificationMessageRepository;
import br.com.rafaelvieira.bytehub.domain.repository.UserRepository;
import br.com.rafaelvieira.bytehub.domain.service.publisher.NotificationPublisher;
import br.com.rafaelvieira.bytehub.domain.util.ConvertTo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificationServiceLike extends ConvertTo<NotificationMessageLikeDTO, NotificationMessage> {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final NotificationPublisher notificationPublisher;
    private final NotificationMessageRepository notificationMessageRepository;


    @Transactional
    public void saveAndPublishLikeNotification(NotificationMessageLikeDTO notificationLike) {
        notificationMessageRepository.save(convertToDto(notificationLike));
        NotificationMessage notificationId = notificationMessageRepository.getByTargetProfileId(notificationLike.getProfileId());

        notificationPublisher.publishLikeNotification(
                notificationId.getId(),
                notificationLike.getProfileId(),
                notificationLike.getArticleId(),
                notificationLike.getUsername()
        );
    }

    public void sendLikeNotification(Long likerId, Long articleId) {
        User liker = userRepository.findById(likerId).orElseThrow();
        Article article = articleRepository.findById(articleId).orElseThrow();

        // Send a notification to the author of the article
        // e.g., store the notification in the database, send an email, etc.
    }

    @Transactional(readOnly = true)
    public List<NotificationMessageLikeDTO> getNotificationsLikeForProfile(Long profileId) {
        return notificationMessageRepository.findTargetProfileIdLike(profileId);
    }

    @Transactional(readOnly = true)
    public Long getTotalCount(Long profileId) {
        return notificationMessageRepository.countTargetProfileIdLike(profileId);
    }


//    public NotificationMessage toEntity(NotificationMessageLikeDTO dto) {
//        return NotificationMessage.builder()
//                .id(dto.getId())
//                .sourceProfileId(dto.getFollowerId())
//                .targetProfileId(dto.getFollowedId())
//                .sendUsername(dto.getUsername())
//                .type(dto.getType())
//                .build();
//    }
}