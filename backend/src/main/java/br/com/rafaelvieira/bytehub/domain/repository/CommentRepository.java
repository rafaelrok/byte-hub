package br.com.rafaelvieira.bytehub.domain.repository;

import br.com.rafaelvieira.bytehub.domain.model.Article;
import br.com.rafaelvieira.bytehub.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticle(Article article);
}
