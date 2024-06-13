package br.com.rafaelvieira.bytehub.domain.service;

import br.com.rafaelvieira.bytehub.domain.dto.follow.NotificationMessageFollowDTO;
import br.com.rafaelvieira.bytehub.domain.dto.like.NotificationMessageLikeDTO;
import br.com.rafaelvieira.bytehub.domain.enums.NotificationType;
import br.com.rafaelvieira.bytehub.domain.exception.ProfileNotFoundException;
import br.com.rafaelvieira.bytehub.domain.model.Article;
import br.com.rafaelvieira.bytehub.domain.model.NotificationMessage;
import br.com.rafaelvieira.bytehub.domain.model.Profile;
import br.com.rafaelvieira.bytehub.domain.model.User;
import br.com.rafaelvieira.bytehub.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final static Logger LOG = LoggerFactory.getLogger(ProfileService.class);

    private final String DEFAULT_IMAGE_URL = "https://res.cloudinary.com/dkar9uu7g/image/upload/v1717717949/Developer/ByteHub/smiley-cyrus_d4kop7.jpg";

    private final ArticleService articleService;
    private final ProfileRepository profileRepository;
    private final NotificationServiceFollow notificationServiceFollow;
    private final NotificationServiceLike notificationServiceLike;

    @Transactional(readOnly = true)
    public Profile getByUsername(String username) {
        return profileRepository.findByUsername(username).orElseThrow(ProfileNotFoundException::new);
    }

    @Transactional
    public Profile save(Profile profile) {

        profile.setBio(profile.getBio());
        profile.setUsername(profile.getUsername());
        profile.setImage(profile.getImage());

        return profileRepository.save(profile);
    }

    public Profile createNewProfile(User user, String username) {
        return Profile.builder()
                .user(user)
                .username(username)
                .bio(null)
                .image(DEFAULT_IMAGE_URL)
                .build();
    }

    @Transactional
    public void follow(Profile current, Profile toFollow) {
        LOG.info("User {} is following {}", current.getId(), toFollow.getId());
        current.followProfile(toFollow);
        profileRepository.save(current);

        NotificationMessageFollowDTO notification = NotificationMessageFollowDTO.builder()
                .followerId(current.getId())
                .followedId(toFollow.getId())
                .username(toFollow.getUsername())
                .type(NotificationType.FOLLOW)
                .build();

        NotificationMessage notificationFollow = notificationServiceFollow.convertToDto(notification);
        toFollow.getNotifications().add(notificationFollow);
        profileRepository.save(toFollow);
        notificationServiceFollow.saveAndPublishFollowNotification(notification);
    }


    @Transactional
    public void unfollow(Profile current, Profile toFollow) {
        current.unfollowProfile(toFollow);
        profileRepository.save(current);
    }

    @Transactional
    public Profile favorite(Profile profile, Article article) {
        LOG.info("User {} is favorite {}", profile.getId(), article.getId());
        profile.favoriteArticle(article);
        profileRepository.save(profile);

        NotificationMessageLikeDTO notificationLikeDTO = NotificationMessageLikeDTO.builder()
                .profileId(profile.getId())
                .articleId(article.getId())
                .username(profile.getUsername())
                .type(NotificationType.LIKE)
                .build();

        NotificationMessage notificationLike = notificationServiceLike.convertToDto(notificationLikeDTO);
        article.getAuthor().getNotifications().add(notificationLike);
        articleService.save(article);
        notificationServiceLike.saveAndPublishLikeNotification(notificationLikeDTO);

        return profile;
    }

    @Transactional
    public Profile unfavorite(Profile profile, Article article) {
        profile.unfavoriteArticle(article);
        return profileRepository.save(profile);
    }
}
