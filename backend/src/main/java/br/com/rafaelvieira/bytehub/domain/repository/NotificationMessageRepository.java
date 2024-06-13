package br.com.rafaelvieira.bytehub.domain.repository;

import br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO;
import br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO;
import br.com.rafaelvieira.bytehub.domain.model.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long>{

    NotificationMessage getByTargetProfileId(Long targetProfileId);

    @Query("SELECT n.targetProfileId, n.sourceProfileId, n.sendUsername, n.type FROM NotificationMessage n WHERE n.profile.id = :targetProfileId")
    List<NotificationMessageFollowDTO> findTargetProfileIdFollow(Long targetProfileId);

    //retornar a quantidade todal de follows do usuário logado
    @Query("SELECT COUNT(n.targetProfileId) FROM NotificationMessage n WHERE n.profile.id = :targetProfileId")
    Long countTargetProfileIdFollow(Long targetProfileId);

    @Query("SELECT n.targetProfileId, n.articleId, n.sendUsername, n.type FROM NotificationMessage n WHERE n.profile.id = :targetProfileId")
    List<NotificationMessageLikeDTO> findTargetProfileIdLike(Long targetProfileId);

    @Query("SELECT COUNT(n.targetProfileId) FROM NotificationMessage n WHERE n.profile.id = :targetProfileId")
    Long countTargetProfileIdLike(Long targetProfileId);

}
