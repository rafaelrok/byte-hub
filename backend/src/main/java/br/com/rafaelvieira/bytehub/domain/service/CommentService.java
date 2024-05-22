package br.com.rafaelvieira.bytehub.domain.service;

import br.com.rafaelvieira.bytehub.domain.exception.CommentNotFoundException;
import br.com.rafaelvieira.bytehub.domain.model.Article;
import br.com.rafaelvieira.bytehub.domain.model.Comment;
import br.com.rafaelvieira.bytehub.domain.model.Profile;
import br.com.rafaelvieira.bytehub.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    public List<Comment> getAllByArticle(Article article) {
        return repository.findAllByArticle(article);
    }

    @Transactional
    public Comment getById(Long commentId) {
        return repository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    @Transactional
    public Comment save(Comment comment, Article article, Profile author) {
        comment.setAuthor(author);
        comment.setArticle(article);
        return repository.save(comment);
    }

    @Transactional
    public void delete(Comment comment) {
        repository.delete(comment);
    }


}
