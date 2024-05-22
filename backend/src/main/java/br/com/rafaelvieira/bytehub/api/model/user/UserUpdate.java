package br.com.rafaelvieira.bytehub.api.model.user;

import br.com.rafaelvieira.bytehub.api.model.BaseResponse;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("user")
public class UserUpdate extends BaseResponse {

    @Email
    @Nullable
    private String email;

    @Nullable
    private String bio;

    @Nullable
    private String username;

    @Nullable
    private String image;

    @Nullable
    private String password;

}
