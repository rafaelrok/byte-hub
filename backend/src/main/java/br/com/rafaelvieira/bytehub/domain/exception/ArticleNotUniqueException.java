package br.com.rafaelvieira.bytehub.domain.exception;

public class ArticleNotUniqueException extends BusinessException {

    public ArticleNotUniqueException() {
        super("There's already an article with this title");
    }
}
