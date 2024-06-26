package br.com.rafaelvieira.bytehub.domain.service;

import br.com.rafaelvieira.bytehub.domain.exception.ArticleNotFoundException;
import br.com.rafaelvieira.bytehub.domain.exception.ArticleNotUniqueException;
import br.com.rafaelvieira.bytehub.domain.model.Article;
import br.com.rafaelvieira.bytehub.domain.model.Profile;
import br.com.rafaelvieira.bytehub.domain.model.Tag;
import br.com.rafaelvieira.bytehub.domain.repository.ArticleRepository;
import br.com.rafaelvieira.bytehub.infra.spec.ArticleSpecification;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Slugify slg;
    private final ArticleRepository repository;

    @Transactional(readOnly = true)
    public Page<Article> listAll(ArticleSpecification filter, Pageable pageable) {
        return repository.findAll(filter, pageable);
    }

    @Transactional(readOnly = true)
    public Article getBySlug(String slug) {
        return repository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Article> getFeedByUser(Profile profile, Pageable pageable) {
        List<Profile> followedUsers = profile.getProfiles().stream().toList();

        return repository.findAllByAuthorIn(followedUsers, pageable);
    }

    @Transactional(readOnly = true)
    public Article findByAuthor(Profile profile) {
        return repository.findByAuthor(profile).orElseThrow(ArticleNotFoundException::new);
    }

    @Transactional
    public Article save(Article article, Profile profile, List<Tag> tags) {
        addAllTags(article, tags);
        article.setAuthor(profile);
        return save(article);
    }

    @Transactional
    public Article save(Article article) {
        var slug = slg.slugify(article.getTitle());
        checkSlugAvailability(slug, article);
        article.setSlug(slug);

        return repository.save(article);
    }

    @Transactional
    public void delete(Article article) {
        var favorited = article.getFavorites();
        favorited.forEach(u -> u.unfavoriteArticle(article));

        repository.delete(article);
    }

    @Transactional
    public Article profileFavorited(Profile profile, Article article) {
        article.addFavorite(profile);
        return repository.save(article);
    }

    @Transactional
    public Article profileUnfavorited(Profile profile, Article article) {
        article.removeFavorite(profile);
        return repository.save(article);
    }

    private void addAllTags(Article article, List<Tag> tags) {
        article.setTagList(new HashSet<>());
        tags.forEach(article::addTag);
    }

    private boolean slugTaken(String slug, Article article) {
        var existingArticle = repository.findBySlug(slug);
        return existingArticle.isPresent() && !existingArticle.get().equals(article);
    }

    private void checkSlugAvailability(String slug, Article article) {
        if (slugTaken(slug, article)) throw new ArticleNotUniqueException();
    }
}
