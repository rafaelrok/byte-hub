package br.com.rafaelvieira.bytehub.api.model.user;

import br.com.rafaelvieira.bytehub.api.model.BaseResponse;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonTypeName("user")
public class UserResponse extends BaseResponse {

    private String email;

    private String username;
    private String bio;
    private String image;

    private String token;
}