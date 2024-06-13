package br.com.rafaelvieira.bytehub.api.controller;

import br.com.rafaelvieira.bytehub.api.mapper.ArticleMapper;
import br.com.rafaelvieira.bytehub.api.model.article.ArticleResponse;
import br.com.rafaelvieira.bytehub.api.security.authorization.CheckSecurity;
import br.com.rafaelvieira.bytehub.domain.service.ArticleService;
import br.com.rafaelvieira.bytehub.domain.service.ProfileService;
import br.com.rafaelvieira.bytehub.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{slug}")
public class ArticleFavoriteController {

    private final UserService userService;
    private final ProfileService profileService;
    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    @PostMapping("/favorite")
    @CheckSecurity.Protected.canManage
    public ArticleResponse favoriteArticle(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);
        var profile = userService.getCurrentUser().getProfile();

        profile = profileService.favorite(profile, article);
        article = articleService.profileFavorited(profile, article);
        return articleMapper.toResponse(profile, article);
    }

    @DeleteMapping("/favorite")
    @CheckSecurity.Protected.canManage
    public ArticleResponse unfavoriteArticle(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);
        var profile = userService.getCurrentUser().getProfile();

        profile = profileService.unfavorite(profile, article);
        article = articleService.profileUnfavorited(profile, article);
        return articleMapper.toResponse(profile, article);
    }
}
