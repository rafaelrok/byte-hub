package br.com.rafaelvieira.bytehub.api.controller;

import br.com.rafaelvieira.bytehub.api.mapper.ArticleMapper;
import br.com.rafaelvieira.bytehub.api.model.article.ArticleRegister;
import br.com.rafaelvieira.bytehub.api.model.article.ArticleResponse;
import br.com.rafaelvieira.bytehub.api.model.article.ArticleUpdate;
import br.com.rafaelvieira.bytehub.api.model.article.ArticleWrapper;
import br.com.rafaelvieira.bytehub.api.security.AuthUtils;
import br.com.rafaelvieira.bytehub.api.security.authorization.CheckSecurity;
import br.com.rafaelvieira.bytehub.domain.model.Tag;
import br.com.rafaelvieira.bytehub.domain.service.ArticleService;
import br.com.rafaelvieira.bytehub.domain.service.TagService;
import br.com.rafaelvieira.bytehub.domain.service.UserService;
import br.com.rafaelvieira.bytehub.infra.spec.ArticleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final AuthUtils authUtils;
    private final TagService tagService;
    private final UserService userService;
    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    private static final String DEFAULT_FILTER_LIMIT = "20";
    private static final String DEFAULT_FILTER_OFFSET = "0";
    private static final Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.DESC, "createdAt");
    @GetMapping
    @CheckSecurity.Public.canRead
    public ArticleWrapper getAll(
            ArticleSpecification filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {


        Pageable pageable = PageRequest.of(offset, limit, DEFAULT_FILTER_SORT);
        var articles = articleService.listAll(filter, pageable).getContent();

        if (authUtils.isAuthenticated()) {
            var profile = userService.getCurrentUser().getProfile();
            return articleMapper.toCollectionModel(profile, articles);
        }

        return articleMapper.toCollectionModel(articles);
    }

    @GetMapping("/feed")
    @CheckSecurity.Public.canRead
    public ArticleWrapper getFeed(
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset
    ) {

        var profile = userService.getCurrentUser().getProfile();
        Pageable pageable = PageRequest.of(offset, limit, DEFAULT_FILTER_SORT);
        var articles = articleService.getFeedByUser(profile, pageable);

        return articleMapper.toCollectionModel(profile, articles);
    }


    @GetMapping("/{slug}")
    @CheckSecurity.Public.canRead
    public ArticleResponse getBySlug(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);

        if (authUtils.isAuthenticated()) {
            var profile = userService.getCurrentUser().getProfile();
            return articleMapper.toResponse(profile, article);
        }

        return articleMapper.toResponse(article);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CheckSecurity.Protected.canManage
    public ArticleResponse save(@RequestBody ArticleRegister register) {
        var profile = userService.getCurrentUser().getProfile();

        List<Tag> tags = new ArrayList<>();
        if (register.getTagList() != null) {
            tags = tagService.saveAll(register.getTagList().stream().toList());
        }

        var article = articleMapper.toEntity(register);
        return articleMapper.toResponse(profile, articleService.save(article, profile, tags));
    }

    @PutMapping("/{slug}")
    @CheckSecurity.Articles.canManage
    public ArticleResponse update(@PathVariable String slug, @RequestBody ArticleUpdate update) {
        var article = articleService.getBySlug(slug);
        articleMapper.copyToEntity(update, article);

        return articleMapper.toResponse(articleService.save(article));
    }

    @DeleteMapping("/{slug}")
    @CheckSecurity.Articles.canManage
    public void delete(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);
        articleService.delete(article);
    }

}
