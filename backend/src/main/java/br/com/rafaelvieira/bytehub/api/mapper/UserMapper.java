package br.com.rafaelvieira.bytehub.api.mapper;

import br.com.rafaelvieira.bytehub.api.model.user.UserRegister;
import br.com.rafaelvieira.bytehub.api.model.user.UserResponse;
import br.com.rafaelvieira.bytehub.api.model.user.UserUpdate;
import br.com.rafaelvieira.bytehub.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .token(user.getToken())
                .bio(user.getProfile().getBio())
                .image(user.getProfile().getImage())
                .username(user.getProfile().getUsername())
                .build();
    }

    public User toEntity(UserRegister register) {
        return modelMapper.map(register, User.class);
    }

    public void copyToEntity(UserUpdate update, User user) {
        modelMapper.map(update, user);
    }
}
