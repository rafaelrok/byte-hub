package br.com.rafaelvieira.bytehub.domain.repository;

import br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO;
import br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import br.com.rafaelvieira.bytehub.domain.model.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long>{

    NotificationMessage getByTargetProfileId(Long targetProfileId);

    @Query("SELECT new br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO(" +
            "n.id, " +
            "n.targetProfileId, " +
            "n.sourceProfileId, " +
            "n.sendUsername, " +
            "n.type," +
            "n.reading) " +
            "FROM NotificationMessage n " +
            "WHERE n.targetProfileId = :targetProfileId " +
            "AND n.type = :type")
    List<NotificationMessageFollowDTO> findTargetProfileIdFollow(Long targetProfileId, NotificationType type);

    @Query("SELECT COUNT(n.targetProfileId) FROM NotificationMessage n WHERE n.targetProfileId = :targetProfileId AND n.type = :type AND n.reading = :reading")
    Long countTargetProfileIdFollow(Long targetProfileId, NotificationType type, boolean reading);

    @Query("SELECT new br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO(" +
            "n.targetProfileId, " +
            "n.articleId, " +
            "n.sendUsername, " +
            "n.type," +
            "n.reading) " +
            "FROM NotificationMessage n " +
            "WHERE n.profile.id = :targetProfileId " +
            "AND n.type = :type")
    List<NotificationMessageLikeDTO> findTargetProfileIdLike(Long targetProfileId, NotificationType type);

    @Query("SELECT COUNT(n.targetProfileId) FROM NotificationMessage n WHERE n.profile.id = :targetProfileId AND n.type = :type")
    Long countTargetProfileIdLike(Long targetProfileId, NotificationType type);

    @Query("SELECT nm FROM NotificationMessage nm WHERE nm.profile.id = :profileId AND nm.articleId = :articleId")
    Optional<NotificationMessage> findByProfileIdAndArticleId(Long profileId, Long articleId);

    @Query("SELECT nm FROM NotificationMessage nm WHERE nm.sourceProfileId = :followerId AND nm.targetProfileId = :followedId ORDER BY nm.createdAt DESC")
    Optional<NotificationMessage> findFirstBySourceProfileIdAndTargetProfileId(Long followerId, Long followedId);

    @Query("SELECT nm FROM NotificationMessage nm WHERE nm.targetProfileId = :userId AND nm.reading = false")
    List<NotificationMessageFollowDTO> findByTargetProfileIdAndReadIsFalse(Long userId);
}
