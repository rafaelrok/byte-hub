package br.com.rafaelvieira.bytehub.api.model.comment;

import br.com.rafaelvieira.bytehub.api.model.BaseResponse;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("comment")
public class CommentRegister extends BaseResponse {

    @NotBlank
    private String body;
}

