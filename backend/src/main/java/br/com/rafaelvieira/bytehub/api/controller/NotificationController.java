package br.com.rafaelvieira.bytehub.api.controller;

import br.com.rafaelvieira.bytehub.api.security.authorization.CheckSecurity;
import br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO;
import br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO;
import br.com.rafaelvieira.bytehub.domain.service.NotificationServiceFollow;
import br.com.rafaelvieira.bytehub.domain.service.NotificationServiceLike;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationServiceFollow serviceFollow;
    private final NotificationServiceLike serviceLike;


    @GetMapping("/follow/profile/{profileId}")
    @CheckSecurity.Protected.canManage
    public ResponseEntity<List<NotificationMessageFollowDTO>> getNotificationsFollow(@PathVariable Long profileId) {
        List<NotificationMessageFollowDTO> notificationsFollow = serviceFollow.getNotificationsFollowForProfile(profileId);
        return ResponseEntity.ok(notificationsFollow);
    }

    // Controller responsavel por retornar o total de follows no usuário logado
    @GetMapping("/follow/profile/{profileId}/total")
    @CheckSecurity.Protected.canManage
    public ResponseEntity<Long> getTotalCountFollow(@PathVariable Long profileId) {
        Long totalCount = serviceFollow.getTotalCount(profileId);
        return ResponseEntity.ok(totalCount);
    }

    //responsavel por atualizar o campo reading para true
    @PutMapping("/follow/profile/{notificationId}/read")
    @CheckSecurity.Protected.canManage
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        serviceFollow.markAsReadFollow(notificationId);
        return ResponseEntity.ok().build();
    }

    //responsavel por retornar as notificações de follow com reading = false
    @GetMapping("/follow/profile/{profileId}/unread")
    @CheckSecurity.Protected.canManage
    public ResponseEntity<List<NotificationMessageFollowDTO>> getUnreadNotificationsFollow(@PathVariable Long profileId) {
        List<NotificationMessageFollowDTO> unreadNotificationsFollow = serviceFollow.getUnreadNotifications(profileId);
        return ResponseEntity.ok(unreadNotificationsFollow);
    }

    @GetMapping("/like/profile/{profileId}")
    @CheckSecurity.Protected.canManage
    public ResponseEntity<List<NotificationMessageLikeDTO>> getNotificationsLike(@PathVariable Long profileId) {
        List<NotificationMessageLikeDTO> notificationsLike = serviceLike.getNotificationsLikeForProfile(profileId);
        return ResponseEntity.ok(notificationsLike);
    }

    @GetMapping("/like/profile/{profileId}/total")
    @CheckSecurity.Protected.canManage
    public ResponseEntity<Long> getTotalCountLike(@PathVariable Long profileId) {
        Long totalCount = serviceLike.getTotalCount(profileId);
        return ResponseEntity.ok(totalCount);
    }
}
