package br.com.rafaelvieira.bytehub.api.security.authorization;

import br.com.rafaelvieira.bytehub.api.security.AuthUtils;
import br.com.rafaelvieira.bytehub.domain.model.Profile;
import br.com.rafaelvieira.bytehub.domain.service.ArticleService;
import br.com.rafaelvieira.bytehub.domain.service.CommentService;
import br.com.rafaelvieira.bytehub.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationConfig {

    private final AuthUtils authUtils;
    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;


    public boolean isArticleAuthor(String slug) {
        if (!isAuthenticated()) {
            return false;
        }

        var article = articleService.getBySlug(slug);
        var author = article.getAuthor();

        return authenticatedUserEquals(author);
    }

    public boolean isCommentAuthor(Long commentId) {
        if (!isAuthenticated()) {
            return false;
        }

        var comment = commentService.getById(commentId);
        var author = comment.getAuthor();

        return authenticatedUserEquals(author);
    }

    private boolean authenticatedUserEquals(Profile user) {
        return userService.getCurrentUser().getProfile().equals(user);
    }

    public boolean isAuthenticated() {
        return authUtils.isAuthenticated();
    }

}