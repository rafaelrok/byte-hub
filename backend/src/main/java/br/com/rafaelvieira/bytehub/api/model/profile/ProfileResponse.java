package br.com.rafaelvieira.bytehub.api.model.profile;

import br.com.rafaelvieira.bytehub.api.model.BaseResponse;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("profile")
public class ProfileResponse extends BaseResponse {

    private String username;
    private String bio;
    private String image;
    private boolean following;
}
