package br.com.rafaelvieira.bytehub.api.controller;

import br.com.rafaelvieira.bytehub.api.mapper.CommentMapper;
import br.com.rafaelvieira.bytehub.api.model.comment.CommentRegister;
import br.com.rafaelvieira.bytehub.api.model.comment.CommentResponse;
import br.com.rafaelvieira.bytehub.api.model.comment.CommentWrapper;
import br.com.rafaelvieira.bytehub.api.security.AuthUtils;
import br.com.rafaelvieira.bytehub.api.security.authorization.CheckSecurity;
import br.com.rafaelvieira.bytehub.domain.service.ArticleService;
import br.com.rafaelvieira.bytehub.domain.service.CommentService;
import br.com.rafaelvieira.bytehub.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{slug}/comments")
public class CommentController {

    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final AuthUtils authUtils;

    @GetMapping
    @CheckSecurity.Public.canRead
    public CommentWrapper listByArticle(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);

        if (authUtils.isAuthenticated()) {
            var profile = userService.getCurrentUser().getProfile();
            return commentMapper.toCollectionResponse(profile, commentService.getAllByArticle(article));
        }


        return commentMapper.toCollectionResponse(commentService.getAllByArticle(article));
    }

    @PostMapping
    @CheckSecurity.Protected.canManage
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse save(@PathVariable String slug, @Valid @RequestBody CommentRegister register) {
        var comment = commentMapper.toEntity(register);
        var article = articleService.getBySlug(slug);
        var author = userService.getCurrentUser().getProfile();

        return commentMapper.toResponse(commentService.save(comment, article, author));
    }

    @DeleteMapping("/{commentId}")
    @CheckSecurity.Comments.canDelete
    public void delete(@PathVariable String slug, @PathVariable Long commentId) {
        var article = articleService.getBySlug(slug);
        var comment = commentService.getById(commentId);

        commentService.delete(comment);
    }

}
