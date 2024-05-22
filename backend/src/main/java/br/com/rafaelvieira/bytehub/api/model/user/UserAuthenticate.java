package br.com.rafaelvieira.bytehub.api.model.user;

import br.com.rafaelvieira.bytehub.api.model.BaseResponse;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("user")
public class UserAuthenticate extends BaseResponse {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
