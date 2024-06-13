package br.com.rafaelvieira.bytehub.api.model.article;

import br.com.rafaelvieira.bytehub.api.model.BaseResponse;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("article")
public class ArticleUpdate extends BaseResponse {

    @Nullable
    private String title;

    @Nullable
    private String description;

    @Nullable
    private String body;
}
